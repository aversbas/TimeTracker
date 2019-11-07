package service;

import connection.Datasource;
import connection.TransactionHandler;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.idao.ActivityDAO;
import entity.Activity;
import exeptions.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static entity.Activity.activityNameList;

/**
 * Created by alexm on 07.11.2019.
 */
public class ActivityService {

    private final static Logger logger = Logger.getLogger(ActivityService.class);
    private volatile static ActivityService instance;
//    private DaoFactory mySqlFactory;
    private ActivityDAO activityDAO;

//    private ActivityService() {
//        mySqlFactory = DaoFactory.getDaoFactory(DaoFactory.MYSQL);
//        activityDAO = mySqlFactory.getActivityDao();
//    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static ActivityService getInstance() {
        if (instance == null) {
            synchronized (ActivityService.class) {
                if (instance == null) {
                    return instance = new ActivityService();
                }
            }
        }
        return instance;
    }

    /**
     * This method add new activity in DB. This method implements work with transaction support.
     *
     * @param activity - a new user which will be registered.
     * @throws SQLException
     */
    public void createActivityDB(Activity activity) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                activityDAO.add(activity)
        );
    }

    /**
     * This method receives user object. This method implements work with transaction support.
     *
     * @param id - entered id.
     * @return - User object.
     */
    public Activity getActivityById(String id) throws SQLException {
        final Activity[] activity = new Activity[1];
        TransactionHandler.runInTransaction(connection ->
                activity[0] = activityDAO.getById(id, connection)
        );
        return activity[0];
    }

    /**
     * This method updates user object. This method implements work with transaction support.
     *
     * @param activity - an user which fields will be updated.
     * @throws SQLException
     */
    public void updateActivity(Activity activity) throws SQLException {
        TransactionHandler.runInTransaction(connection ->
                activityDAO.update(activity, connection)
        );
    }

    /**
     * This method receives all activities from database. This method implements work with transaction support.
     *
     * @return - a list of activities from the database.
     * @throws SQLException
     */
    public List<Activity> getAllActivities() throws SQLException {
        final List<Activity>[] activityList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                activityList[0] = activityDAO.getAll()
        );
        return activityList[0];
    }

    /**
     * This method receives all activity names from database. This method implements work with transaction support.
     *
     * @return - a list of activity names from the database.
     * @throws SQLException
     */
    public List<String> getAllActivityNames() throws SQLException {
        final List<String>[] activityNameList = new List[1];
        TransactionHandler.runInTransaction(connection ->
                activityNameList[0] = getAllNames()
        );
        return activityNameList[0];
    }

    /**
     * An additional accessory method that provides work with some attributes of the object of http session.
     * This methods sets activities's parameters to the session.
     *
     * @param session - an object of the current session.
     */
    public void setActivityToSession(Activity activity, HttpSession session) {
        session.setAttribute(Parameters.ACTIVITY, activity);

    }

    public void setActivityNameListToSession(List<String> activityList, HttpSession session) {
        session.setAttribute(Parameters.ACTIVITY_LIST, activityList);

    }

    /**
     * This method add activity names in array for further output in jsp page.
     *
     * @param activity - the current user which has been created.     *
     */
    public void addIfNewInListName(Activity activity) {
        if(activity.getDescription()=="") return;
        for (int i = 0; i < activityNameList.size(); i++) {
            if (activity.getDescription().equals(activityNameList.get(i))) {
                return;
            }
        }
        activityNameList.add(activity.getDescription());
    }

    /**
     * This method checks the uniqueness of the activity. This method implements work with transaction support.
     *
     * @param activity - an activity object with fields will be checked.
     * @return - boolean value of the condition.
     * @throws SQLException
     */
    public boolean isUniqueActivity(Activity activity) throws SQLException {
        final boolean[] isUnique = new boolean[1];
        TransactionHandler.runInTransaction(connection ->
                isUnique[0] = activityDAO.checkUniqueActivity(activity.getDescription(), connection)
        );
        return isUnique[0];
    }

    /**
     * This method checks the uniqueness of the activity name in user list. This method implements work with transaction support.
     *
     * @param activityName - an activity name with fields will be checked.
     * @param activityNameList - an activities names array with all names.
     * @return - boolean value of the condition.
     * @throws SQLException
     */
    public boolean isUniqueActivityName(String activityName, List<String> activityNameList) {
        for (int i = 0; i < activityNameList.size(); i++) {
            if (activityName.equals(activityNameList.get(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @return - list of all entities from a database table.
     */
    public List<String> getAllNames() throws DAOException {

        ResultSet resultSet = null;
        List<Activity> activities = new ArrayList<>();
        List<String> activityNames;
        try (Connection conn = Datasource.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.GET_ALL_ACTIVITIES)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                activities.add(activityDAO.createActivity(resultSet, new Activity()));
            }
            activityNames = activitiesArrayGetNames(activities);
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return activityNames;
    }

    List<String> activitiesArrayGetNames(List<Activity> activities) {
        List<String> activityNames = new ArrayList<>();
        for (int i = 0; i < activities.size(); i++) {
            activityNames.add(activities.get(i).getDescription());
        }
        return activityNames;
    }

}
