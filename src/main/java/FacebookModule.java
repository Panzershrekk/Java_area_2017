import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBase;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.org.json.JSONObject;
import spark.Request;
import spark.Response;

public class FacebookModule extends Modules {

    private Facebook facebook;
    private String mode = "suscribe";
    private String verifyToken = "123456789";
    private String hub = "";

    public FacebookModule() {

        setModuleName("Facebook");
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthAppId("748909205316555")
                    .setOAuthAppSecret("2a0e3d5c7be1b2453e1e4ecb9fd0e040")
                    .setOAuthPermissions("publish_actions")
                    .setOAuthAccessToken("EAAKpIQMm48sBAGlZA5Nrb6y7178GJkkIotp4ZABKifdy6nQFhFjbbXe08uZAvGRU3UxqnArXREaWkqIdXnV1qEoYp3FE0fX1BINnw5XCNBzGMkkXPj3w7DkgN1ySeYjfLkV8cH5sJlWG5NlG6L71Lf3Li4vlrrd8grV9GxXEjsNZBZB4dKZBoMlcJZCzJt0bpOf9NjV8a5JdKGn5JnzjfKeAwA7sSuKVePvWr4eOhTR3wZDZD");
            FacebookFactory fb = new FacebookFactory(cb.build());
            this.facebook = fb.getInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getPost()
    {
        String post= "";
        try {
            for (Friend p: facebook.getFriends())
                post += p.getName();
        }
        catch (FacebookException e) {
            e.printStackTrace();
        }
        return post;
    }

    public void postFacebook(String msg)
    {
        try {
            facebook.postStatusMessage(msg);
        }
        catch (FacebookException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteFacebook(String msg)
    {
        try {
            Post post = facebook.getPosts(msg).get(0);
            facebook.deletePost(post.getId());
        }
        catch (FacebookException e)
        {
            e.printStackTrace();
        }

    }

    public String getSuscribe(Request request, Response response) {
        if (request.queryParams("hub.mode").equals(mode) &&
                request.queryParams("hub.verify_token").equals(verifyToken)) {
            hub = request.queryParams("hub.challenge");
            return (hub);
        }
        response.status(200);
        return ("");
    }

    public String postData(Request request, Response response) {
        System.out.println(request.body());
        try {
            JSONObject jsonObj = new JSONObject(request.body());
            System.out.println("Json = " + jsonObj);
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }
        response.status(200);
        return (hub);
    }
}
