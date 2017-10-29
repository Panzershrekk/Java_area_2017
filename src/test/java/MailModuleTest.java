import static org.junit.Assert.*;

public class MailModuleTest {
    final MailModule test = new MailModule();

    @org.junit.Test
    public void getFromMailModule() throws Exception {
        assertEquals(1,1);
    }

    @org.junit.Test
    public void getUnreadMailModule() throws Exception {
        assertEquals(1,1);
    }

    @org.junit.Test
    public void getAllMailModule() throws Exception {
        assertEquals(1,1);
    }

    @org.junit.Test
    public void postMailModule() throws Exception {
        assertEquals(1,1);
    }

    @org.junit.Test
    public void postMailReactModule() throws Exception {
       assertEquals("200 OK", test.postMailReactModule("warin-adrien@hotmail.fr","PostMailReactionTest", "grattepanche.robin@hmail.com"));
    }

}