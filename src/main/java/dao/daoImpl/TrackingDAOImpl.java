package dao.daoImpl;

import connection.Datasource;
import constants.MessageConstants;
import constants.Parameters;
import constants.QueriesDB;
import dao.idao.TrackingDAO;
import entity.*;
import exeptions.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexm on 06.11.2019.
 */
public class TrackingDAOImpl implements TrackingDAO {
    private static final Logger logger = Logger.getLogger(TrackingDAOImpl.class);

    private volatile static TrackingDAOImpl instance;

    private Datasource datasource;

    private TrackingDAOImpl() {
    }

    /**
     * Singleton realization with "Double Checked Locking & Volatile" principle for high performance and thread safety.
     *
     * @return - an instance of the class.
     */
    public static TrackingDAOImpl getInstance() {
        if (instance == null) {
            synchronized (TrackingDAOImpl.class) {
                if (instance == null) {
                    instance = new TrackingDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id         - id number of the current entity which will be deleted.
     */
    @Override
    public void deleteTrackingByUserId(int id) throws DAOException {

        try (Connection conn = datasource.getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.DELETE_TRACKING_BY_USER_ID)){
            statement.setLong(1, id);
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
    public void deleteTrackingById(int id) throws DAOException {

        try (Connection conn = datasource.getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.DELETE_TRACKING_BY_ID)){
            statement.setLong(1, id);
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
    public Tracking getTrackingById(String id) throws DAOException {

        ResultSet resultSet = null;
        Tracking tracking = new Tracking();
        try (Connection conn = datasource.getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.GET_TRACKING_BY_ID)){
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                createTracking(resultSet, tracking);
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return tracking;
    }

    /**
     * An additional method.
     * This method creates entity of Tracking class from data received from ResultSet.
     *
     * @param resultSet - a database result "row" with required values.
     * @param tracking  - the entity of User with "null" value for setting corresponding values.
     * @return - created user with fields.
     * @throws SQLException
     */
    private Tracking createTracking(ResultSet resultSet, Tracking tracking) throws SQLException {
        tracking.setTrackingId(resultSet.getLong(Parameters.TRACKING_ID_DB));
        tracking.setUser(UserDAOImpl.getInstance().createUser(resultSet, new User()));
        tracking.setActivity(ActivityDAOImpl.getInstance().createActivity(resultSet, new Activity()));
        switch (resultSet.getString(Parameters.STATUS_NAME_DB)) {
            case Parameters.NEW_ACTIVITY_DB:
                tracking.setStatus(ActivityStatus.NEW_ACTIVITY);
                break;
            case Parameters.IN_PROGRESS_DB:
                tracking.setStatus(ActivityStatus.IN_PROGRESS);
                break;
            case Parameters.PAUSE:
                tracking.setStatus(ActivityStatus.PAUSE);
                break;
            case Parameters.FINISHED:
                tracking.setStatus(ActivityStatus.FINISHED);
                break;
            case Parameters.STOP:
                tracking.setStatus(ActivityStatus.STOP);
                break;
        }
        String userRequest = resultSet.getString(Parameters.USER_REQUEST_NAME_DB);
        if (!resultSet.wasNull()) {
            switch (userRequest) {
                case Parameters.ADD:
                    tracking.setUserRequest(UserRequest.ADD);
                    break;
                case Parameters.REMOVE:
                    tracking.setUserRequest(UserRequest.REMOVE);
                    break;
            }
        } else {
            tracking.setUserRequest(null);
        }
        tracking.setElapsedTime(resultSet.getString(Parameters.TIME));
        tracking.setTimeStart(resultSet.getLong(Parameters.TIME_START_DB));
        tracking.setTimeStop(resultSet.getLong(Parameters.TIME_STOP_DB));
        tracking.setDifferenceTime(resultSet.getLong(Parameters.DIFFERENCE_TIME_DB));
        return tracking;
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param tracking   - the current entity of user which will be updated.
     */
    @Override
    public void updateTrackingStatusAndTimeByID(Tracking tracking) throws DAOException {

        try (Connection conn = datasource.getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.UPDATE_TRACKING_STATUS_AND_TIME_BY_ID)){

            statement.setString(1, tracking.getStatus().toString());
            statement.setString(2, tracking.getElapsedTime());
            statement.setString(3, tracking.getTrackingId().toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param tracking   - the current entity of user which will be updated.
     */
    @Override
    public void updateTrackingById(String id, Tracking tracking) throws DAOException {

        try (Connection conn = datasource.getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.UPDATE_TRACKING_TIME)){
            Integer status = null;
            switch (tracking.getStatus().toString().toLowerCase()) {
                case Parameters.NEW_ACTIVITY_DB:
                    status=1;
                    break;
                case Parameters.IN_PROGRESS_DB:
                    status=2;
                    break;
                case Parameters.PAUSE:
                    status=3;
                    break;
                case Parameters.FINISHED:
                    status=4;
                    break;
                case Parameters.STOP:
                    status=5;
                    break;
            }
            statement.setInt(1, status);
            statement.setString(2, tracking.getElapsedTime());
            statement.setString(3, tracking.getTimeStop().toString());
            statement.setString(4, tracking.getDifferenceTime().toString());
            statement.setString(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } catch (NullPointerException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
    }

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param tracking   - the current entity of user which will be updated.
     */
    @Override
    public void updateTrackingStatusByID(Tracking tracking) throws DAOException {

        try (Connection conn = datasource.getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.UPDATE_TRACKING_STATUS_BY_ID)){
            statement.setString(1, tracking.getStatus().toString());
            statement.setString(3, tracking.getTrackingId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
    }

    /**
     * This method creates and inserts an entity in a database table.
     *
     * @param tracking   - the current user which has been created.

     */
    @Override
    public void add(Tracking tracking) throws DAOException {

        try (Connection conn = datasource.getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.ADD_TRACKING)){

            statement.setString(1, String.valueOf(tracking.getUser().getUserId()));
            statement.setString(2, String.valueOf(tracking.getActivity().getActivityId()));
            statement.setString(3, "1");
            statement.setNull(4, Types.VARCHAR);
            statement.setString(5, String.valueOf(tracking.getElapsedTime()));
            statement.setString(6, "0");
            statement.setString(7, "0");
            statement.setString(8, "0");
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        } catch (NullPointerException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
    }

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @return - list of all entities from a database table.
     */
    @Override
    public List<Tracking> getAll() throws DAOException {

        ResultSet resultSet = null;
        List<Tracking> tracking = new ArrayList<>();
        try (Connection conn = datasource.getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.GET_ALL_TRACKING)){
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tracking.add(createTracking(resultSet, new Tracking()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return tracking;
    }

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @param user       - user which rows will have retrieved from tracking table in DB.
     * @return - list of all entities from a database table.
     */
    @Override
    public List<Tracking> getTrackingByClientId(User user) throws DAOException {

        ResultSet resultSet = null;
        List<Tracking> trackingList = new ArrayList<>();

        try (Connection conn = datasource.getConnection();
             PreparedStatement statement = conn.prepareStatement(QueriesDB.GET_ALL_TRACKING_BY_CLIENT_ID)){
            statement.setString(1, String.valueOf(user.getUserId()));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trackingList.add(createTracking(resultSet, new Tracking()));
            }
        } catch (SQLException e) {
            logger.error(MessageConstants.EXECUTE_QUERY_ERROR, e);
            throw new DAOException(MessageConstants.EXECUTE_QUERY_ERROR, e);
        }
        return trackingList;
    }
}
