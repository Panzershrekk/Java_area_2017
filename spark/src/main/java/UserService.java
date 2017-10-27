import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailMessage;
import org.eclipse.jetty.server.Authentication;
import org.omg.CORBA.UserException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public interface UserService {

        public static List<GmailMessage> getMail(GmailClient client){
            final List<GmailMessage> messages = client.getUnreadMessages();
            for (GmailMessage message : messages){
                System.out.println(message);
            }
            return messages;

        }

    public static void sendMail(String to, String subject, String content){
            final String username = "grattepanche.robin@gmail.com";
            final String password = "azerty--66";

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("grattepanche.robin@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(content);

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
}
