package com.connection;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

public class FirstTwitterApp {

    Twitter twitter;

    public static void main() {
        FirstTwitterApp test = new FirstTwitterApp();
    }
    public FirstTwitterApp() {
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("hAVBTJykgQyF6bkxsSNmTs7mj")
                .setOAuthConsumerSecret("dnr2QSlGlq5dyiecgZqLDBdtqYpfXN7a5MCwH9AkgYAozgrBJ6")
                .setOAuthAccessToken("922446818033664001-wwrq7uhrWDJGdrWONt9W1n9208KrSER")
                .setOAuthAccessTokenSecret("4Yx5KgWPmgpzWQ2AEzN58bykrmPiMrZr9TSoYuKSH28hP");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        try {
            List<Status> status = twitter.getHomeTimeline();

            for (Status st : status) {
                System.out.println(st.getUser().getName() + "------" + st.getText());
            }
        }
        catch (TwitterException e) {
            System.out.println("Exceptions");
        }
    }

}
