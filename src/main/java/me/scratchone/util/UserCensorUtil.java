package me.scratchone.util;

import me.scratchone.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class UserCensorUtil {

    public static User censor(User user) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ClassLoader classLoader = UserCensorUtil.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("user_censor.properties");

        Properties prop = new Properties();
        prop.load(is);


        Class<?> cls = Class.forName("me.scratchone.domain.User");
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            if(prop.containsKey(fieldName)) {
                String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                if(fieldName.startsWith("id", fieldName.length() - 2)) {
                    cls.getMethod(methodName, int.class).invoke(user, Integer.parseInt(prop.getProperty(fieldName)));
                } else {
                    cls.getMethod(methodName, java.lang.String.class).invoke(user, prop.getProperty(fieldName));
                }
            }
        }

        return user;
    }
}
