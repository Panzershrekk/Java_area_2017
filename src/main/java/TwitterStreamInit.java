import facebook4j.api.FavoriteMethods;
import twitter4j.*;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StallWarning;
import twitter4j.StatusListener;

public class TwitterStreamInit implements StatusListener {
    FacebookModule facebookModule;
    MailModule mailModule;

    public void setFacebookModule(FacebookModule facebookModule) {
        this.facebookModule = facebookModule;
    }

    public void setMailModule(MailModule mailModule) {
        this.mailModule = mailModule;
    }

    @Override
    public void onStatus(Status status) {
        //System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
        mailModule.postMailReactModule("grattepanche.robin@gmail.com", "New Tweet incoming",
                "You got a notification !\n" + status.getText());
        facebookModule.postFacebook("From Twitter @" + status.getUser().getScreenName() + " - " + status.getText());
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
}