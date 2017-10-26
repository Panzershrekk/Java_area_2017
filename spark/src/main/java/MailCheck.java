import com.googlecode.gmail4j.EmailAddress;
import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailConnection;
import com.googlecode.gmail4j.GmailMessage;
import com.googlecode.gmail4j.http.HttpGmailConnection;
import com.googlecode.gmail4j.javamail.ImapGmailClient;
import com.googlecode.gmail4j.javamail.ImapGmailConnection;
import com.googlecode.gmail4j.javamail.JavaMailGmailMessage;
import com.googlecode.gmail4j.rss.RssGmailClient;
import com.googlecode.gmail4j.rss.RssGmailMessage;
import com.googlecode.gmail4j.util.LoginDialog;
import com.sun.security.ntlm.Client;

import javax.jws.soap.SOAPBinding;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static spark.Spark.get;
import static spark.Spark.post;

public class MailCheck {

    private static List<GmailMessage> myMail;

    public MailCheck() {

    }

    public static void main(String[] inArgs) {

        GmailClient client = new RssGmailClient();
        GmailConnection connection = new HttpGmailConnection("grattepanche.robin@gmail.com", "azerty--66".toCharArray());
        client.setConnection(connection);

        MailCheck mail = new MailCheck();

        get("/mail", (req, res) -> {
            List<GmailMessage> list = UserService.getMail(client);
//            List<GmailMessage> list = mail.getMail(client);
            return list;
        });

        post("/mail/sendMail", (req, res) -> {
            //mail.sendMail();
            UserService.sendMail();
            return "";
        });
    }

    /*public static void sendMail() {
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
                    InternetAddress.parse("grattepanche.robin@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n test mail api");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*public List<GmailMessage> getMail(GmailClient client){
       final List<GmailMessage> messages = client.getUnreadMessages();
         for (GmailMessage message : messages){
            System.out.println(message);
        }
        return messages;
    }

    public List<GmailMessage> getMyMail() {
        return myMail;
    }*/
}
