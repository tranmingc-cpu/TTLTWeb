package util;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;




public class EmailUtil {

    private static final String FROM_EMAIL = "nhatm9803@gmail.com";
    private static final String PASSWORD = "wzzhuxuoaytyrvtx";

    public static void send(String to, String subject, String htmlContent) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                }
            }
        );

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM_EMAIL, "FoodOrder System"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(msg);
            System.out.println("📧 Send mail success");
        } catch (Exception e) {
            e.printStackTrace(); // xem log nếu lỗi
        }
    }
}
