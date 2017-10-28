import facebook4j.Facebook;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

public class TwitterModule extends Modules {

    private Twitter twitter;
    private TwitterFactory tf;
    private ResponseList<Status>    timeline;
    private TwitterStreamInit twitterListener;

    public TwitterModule() {
        setModuleName("Twitter");
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();

            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("hAVBTJykgQyF6bkxsSNmTs7mj")
                    .setOAuthConsumerSecret("dnr2QSlGlq5dyiecgZqLDBdtqYpfXN7a5MCwH9AkgYAozgrBJ6")
                    .setOAuthAccessToken("922446818033664001-wwrq7uhrWDJGdrWONt9W1n9208KrSER")
                    .setOAuthAccessTokenSecret("4Yx5KgWPmgpzWQ2AEzN58bykrmPiMrZr9TSoYuKSH28hP");
            tf = new TwitterFactory(cb.build());
            twitter = tf.getInstance();
            timeline = twitter.getUserTimeline();


            ConfigurationBuilder bc = new ConfigurationBuilder();

            bc.setDebugEnabled(true)
                    .setOAuthConsumerKey("hAVBTJykgQyF6bkxsSNmTs7mj")
                    .setOAuthConsumerSecret("dnr2QSlGlq5dyiecgZqLDBdtqYpfXN7a5MCwH9AkgYAozgrBJ6")
                    .setOAuthAccessToken("922446818033664001-wwrq7uhrWDJGdrWONt9W1n9208KrSER")
                    .setOAuthAccessTokenSecret("4Yx5KgWPmgpzWQ2AEzN58bykrmPiMrZr9TSoYuKSH28hP");
            twitterListener = new TwitterStreamInit();

            FilterQuery fq = new FilterQuery();
            fq.follow(twitter.getId());
            TwitterStream twitterStream = new TwitterStreamFactory(bc.build()).getInstance();
            twitterStream.addListener(twitterListener);
            twitterStream.filter(fq);

        }
        catch (Exception e)
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

    public void postTweet(String msg, List<Modules> Modules)
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

    public TwitterStreamInit getTwitterListener() {
        return twitterListener;
    }
}