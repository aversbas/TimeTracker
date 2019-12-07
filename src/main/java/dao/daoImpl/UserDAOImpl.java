package dao.daoImpl;

import connection.ConnectionFactory;
import connection.ConnectionHolder;
import connection.Datasource;
import connection.TransactionManager;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.idao.UserDAO;
import entity.User;
import entity.UserType;
import exeptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexm on 06.11.2019.
 */
public class UserDAOImpl  implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    private final TransactionManager TRANSACTION_MANAGER = TransactionManager.getInstance();

    private volatile static UserDAOImpl instance;

    private Datasource datasource;

    public UserDAOImpl() {
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static UserDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserDAOImpl.class) {
                if (instance == null) {
                    instance = new UserDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * This method creates and inserts an entity in a database table.
     *
     * @param user       - the current user which has been created.
     */
    @Override
    public void add(User user) throws DAOException {

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.ADD_USER_CLIENT)){
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setString(6, String.valueOf(2));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
    }

    /**
     * This method checks the availability of the <i>login</i> and <i>password</i> in the <i>users</i> database table.
     *
     * @param login      - entered <i>login</i> filed of the user.
     * @param password   - entered <i>password</i> field of the user.
     * @return - boolean value as a result:
     * returns "true" if the incoming data correspond to the record of the database table;
     * returns "false" if the incoming data do not correspond to the record of the database table.
     */
    @Override
    public boolean isAuthorized(String login, String password) throws DAOException {

        ResultSet resultSet = null;
        boolean isLogined = false;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.CHECK_AUTHORIZATION)){
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isLogined = true;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return isLogined;
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param user       - the current entity of user which will be updated.
     */
    @Override
    public void update(User user) throws DAOException {

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.UPDATE_USER_BY_ID)){

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setString(6, String.valueOf(user.getUserType()));
            statement.setLong(7, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
    }

    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id         - id number of the current entity which will be deleted.
     */
    @Override
    public void deleteById(int id) throws DAOException {

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.DELETE_USER_BY_ID)){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
    }

    /**
     * This method reads data from <i>users</i> database table, creates and returns User object according to the entered login.
     *
     * @param login      - entered <i>login</i>.
     * @return - User object.
     */
    @Override
    public User getByLogin(String login) throws DAOException {

        ResultSet resultSet = null;
        User user = new User();
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.GET_USER_BY_LOGIN)){
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUser(resultSet, user);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return user;
    }

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id         - id number of the record (row) in the database table..
     * @return - an entity from a database table according to the incoming id number.
     */
    @Override
    public User getById(String id) throws DAOException {

        ResultSet resultSet = null;
        User user = new User();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.GET_USER_BY_ID)){

            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUser(resultSet, user);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return user;
    }

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @return - list of all entities from a database table.
     */
    @Override
    public List<User> getAll() throws DAOException {

        ResultSet resultSet;
        List<User> users = new ArrayList<>();
        try (ConnectionHolder connectionHolder = TRANSACTION_MANAGER.getConnection();
             PreparedStatement statement = connectionHolder.prepareStatement(QueriesDB.GET_ALL_USERS)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createUser(resultSet, new User()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return users;
    }

    /**
     * An additional method.
     * This method creates entity of User class from data received from ResultSet.
     *
     * @param resultSet - a database result "row" with required values.
     * @param user      - the entity of User with "null" value for setting corresponding values.
     * @return - created user with fields.
     * @throws SQLException
     */
    public User createUser(ResultSet resultSet, User user) throws SQLException {
        user.setUserId(resultSet.getLong(Parameters.USER_ID_DB));
        user.setFirstName(resultSet.getString(Parameters.FIRST_NAME_DB));
        user.setSecondName(resultSet.getString(Parameters.SURNAME_DB));
        user.setEmail(resultSet.getString(Parameters.EMAIL_DB));
        user.setLogin(resultSet.getString(Parameters.LOGIN));
        user.setPassword(resultSet.getString(Parameters.PASSWORD));
        user.setUserType(new UserType(resultSet.getLong(Parameters.USER_TYPE_ID_DB)
                , resultSet.getString(Parameters.USER_TYPE_NAME_DB)));
        return user;
    }

    /**
     * This method check the uniqueness of the user.
     *
     * @param login      - entered <i>login</i>.
     * @return - boolean value of the condition.
     * @throws DAOException
     */
    @Override
    public boolean checkUniqueUser(String login) throws DAOException {

        boolean isUniqueUser = true;
        ResultSet resultSet = null;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.GET_USER_BY_LOGIN)){
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isUniqueUser = false;
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return isUniqueUser;
    }
}
