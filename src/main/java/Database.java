import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    public static Connection createConnection()
    {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/customers"; //MySQL URL followed by the database name
        String username = "root"; //MySQL username
        String password = "root"; //MySQL password
        try
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver"); //loading MySQL drivers
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            con = DriverManager.getConnection(url, username, password); //attempting to connect to MySQL database
            System.out.println("Printing connection object "+con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public static void Subscribe (String fullName, String mail, String userName, String password)
    {
        MD5Encrypt md5 = new MD5Encrypt();
        password = md5.Encrypt(password);

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = createConnection();
            String query = "insert into users(S1No,fullName,Email,userName,password) values (NULL,?,?,?,?)"; //Insert user details into the table 'USERS'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, mail);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, password);
            int i= preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}