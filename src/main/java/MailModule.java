import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailConnection;
import com.googlecode.gmail4j.GmailMessage;
import com.googlecode.gmail4j.http.HttpGmailConnection;
import com.googlecode.gmail4j.rss.RssGmailClient;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class MailModule extends Modules {

    private static List<GmailMessage> myMail;

    public MailModule() {
        setModuleName("Mail");
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
            String host = "pop.gmail.com";
            String mailStoreType = "pop3";
            String username = "grattepanche.robin@gmail.com";
            String password = "azerty--66";

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
        post("/mail/sendMail", (req, res) -> {
            String to = req.queryParams("to");
            String subject = req.queryParams("subject");
            String content = req.queryParams("content");
            if(to == null)
                return "401 Unauthorized";
            if (subject == null)
                subject = "";
            if (content == null)
                content = "";
            String out = UserService.sendMail(to, subject, content);
            if (out == "200 OK") {
                res.status(200);
                return "200 OK";
            }
            return "400 send mail fail";
        });
        return "400 send mail fail";
    }

    public static String postMailReactModule(String to, String subject, String content)
    {
     UserService.sendMail(to, subject, content);
     return "200 OK";
    }

    public static void main(String[] inArgs) {

        MailModule mail = new MailModule();

        mail.getFromMailModule();

        mail.getUnreadMailModule();

        mail.getAllMailModule();

        mail.postMailModule();
    }
}
