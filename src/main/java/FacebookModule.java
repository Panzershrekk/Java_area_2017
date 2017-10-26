import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBase;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookModule {

    Facebook facebook;

    public FacebookModule() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthAppId("748909205316555")
                .setOAuthAppSecret("2a0e3d5c7be1b2453e1e4ecb9fd0e040")
                .setOAuthPermissions("publish_actions")
            .setOAuthAccessToken("EAAKpIQMm48sBAOmcOmeezl8YaEHP9H8HAmnyDkX7jredmlr2fX2nGnjwfUzbgfKIYocOePygxT8aZAPhLKVDG8q3RFzP9aj7wqsDsznzLCtonlW5xcG9MBHvNWAeWZAg14opvEHmlN3ZA95iHSv7ASSHCeEhugZA3FvKXDy3QJOBsOSr7g7BET6hYWlHZAZAWmJk4AOXUrG2XTeZBWZCfcaWEpLN4fRekLrYWH9KueBZA4QZDZD");
        FacebookFactory fb = new FacebookFactory(cb.build());
        this.facebook = fb.getInstance();
        try
        {
            System.out.println(this.facebook.getName());
        } catch (FacebookException e)
        {
            e.printStackTrace();
        }
    }

    public String getPost()
    {
        String post= "y";
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

}
