import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import static spark.Spark.get;
import static spark.Spark.post;

public class DropboxModule extends Modules
{
    private static final String AccessToken = "KuxKIuxdEAAAAAAAAAAADY0Gs9UVHvU0650ZrgDL2POt2wKrktmifLZNfSYPeNk2";
    private DbxRequestConfig config = new DbxRequestConfig("dropbox/AreaProjectEpitech", "en_US");
    private DbxClientV2 client = new DbxClientV2(config, AccessToken);

    private MailModule mail;

    private ArrayList<String> before = new ArrayList<String>();
    private ArrayList<String> after = new ArrayList<String>();

    public DropboxModule(MailModule m)
    {
        this.mail = m.getInstance();
    }

    public void dropBoxCore() {
        try {
            initListEntries("");
            while (true) {
                dropboxLoop("");
                if (!compareLists()) {
                    ArrayList<String> rmList = new ArrayList<String>(after);
                    rmList.removeAll(before);
                    before = (ArrayList<String>)after.clone();

                    ArrayList<String> sendM = retChanges(rmList);
                    if (sendM.get(0) == "delete") {
                        mail.postMailReactModule("grattepanche.robin@gmail.com", "Notification DropBox",
                                "A file has been deleted in your DropBox");
                    } else {
                        mail.postMailReactModule("grattepanche.robin@gmail.com", "Notification DropBox",
                                "The file " + sendM.get(1) + " has been added in your DropBox");
                    }
                    sendM.clear();
                }
                after.clear();
            }
        } catch (Exception e) {
            System.err.println("Something went wrong !");
        }
    }

    public ArrayList<String> retChanges(ArrayList<String> changes) {
        ArrayList<String> out = new ArrayList<String>();
        if (changes.isEmpty()) {
            out.add("delete");
        }
        else {
            out.add("add");
            out.add(changes.get(0));
        }
        return out;
    }

    private boolean compareLists()
    {
        if (before == null && after == null)
            return true;
        if ((before == null && after != null) || (before != null && after == null) || (before.size() != after.size()))
            return false;
        Collections.sort(before);
        Collections.sort(after);
        return (before.equals(after));
    }

    private void initListEntries(String path)
    {
        try {
            ListFolderResult result = client.files().listFolder(path);
            while (true) {
                for (Metadata metadata : result.getEntries())
                {
                    before.add(metadata.getPathLower());
                    if (metadata instanceof FolderMetadata)
                    {
                        initListEntries(metadata.getPathLower());
                    }
                }
                if (!result.getHasMore()) {
                    break;
                }
                result = client.files().listFolderContinue(result.getCursor());
            }

        } catch (DbxException e) {
            System.err.println("Something wrong with DropBox");
        }
    }

    private ArrayList<String> getFileInPath(String path)
    {
        ArrayList<String> out = new ArrayList<String>();
        try {
            ListFolderResult result = client.files().listFolder(path);
            while (true) {
                for (Metadata metadata : result.getEntries())
                {
                    out.add(metadata.getPathLower());
                    if (metadata instanceof FolderMetadata)
                    {
                        initListEntries(metadata.getPathLower());
                    }
                }
                if (!result.getHasMore()) {
                    break;
                }
                result = client.files().listFolderContinue(result.getCursor());
            }
        } catch (DbxException e) {
            System.err.println("Something wrong with DropBox");
        }
        return out;
    }

    public void dropboxLoop(String path) {
        try {
            ListFolderResult result = client.files().listFolder(path);
            while (true) {
                for (Metadata metadata : result.getEntries())
                {
                    after.add(metadata.getPathLower());
                    if (metadata instanceof FolderMetadata)
                    {
                        dropboxLoop(metadata.getPathLower());
                    }
                }
                if (!result.getHasMore()) {
                    break;
                }
                result = client.files().listFolderContinue(result.getCursor());
            }

        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

    private String postFile(String file)
    {
        try (InputStream in = new FileInputStream(file)) {
            FileMetadata metadata = client.files().uploadBuilder("/" + file)
                    .uploadAndFinish(in);
        } catch (FileNotFoundException e) {
            System.err.println("File not found !");
        } catch (IOException e) {
            System.err.println("Something went wrong");
        } catch (UploadErrorException e) {
            System.err.println("Error during uploading file !");
        } catch (DbxException e) {
            System.err.println("Error : Something wrong with the DropBox API");
        }
        return ("200 OK");
    }

    /*
        Request Spark functions :
     */

    public ArrayList<String> getFilesModule()
    {
        get("/dropbox", (req, res) -> {
            ArrayList<String> out = getFileInPath("");
            return out;
        });
        return null;
    }

    public ArrayList<String> getFilesSpesific()
    {
        get("/dropbox/:path", (req, res) -> {
            ArrayList<String> out = getFileInPath("/" + req.params(":path"));
            return out;
        });
        return null;
    }
/*
    public String postFileModule()
    {
        post("/dropbox/upload", (req, res) -> {
            String out = postFile(req.queryParams("path"));
            return out;
         });
        return ("400 Bad Request");
    }
    */
}
