package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by alexm on 10.11.2019.
 */
public class ConnectionFactory {
    private static String url = "jdbc:mysql://localhost:3306/timeTracker";
    private static String user = "root";
    private static String password = "root";

    public static Connection getConnection(){
        Connection connection = null;
        try{


            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, password);
//            return connection;
//        }catch (Exception e){
//            System.out.println("EXCEPTION: " + e.getStackTrace());
//            e.getMessage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
