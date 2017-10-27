import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;

public class MessageJson
{
    @SerializedName("id")
    private int _id;

    @SerializedName("folder")
    private String _folder;

    @SerializedName("subject")
    private String _subject;

    @SerializedName("from")
    private String _from;

    @SerializedName("content")
    private String _content;

    @SerializedName("sendDate")
    private String _sendDate;

    public int getId() {
        return _id;
    }

    public String getContent() {
        return _content;
    }

    public String getFrom() {
        return _from;
    }

    public String getSendDate() {
        return _sendDate;
    }

    public String getSubject() {
        return _subject;
    }

    public String getFolder() {
        return _folder;
    }

    public void setContent(String _content) {
        this._content = _content;
    }

    public void setFolder(String _folder) {
        this._folder = _folder;
    }

    public void setFrom(String _from) {
        String cpyForm = _from;
        int i = cpyForm.indexOf("<");
        String subFrom = _from.substring(i + 1, _from.length() - 1);
        this._from = subFrom;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setSendDate(String _sendDate) {
        this._sendDate = _sendDate;
    }

    public void setSubject(String _subject) {
        this._subject = _subject;
    }

    public MessageJson(Message msg, int id) {

        setId(id);
        try {
            setContent(msg.getContent().toString());
            setFrom(msg.getFrom()[0].toString());
            setSendDate(msg.getSentDate().toString());
            setSubject(msg.getSubject().toString());
            setFolder(msg.getFolder().toString());
        } catch (IOException e) {
            System.err.println("Mail received is invalid !");
        } catch (MessagingException e) {
            System.err.println("Mail received is invalid !");
        }
    }

    public ArrayList<String> createJson() {
        ArrayList<String> out = new ArrayList<String>();

        out.add(this.getSendDate());
        out.add(this.getFolder());
        out.add(this.getFrom());
        out.add(this.getContent());
        out.add(this.getSubject());

        return out;
    }
}
