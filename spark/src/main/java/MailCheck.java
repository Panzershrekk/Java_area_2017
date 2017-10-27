import com.google.gson.*;
import com.fasterxml.jackson.databind.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonSerializer;
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
import javax.json.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static spark.Spark.get;
import static spark.Spark.post;

public class MailCheck {

    private static List<GmailMessage> myMail;

    public MailCheck() {

    }

    public static class MessageAdapter implements JsonSerializer<Message> {
        public JsonElement serialize(Message src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    private static Gson getGsonParser(Object o) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(o.getClass(), new MessageAdapter());
        return gsonBuilder.create();
    }


    public static void main(String[] inArgs) {

        GmailClient client = new RssGmailClient();
        GmailConnection connection = new HttpGmailConnection("grattepanche.robin@gmail.com", "azerty--66".toCharArray());
        client.setConnection(connection);

        MailCheck mail = new MailCheck();

        get("/mail/getMail/:from", (req, res) -> {
            String host = "pop.gmail.com";// change accordingly
            String mailStoreType = "pop3";
            String username = "grattepanche.robin@gmail.com";// change accordingly
            String password = "azerty--66";// change accordingly
            String from = req.params(":from");

            ArrayList<Message> mFrom = UserService.getMailByAdress(host, mailStoreType, username, password, from);

            ArrayList<ArrayList<String>> messages = new ArrayList<ArrayList<String>>();
            int i = 0;
            for (Message mes : mFrom) {
                MessageJson msg = new MessageJson(mes, i);
                messages.add(msg.createJson());
                i += 1;
            }
            return messages;
        });


        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Message.class, new MessageAdapter());
        get("/mail/unread", (req, res) -> {
            ArrayList<Message> unread = UserService.getUnreadMail();
            ArrayList<ArrayList<String>> messages = new ArrayList<ArrayList<String>>();
            int i = 0;
            for (Message mes : unread) {
                MessageJson msg = new MessageJson(mes, i);
                messages.add(msg.createJson());
                i += 1;
            }
            return messages;
        });

        get("/mail/all", (req, res) -> {
            String host = "pop.gmail.com";// change accordingly
            String mailStoreType = "pop3";
            String username = "grattepanche.robin@gmail.com";// change accordingly
            String password = "azerty--66";// change accordingly

            Message[] all = UserService.getAllMail(host, mailStoreType, username, password);
            ArrayList<ArrayList<String>> messages = new ArrayList<ArrayList<String>>();
            int i = 0;
            for (Message mes : all) {
                MessageJson msg = new MessageJson(mes, i);
                messages.add(msg.createJson());
                i += 1;
            }
            return messages;
        });

        post("/mail/sendMail/", (req, res) -> {
            //mail.sendMail();
            String to = req.queryParams("to");
            String subject = req.queryParams("subject");
	        String content = req.queryParams("content");
            UserService.sendMail(to, subject, content);
            res.status(200);
            return res.toString();
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
