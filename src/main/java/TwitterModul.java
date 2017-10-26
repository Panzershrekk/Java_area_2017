import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

public class TwitterModul {

    private Twitter twitter;
    private ResponseList<Status>    timeline;

    public TwitterModul() {
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("hAVBTJykgQyF6bkxsSNmTs7mj")
                .setOAuthConsumerSecret("dnr2QSlGlq5dyiecgZqLDBdtqYpfXN7a5MCwH9AkgYAozgrBJ6")
                .setOAuthAccessToken("922446818033664001-wwrq7uhrWDJGdrWONt9W1n9208KrSER")
                .setOAuthAccessTokenSecret("4Yx5KgWPmgpzWQ2AEzN58bykrmPiMrZr9TSoYuKSH28hP");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        try {
            timeline = twitter.getUserTimeline();
        }
        catch (TwitterException e)
        {
            e.printStackTrace();
        }
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

    public void displayNewTweets() {
        try {
            ResponseList<Status> newTimeline = twitter.getUserTimeline();
            if (newTimeline.removeAll((timeline))) {
                if (newTimeline.isEmpty()) {
                    System.out.println("There is no new tweet");
                } else {
                    for (Status s :
                            newTimeline) {
                        System.out.println("New Tweet : " + s.getText());
                    }
                    timeline = twitter.getUserTimeline();
                }
            }
            //TwitterUtils.createStream(jssc);
        }
        catch (TwitterException e) {
            System.out.println(e);
        }
    }
}