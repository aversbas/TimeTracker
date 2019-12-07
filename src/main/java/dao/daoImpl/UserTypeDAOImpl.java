package dao.daoImpl;

import connection.ConnectionFactory;
import connection.Datasource;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.idao.UserTypeDAO;
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
public class UserTypeDAOImpl implements UserTypeDAO {
    private static final Logger logger = Logger.getLogger(UserTypeDAOImpl.class);

    private volatile static UserTypeDAOImpl instance;

    private Datasource datasource;

    public UserTypeDAOImpl() {
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static UserTypeDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserTypeDAOImpl.class) {
                if (instance == null) {
                    instance = new UserTypeDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * This method creates and inserts an entity in a database table.
     *
     * @param userType   - the current user which has been created.
     */
    @Override
    public void add(UserType userType) throws DAOException {

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.ADD_USER_TYPE)){
            statement.setString(1, userType.getUserType());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param userType   - the current entity of user which will be updated.
     */
    @Override
    public void update(UserType userType) throws DAOException {

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.UPDATE_USER_TYPE_BY_ID)){
            statement.setString(1, userType.getUserType());
            statement.setString(2, String.valueOf(userType.getUserTypeId()));
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
             PreparedStatement statement = connection.prepareStatement(QueriesDB.DELETE_USER_TYPE_BY_ID)){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
    }

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id         - id number of the record (row) in the database table..
     * @return - an entity from a database table according to the incoming id number.
     */
    @Override
    public UserType getById(int id) throws DAOException {

        ResultSet resultSet = null;
        UserType userType = new UserType();
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.GET_USER_TYPE_BY_ID)){
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUserType(resultSet, userType);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return userType;
    }
    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param type         - type of the record (row) in the database table..
     * @return - an entity from a database table according to the incoming id number.
     */
    @Override
    public UserType getByType(String type) throws DAOException {

        ResultSet resultSet = null;
        UserType userType = new UserType();
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.GET_USER_TYPE_BY_TYPE)){
            statement.setString(1, type);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createUserType(resultSet, userType);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return userType;
    }

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @return - list of all entities from a database table.
     */
    @Override
    public List<UserType> getAll() throws DAOException {

        ResultSet resultSet = null;
        List<UserType> users = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.GET_ALL_USERS_TYPE)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(createUserType(resultSet, new UserType()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return users;
    }

    /**
     * An additional method.
     * This method creates entity of UserType class from data received from ResultSet.
     *
     * @param resultSet - a database result "row" with required values.
     * @param userType  - the entity of User with "null" value for setting corresponding values.
     * @return - created user with fields.
     * @throws SQLException
     */
    private UserType createUserType(ResultSet resultSet, UserType userType) throws SQLException {
        userType.setUserTypeId(resultSet.getLong(Parameters.USER_TYPE_ID));
        userType.setUserType(resultSet.getString(Parameters.USER_TYPE));
        return userType;
    }
}
