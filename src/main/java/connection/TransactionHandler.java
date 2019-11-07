package connection;

import constants.MessageConstants;
import exeptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by alexm on 07.11.2019.
 */
public class TransactionHandler {
    private final static Logger logger = Logger.getLogger(TransactionHandler.class);

    public static void runInTransaction(Transaction transaction) throws SQLException {
        Connection connection = Datasource.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            transaction.execute(connection);
            connection.commit();
            logger.info(MessageConstants.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DAOException e) {
            if (connection != null) {
                connection.rollback();
            }
            logger.error(MessageConstants.TRANSACTION_FAILED, e);
            throw new SQLException(e);
        } catch (NullPointerException e) {
            System.out.println("Ou sheet");
        } finally {
            Datasource.getInstance().closeConnection(connection);
        }
    }
}
