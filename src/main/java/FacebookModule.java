import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBase;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookModule extends Modules {

    private String moduleName;
    private Facebook facebook;

    public FacebookModule() {

        setModuleName("Facebook");
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthAppId("748909205316555")
                    .setOAuthAppSecret("2a0e3d5c7be1b2453e1e4ecb9fd0e040")
                    .setOAuthPermissions("publish_actions")
                    .setOAuthAccessToken("EAAKpIQMm48sBACSKf6XFTtuBHQjsFJp4IbkKb9X24tW49zxrs7PGClkm43ctDVZChPktnZAL25BPbTfKknDy1sKGaTjHOXojayzaSTXrzRylleTSGmxZAZBtGyrZBrJrgaIXWm5jtWJWh4AXPbmy4P5aZBOLyw0eaE7fQIRfRS5agk4gsdNZAjH5Fy0BZAI3Jo6dLlx0d2ZCwECfuC275tkc7O4TbMl7gK0MeGIzZBoIen4xqbRLbFQl3D");
            FacebookFactory fb = new FacebookFactory(cb.build());
            this.facebook = fb.getInstance();
            System.out.println(this.facebook.getName());

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
}
