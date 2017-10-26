import com.googlecode.gmail4j.*;
import com.googlecode.gmail4j.http.HttpGmailConnection;
import com.googlecode.gmail4j.rss.RssGmailClient;
import com.googlecode.gmail4j.util.LoginDialog;


import java.util.List;

public class GetMail {

    public List<GmailMessage> myMail;

    public void GetMail(){
        GmailClient client = new RssGmailClient();
        GmailConnection connection = new HttpGmailConnection(LoginDialog.getInstance().show("Enter Gmail Login"));
        client.setConnection(connection);
        final List<GmailMessage> messages = client.getUnreadMessages();
        myMail = messages;
    }

    public List<GmailMessage> getMyMail() {
        return myMail;
    }
}
