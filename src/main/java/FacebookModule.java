import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBase;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookModule extends Modules {

    private Facebook facebook;

    public FacebookModule() {

        setModuleName("Facebook");
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthAppId("748909205316555")
                    .setOAuthAppSecret("2a0e3d5c7be1b2453e1e4ecb9fd0e040")
                    .setOAuthPermissions("publish_actions")
                    .setOAuthAccessToken("EAAKpIQMm48sBADc5PM7TeTGEehGzd4yM94kl3B7wsgO5VEfE4p39PmoIkZA5NBrZAW5ibO0xMqMUrwVbV2jmeJzhs1xFr9z40RisatnbaHhz46qGclEa5MEzZCR3Q7unyuZBI3V3jEtzGGUsyaQ2MxDbtUaMFuHj3deZAgWiyvgEsnrRzVUcj7P15xu8eWOkSUTrZBWZARLcOorB9H4oYw0XPtTr9xZBCpsHbKnyXfyFiQZDZD");
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
}
