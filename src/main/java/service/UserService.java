package service;

import connection.Datasource;
import connection.TransactionHandler;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.idao.UserDAO;
import entity.Activity;
import entity.Tracking;
import entity.User;
import exeptions.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexm on 07.11.2019.
 */
public class UserService {
    private final static Logger logger = Logger.getLogger(UserService.class);
    private volatile static UserService instance;
//    private DaoFactory mySqlFactory;
    private UserDAO userDAO;

//    private UserService() {
//        mySqlFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
//        userDAO = mySqlFactory.getUserDao();
//    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    return instance = new UserService();
                }
            }
        }
        return instance;
    }

    /**
     * This method checks if the user's login and password are correct. This method implements work with transaction support.
     *
     * @param login    - incoming user's login.
     * @param password - incoming user's password.
     * @return - boolean value of the condition if the user is authorized or not.
     * @throws SQLException
     */
    public boolean checkUserAuthorization(String login, String password) throws SQLException {
        final boolean[] isAuthorized = new boolean[1];
        TransactionHandler.runInTransaction(connection ->
                isAuthorized[0] = userDAO.isAuthorized(login, password)
        );
        return isAuthorized[0];
    }

    /**
     * This method receives user object. This method implements work with transaction support.
     *
     * @param login - entered login.
     * @return - User object.
     */
    public User getUserByLogin(String login) throws SQLException {
        final User[] user = new User[1];
        TransactionHandler.runInTransaction(connection ->
                user[0] = userDAO.getByLogin(login)
        );
        return user[0];
    }

    /**
     * This method receives user object. This method implements work with transaction support.
     *
     * @param overviewUserId - entered id.
     * @return - User object.
     */
    public User getUserById(String overviewUserId) throws SQLException {
        final User[] user = new User[1];
        TransactionHandler.runInTransaction(connection ->
                user[0] = userDAO.getById(overviewUserId)
        );
        return user[0];
    }

    /**
     * This method updates user object. This method implements work with transaction support.
     *
     * @param user - an user which fields will be updated.
     * @throws SQLException
     */
    public void updateUser(User user) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                userDAO.update(user)
        );
    }

    /**
     * This method checks the uniqueness of the user. This method implements work with transaction support.
     *
     * @param user - an user object with fields will be checked.
     * @return - boolean value of the condition.
     * @throws SQLException
     */
    public boolean isUniqueUser(User user) throws SQLException {
        final boolean[] isUnique = new boolean[1];
        TransactionHandler.runInTransaction(connection ->
                isUnique[0] = userDAO.checkUniqueUser(user.getLogin())
        );
        return isUnique[0];
    }

    /**
     * This method registers new user of application in DB. This method implements work with transaction support.
     *
     * @param user - a new user which will be registered.
     * @throws SQLException
     */
    public void registerUser(User user) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                userDAO.add(user)
        );
    }

    /**
     * An additional accessory method that provides work with some attributes of the object of http session.
     * This method sets user's parameters to the session.
     *
     * @param session - an object of the current session.
     */
    public void setAttributeAdminToSession(User adminName, HttpSession session) {
        session.setAttribute(Parameters.ADMIN_NAME, adminName);
    }

    /**
     * An additional accessory method that provides work with some attributes of the object of http session.
     * This method sets user's parameters to the session.
     *
     * @param session - an object of the current session.
     */
    public void setAttributeOverviewUserToSession(User overviewUser, HttpSession session) {
        session.setAttribute(Parameters.OVERVIEWUSER, overviewUser);
    }

    /**
     * An additional accessory method that provides work with some attributes of the object of http session.
     * This method sets user's parameters to the session.
     *
     * @param session - an object of the current session.
     */
    public void setAttributeClientToSession(User clientUser, HttpSession session) {
        session.setAttribute(Parameters.CLIENTUSER, clientUser);
    }

    /**
     * An additional overloaded method that provides work with some attributes of the object of http session.
     * This method sets user's parameters to the session.
     *
     * @param session - an object of the current session.
     */
    public void setAttributeToSession(List<Activity> activityAdminList, List<Tracking> trackingList,
                                      List<User> userList, HttpSession session) {
        session.setAttribute(Parameters.ACTIVITY_ADMIN_LIST, activityAdminList);
        session.setAttribute(Parameters.TRACKING_LIST, trackingList);
        session.setAttribute(Parameters.USER_LIST, userList);
    }

    /**
     * An additional overloaded method that provides work with some attributes of the object of http session.
     * This method sets user's parameters to the session.
     *
     * @param session - an object of the current session.
     */
    public void setAttributeToSession(List<Activity> activityAdminList, HttpSession session) {
        session.setAttribute(Parameters.ACTIVITY_ADMIN_LIST, activityAdminList);
    }

    /**
     * This method receives all client names from database. This method implements work with transaction support.
     *
     * @return - a list of activity names from the database.
     * @throws SQLException
     */
//    public List<String> getAllClientNames() throws SQLException {
//        final List<String>[] clientNameList = new List[1];
//        TransactionHandler.runInTransaction(connection ->
//                clientNameList[0] = getAllClientNames()
//        );
//        return clientNameList[0];
//    }

    /**
     * This method receives all Users from database. This method implements work with transaction support.
     *
     * @return - a list of activities from the database.
     * @throws SQLException
     */
    public List<User> getAllUser() throws SQLException {
        final List<User>[] userList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                userList[0] = userDAO.getAll()
        );
        return userList[0];
    }

    /**
     * This method reads and returns users names from database table.
     *
     * @return - list of all entities from a database table.
     */
    public List<String> getAllUsersNames() throws DAOException {

        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        List<String> userNames;
        try (Connection connection = Datasource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(QueriesDB.GET_ALL_USERS)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(userDAO.createUser(resultSet, new User()));
            }
            userNames = usersArrayGetNames(users);
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return userNames;
    }

    /**
     * This method reads and returns clients names from database table.
     *
     * @return - list of all entities from a database table.
     */
    public List<String> getAllClientNames() throws DAOException {

        ResultSet resultSet = null;
        List<User> clients = new ArrayList<>();
        List<String> userNames;
        try(Connection connection = Datasource.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(QueriesDB.GET_ALL_CLIENTS)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                clients.add(userDAO.createUser(resultSet, new User()));
            }
            userNames = usersArrayGetNames(clients);
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return userNames;
    }

    List<String> usersArrayGetNames(List<User> activities) {
        List<String> clientNames = new ArrayList<>();
        for (int i = 0; i < activities.size(); i++) {
            clientNames.add(activities.get(i).getFirstName() + " " + activities.get(i).getSecondName());
        }
        return clientNames;
    }

}