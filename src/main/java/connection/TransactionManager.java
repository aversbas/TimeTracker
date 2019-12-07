package connection;

import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by alexm on 11.11.2019.
 */
public class TransactionManager {
    private final Datasource datasource = Datasource.getInstance();
    private static TransactionManager instance;
    private final ThreadLocal<ConnectionHolder> currentConnection = new ThreadLocal<>();

    private final static Logger LOGGER = Logger.getLogger(TransactionManager.class);

    private TransactionManager() {
    }

    public static TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    /**
     * Provide connection to Database if not exists,
     * or invoke method to provide connection for currentConnection variable.
     *
     * @return Connection to Database
     */
    public ConnectionHolder getConnection() {
        if (currentConnection.get() == null) {
            return new ConnectionHolder(datasource.getConnection());
        } else {
            return provideConnection();
        }
    }

    /**
     * Starts transaction for currentConnection.
     *
     * @return Conection with active transaction.
     */
    public ConnectionHolder startTransaction() {
        if (currentConnection.get() != null) {
            throw new IllegalStateException();
        }
        return provideConnection();
    }

    /**
     * Commit transaction if success is true,
     * or rollback if success is false
     *
     * @param success State of transaction success.
     */
    public void commit(boolean success) {
        if (currentConnection.get() == null) {
            throw new IllegalStateException();
        }
        currentConnection.get().setTransactionActive(false);
        try {
            if (success) {
                currentConnection.get().commit();
            } else {
                LOGGER.info("Transaction rolled back");
                currentConnection.get().rollback();
            }
        } catch (SQLException e) {
            LOGGER.error("Can`t connect to DB", e);
        } finally {
            try {
                currentConnection.get().setAutoCommit(true);
                currentConnection.get().close();
            } catch (SQLException e) {
                LOGGER.error("Can`t connect to DB", e);
            }
        }
        currentConnection.set(null);
    }

    /**
     * Return connection from ThreadLocal variable,
     * or provide new connection and starts transaction.
     *
     * @return Connection to Database,
     */
    private ConnectionHolder provideConnection() {

        if (currentConnection.get() != null) {
            return currentConnection.get();
        }
        currentConnection.set(new ConnectionHolder(datasource.getConnection()));
        currentConnection.get().setTransactionActive(true);
        try {
            currentConnection.get().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Can`t connect to DB", e);
        }
        return currentConnection.get();
    }
}