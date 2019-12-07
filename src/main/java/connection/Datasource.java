package connection;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class Datasource {
    private static final Logger LOGGER = Logger.getLogger(Datasource.class);
    private static Datasource instance;

    private DataSource dataSource;

    private Datasource() {
        init();
    }

    private void init() {
        try {
            /**
             * Get initial context that has references to all configurations and
             * resources defined for this web application.
             */
            Context initContext = new InitialContext();
            /**
             * Get Context object for all environment naming (JNDI), such as
             * Resources configured for this web application.
             */
            Context envContext = (Context) initContext.lookup("java:comp/env/");
            /**
             * Get the data source for the MySQL to request a connection.
             */
            dataSource = (DataSource) envContext.lookup("jdbc/timeTracker");
        } catch (NamingException e) {
            LOGGER.error(e);
        }
    }

    public static Datasource getInstance() {
        if (instance == null) {
            instance = new Datasource();
        }
        return instance;
    }

    /**
     * @return Connection from Connection pool
     */
    public Connection getConnection() {
        Connection connection = null;
        LOGGER.info("Return the object of DataSource class.");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Exception in getting connection");
        }
        return connection;
    }

    public void closeConnection(Connection connection){
        try{
            if(connection != null){
                connection.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
