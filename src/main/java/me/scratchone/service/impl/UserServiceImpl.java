package me.scratchone.service.impl;

import me.scratchone.dao.UserDao;
import me.scratchone.dao.impl.UserDaoImpl;
import me.scratchone.domain.RegistrationState;
import me.scratchone.domain.User;
import me.scratchone.service.UserService;
import me.scratchone.util.MailUtils;
import me.scratchone.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public RegistrationState register(User user) {
        try {
            User u1 = userDao.findUserByUsername(user.getUsername());
            User u2 = userDao.findUserByEmail(user.getEmail());

            if (u1 != null) {
                return RegistrationState.USERNAME_TAKEN;
            } else if(u2 != null) {
                return RegistrationState.EMAIL_TAKEN;
            } else {
                user.setCode(UuidUtil.getUuid());
                user.setStatus("N");
                userDao.createNewUser(user);

                String content="http://scratchone.me/activate?acode="+user.getCode();
                content += "\n";
                content += "Active Here! Start your journey with ScratchOne!";
                MailUtils mailUtils = new MailUtils();
                mailUtils.sendMail(user.getEmail(),content,"ScratchOne Activation");

                return RegistrationState.SUCCESS;
            }
        } catch(Exception e) {
            return RegistrationState.SERVER_ERROR;
        }
    }

    @Override
    public void activate(String code) {
        userDao.updateUserStatusByCode(code);
    }

    @Override
    public User login(User user) { return userDao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword()); }

    @Override
    public User getProfile(String username) { return userDao.findUserByUsername(username); }

    @Override
    public void updateProfile(User user) { userDao.updateUserByProfile(user); }
}
