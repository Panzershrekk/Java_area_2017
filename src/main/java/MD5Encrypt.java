
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encrypt {

    public MD5Encrypt() {
    }

    public String Encrypt(String password) {

        String original = password;
        MessageDigest md = null;
        try {

            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException x) {
            System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
        }
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        password = sb.toString();
        return (password);
    }
}