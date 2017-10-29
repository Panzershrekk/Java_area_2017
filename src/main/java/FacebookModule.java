import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBase;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.Iterator;
import java.util.Map;

public class FacebookModule extends Modules {

    private Facebook facebook;
    private String mode = "subscribe";
    private String verifyToken = "123456789";
    private String hub = "";
    private String msg = "";

    public FacebookModule() {

        setModuleName("Facebook");
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthAppId("127406281303958")
                    .setOAuthAppSecret("243701dadc53d3879683e0d8e2bff36c")
                    .setOAuthPermissions("publish_actions")
                    .setOAuthAccessToken("EAABz4BYjp5YBAH9BtbDtz3RqSQwmY8MSQ1mqPZBLanDDTmIrZCrMQwwlSb3XZCIv1x7RdX8pE083OJViJOJHYXFVZBmvSdxPoHRk644QHdeZBmFCPQlNhp5ZCmVpP5GXiQ6E4ZCUdUcZCkOlO2tWZBlZAmhHPwzjjNUpidfp9UAK0j8cweMcZCHSF437LdY5RNnTBLv9FrIvTkHZBN4EjBZCixZAdZBMoHSHel5KvbzUrxqpNpmxxTy8EVGTA7C");
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
        return (hub);
    }

    public String postData(Request request, Response response) {
        try {
            JSONObject tmp = new JSONObject(request.body());
            String entry = (String)tmp.get("entry").toString();
            return (entry);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        response.status(200);
        return (msg);
    }
}
