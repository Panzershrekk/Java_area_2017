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

        List<Modules> ModuleList = new ArrayList<>();
        ModuleList.add(twitos);
        ModuleList.add(facebook);
        ModuleList.add(mail);

        twitos.getTwitterListener().setFacebookModule(facebook);
        twitos.getTwitterListener().setMailModule(mail);

        get("/get/twitos", (request, response) -> {
            return twitos.getTweet();
        });

        post("/post/twitter", (request, response) -> {
            String msg = request.queryParams("msg");
            twitos.postTweet(msg, ModuleList);
            return "Worked";
        });

        get("/displayTweet/", (request, response) -> {
            System.out.println("We are going to display you all new tweet: ");
            twitos.displayNewTweets();
            return "It works well !";
        });

        post("/get/facebook", (request, response) -> facebook.postData(request, response));

        get("/get/facebook", (request, response) -> (facebook.getSuscribe(request,response)));

        post("/subscribe/new/", (request, response) -> {
            String fullName = request.queryParams("fullname");
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            String userName = request.queryParams("username");
            Database.Subscribe(fullName, email, userName, password);
            mail.postMailReactModule(email, "Merci " + userName + " pour votre inscription",
                    "Vous êtes bien inscrit sur notre api\nUsername = " + userName + "\nPassword = " + password);

            return "Worked";
        });

        mail.getAllMailModule();
        mail.getFromMailModule();
        mail.getUnreadMailModule();
        mail.postMailModule();
    }
}