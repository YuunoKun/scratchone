package me.scratchone.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class MailUtils {
    private String msg;
    private String className;
    private final String USERNAME = "ScratchOne2020@gmail.com";
    private final String PASSWORD = "Malding2020";
    private final String HOST = "smtp.gmail.com";

    public void sendMail(String email, String text, String title)
    {
        String to = email;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, null);
        try
        {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(title);
            message.setText(text);

            Transport.send(message, USERNAME, PASSWORD);
            msg = "Email Successfully Sent";
        }
        catch(Exception ex)
        {
            msg = ex.getClass().getName();
        }
    }

}
