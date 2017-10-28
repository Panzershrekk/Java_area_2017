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

        mail.getAllMailModule();
        mail.getFromMailModule();
        mail.getUnreadMailModule();
        mail.postMailModule();
    }
}