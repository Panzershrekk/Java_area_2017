import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {
        TwitterModul twitos = new TwitterModul();
        FacebookModule facebook = new FacebookModule();

        get("/get/twitos", (request, response) -> {
            return twitos.getTweet();
        });

        get("/get/facebook/post", (request, response) -> {
            return facebook.getPost();
        });

        get("/post/facebook/post/:msg", (request, response) -> {
            facebook.postFacebook(request.params(":msg"));
            return ("Worked");
        });

        get("/post/twitter/:msg", (request, response) -> {
            return "Worked";
        });

        post("/post/twitter/:msg", (request, response) -> {
            twitos.postTweet(request.params(":msg"));
            return ("worked");
        });
    }
}