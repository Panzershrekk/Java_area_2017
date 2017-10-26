import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

public class TwitterModul {

    Twitter twitter;

    public TwitterModul() {
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("hAVBTJykgQyF6bkxsSNmTs7mj")
                .setOAuthConsumerSecret("dnr2QSlGlq5dyiecgZqLDBdtqYpfXN7a5MCwH9AkgYAozgrBJ6")
                .setOAuthAccessToken("922446818033664001-wwrq7uhrWDJGdrWONt9W1n9208KrSER")
                .setOAuthAccessTokenSecret("4Yx5KgWPmgpzWQ2AEzN58bykrmPiMrZr9TSoYuKSH28hP");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public String twitterTest() {
        return "NO THIS IS TWITTER";
    }

    public String getTweet()
    {
        String tweet= "";
        try {
            List<Status> status = twitter.getHomeTimeline();

            for (Status st : status) {
                tweet += st.getUser().getName() + "------" + st.getText() + "\n";
                //System.out.println(st.getUser().getName() + "------" + st.getText());
            }
        }
        catch (TwitterException e) {
            tweet += "ERROR";
        }
        return tweet;
    }

    public void postTweet(String msg)
    {
        try {
            List<Status> status = twitter.getHomeTimeline();
            System.out.println(msg);
            twitter.updateStatus(msg);
        }
        catch (TwitterException e) {
            System.out.println(e);
        }

    }
}