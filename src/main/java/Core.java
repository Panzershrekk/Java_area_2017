import spark.Request;
import spark.Response;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class Core {
    public static void main(String[] args) {

        TwitterModule twitos = new TwitterModule();
        FacebookModule facebook = new FacebookModule();
        MailModule mail = new MailModule();
        Database database = new Database();
        DropboxModule dropBox = new DropboxModule(mail);

        List<Modules> ModuleList = new ArrayList<>();
        ModuleList.add(twitos);
        ModuleList.add(facebook);
        ModuleList.add(mail);
        ModuleList.add(dropBox);

        twitos.getTwitterListener().setFacebookModule(facebook);
        twitos.getTwitterListener().setMailModule(mail);

        new Thread(() -> {
            while (true) {
                dropBox.dropBoxCore();
            }
        }).start();

        get("/get/twitos", (request, response) -> {
            return twitos.getTweet();
        });

        post("/post/twitter", (request, response) -> {
            String msg = request.queryParams("msg");
            twitos.postTweet(msg, ModuleList);
            return "Worked";
        });

        post("/get/facebook", (request, response) -> facebook.postData(request, response));

        get("/get/facebook", (request, response) -> (facebook.getSuscribe(request,response)));

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

        get("/info/user/:email", (request, response) -> {
            String email = request.params(":email");
            return Database.getInfo(email);
        });

        delete("/delete/user/", (request, response) -> {
            String email = request.queryParams("email");
            Database.deleteUser(email);

            return "Worked";
        });

        mail.getAllMailModule();
        mail.getFromMailModule();
        mail.getUnreadMailModule();
        mail.postMailModule();

        dropBox.getFilesModule();
        dropBox.getFilesSpesific();
    }
}