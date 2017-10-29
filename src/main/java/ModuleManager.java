import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

public class ModuleManager {
    TwitterModule twitter = new TwitterModule();
    FacebookModule facebook = new FacebookModule();
    MailModule mail = new MailModule();
    Database database = new Database();
    DropboxModule dropboxModule = new DropboxModule(mail);
    List<Modules> ModuleList = new ArrayList<>();

    public void init() {
        ModuleList.add(twitter);
        ModuleList.add(facebook);
        ModuleList.add(mail);
        ModuleList.add(dropboxModule);
        ModuleList.add(database);

        twitter.getTwitterListener().setFacebookModule(facebook);
        twitter.getTwitterListener().setMailModule(mail);

        mail.getAllMailModule();
        mail.getFromMailModule();
        mail.getUnreadMailModule();
        mail.postMailModule();

        new Thread(() -> {
           while (true) {
               dropboxModule.dropBoxCore();
           }
        }).start();

        dropboxModule.getFilesModule();
        dropboxModule.getFilesSpesific();

        get("/twitter/get", (request, response) -> {
            return (getTwitt());
        });

        post("/twitter/post", (request, response) -> {
            postTwitt(request, response);
            return "Worked";
        });

        post("/facebook/webhook/", (request, response) -> {
            facebokUpdate(request, response);
            return "Worked";
        });

        get("/facebook/webhook/", (request, response) -> {
            return (facebookCallback(request, response));
        });

        post("/subscribe/new/", (request, response) -> {
            String fullName = request.queryParams("fullname");
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            String userName = request.queryParams("username");
            database.Subscribe(fullName, email, userName, password);
            mail.postMailReactModule(email, "Merci " + userName + " pour votre inscription",
                    "Vous êtes bien inscrit sur notre api\nUsername = " + userName + "\nPassword = " + password);
            return "Worked";
        });

        get("/user/info/:email", (request, response) -> {
            String email = request.params(":email");
            return Database.getInfo(email);
        });

        delete("/user/delete/", (request, response) -> {
            String email = request.queryParams("email");
            Database.deleteUser(email);
            return "Worked";
        });
    }

    public String getTwitt() {
        return twitter.getTweet();
    }

    public void postTwitt(Request request, Response response) {
        String msg = request.queryParams("msg");
        twitter.postTweet(msg, ModuleList);
    }

    public void facebokUpdate(Request request, Response response) {
        String post = facebook.postData(request, response);
        mail.postMailReactModule("grattepanche.robin@gmail.com", "New post on facebook", post);
    }

    public String facebookCallback(Request request, Response response) {
        return (facebook.getSuscribe(request, response));
    }
}
