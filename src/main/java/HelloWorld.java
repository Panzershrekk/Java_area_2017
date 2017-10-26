import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {
        TwitterModul twitos = new TwitterModul();

        get("/get/twitos", (request, response) -> {
            return twitos.getTweet();
        });

        get("/post/twitter/:msg", (request, response) -> {
            twitos.postTweet(request.params(":msg"));
            return "Worked";
        });
    }
}