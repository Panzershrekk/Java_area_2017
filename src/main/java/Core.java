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

        List<Modules> ModuleList = new ArrayList<>();
        ModuleList.add(twitos);
        ModuleList.add(facebook);

        twitos.getTwitterListener().setFacebookModule(facebook);


        get("/get/twitos", (request, response) -> {
            return twitos.getTweet();
        });

        get("/post/twitter/:msg", (request, response) -> {
            twitos.postTweet(request.params(":msg"), ModuleList);
            return "Worked";
        });
        get("/displayTweet/", (request, response) -> {
            System.out.println("We are going to display you all new tweet: ");
            twitos.displayNewTweets();
            return "It works well !";
        });

        post("/post/facebook", (request, response) -> {
            return "Worked";
        });

        get("/get/facebook", (request, response) -> {
            //request.params)
            System.out.println("get");
            System.out.println(request.);
            return "";
        });

    }
}