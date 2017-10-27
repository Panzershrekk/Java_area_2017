import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailMessage;
import org.eclipse.jetty.server.Authentication;
import org.omg.CORBA.UserException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public interface UserService {

        public static void getMailByAdress(String host, String storeType, String user, String password, String from){
            try {
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);


            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);


            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);


             SearchTerm sender = new FromTerm(new InternetAddress(from));
             Message[] messages = emailFolder.search(sender);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];

                System.out.println("=================================");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

            } catch (NoSuchProviderException e) {
            e.printStackTrace();
            } catch (MessagingException e) {
            e.printStackTrace();
            } catch (Exception e) {
            e.printStackTrace();
            }
        }

        public static void getUnreadMail() throws Exception {

            Session session = Session.getDefaultInstance(new Properties( ));
            Store store = session.getStore("imaps");
            store.connect("imap.googlemail.com", 993, "grattepanche.robin@gmail.com", "azerty--66");
            Folder inbox = store.getFolder( "INBOX" );
            inbox.open( Folder.READ_ONLY );

            // Fetch unseen messages from inbox folder
            Message[] messages = inbox.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            // Sort messages from recent to oldest
            Arrays.sort( messages, (m1, m2 ) -> {
                try {
                    return m2.getSentDate().compareTo( m1.getSentDate() );
                } catch ( MessagingException e ) {
                    throw new RuntimeException( e );
                }
            } );

            for ( Message message : messages ) {
                System.out.println("---------------------------------");
                System.out.println("SendDate: " + message.getSentDate());
                System.out.println("Subject:" + message.getSubject());
                System.out.println("From:" + message.getFrom()[0]);
                System.out.println("Text:" + message.getContent().toString());
            }
        }

        public static void getAllMail(String host, String storeType, String user, String password) {
            try {

                //create properties field
                Properties properties = new Properties();

                properties.put("mail.pop3.host", host);
                properties.put("mail.pop3.port", "995");
                properties.put("mail.pop3.starttls.enable", "true");
                Session emailSession = Session.getDefaultInstance(properties);

                //create the POP3 store object and connect with the pop server
                Store store = emailSession.getStore("pop3s");

                store.connect(host, user, password);

                //create the folder object and open it
                Folder emailFolder = store.getFolder("INBOX");
                emailFolder.open(Folder.READ_ONLY);

                // retrieve the messages from the folder in an array and print it
                Message[] messages = emailFolder.getMessages();
                System.out.println("messages.length---" + messages.length);

                for (int i = 0, n = messages.length; i < n; i++) {
                    Message message = messages[i];

                    System.out.println("---------------------------------");
                    System.out.println("Email Number " + (i + 1));
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.println("Text: " + message.getContent().toString());

                }

                //close the store and folder objects
                emailFolder.close(false);
                store.close();

            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public static void sendMail(String to, String subject, String content){
            final String username = "grattepanche.robin@gmail.com";
            final String password = "azerty--66";

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("grattepanche.robin@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(content);

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
}
