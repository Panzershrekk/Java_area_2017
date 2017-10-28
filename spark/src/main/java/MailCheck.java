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
        GmailClient client = new RssGmailClient();
        GmailConnection connection = new HttpGmailConnection("grattepanche.robin@gmail.com", "azerty--66".toCharArray());
        client.setConnection(connection);
    }

    public ArrayList<String> getFromMailModule()
    {
        get("/mail/getMail/:from", (req, res) -> {
        String host = "pop.gmail.com";
        String mailStoreType = "pop3";
        String username = "grattepanche.robin@gmail.com";
        String password = "azerty--66";
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
        return null;
    }

    public ArrayList<String> getUnreadMailModule()
    {
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
        return null;
    }

    public ArrayList<String> getAllMailModule()
    {
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
        return null;
    }

    public String postMailModule()
    {
        post("/mail/sendMail/", (req, res) -> {
            //mail.sendMail();
            String to = req.queryParams("to");
            String subject = req.queryParams("subject");
            String content = req.queryParams("content");
            UserService.sendMail(to, subject, content);
            res.status(200);
            return "200 OK";
        });
        return null;
    }

    public static void main(String[] inArgs) {

        MailCheck mail = new MailCheck();

        mail.getFromMailModule();

        mail.getUnreadMailModule();

        mail.getAllMailModule();

        mail.postMailModule();
    }
}
