package dao.idao;

import entity.Tracking;
import entity.User;
import exeptions.DAOException;

import java.util.List;

/**
 * Created by alexm on 06.11.2019.
 */
public interface TrackingDAO extends AbstractDAO<Tracking> {

    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id            - id number of the current entity which will be deleted.
     */
    void deleteTrackingByUserId(int id) throws DAOException;
    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id            - id number of the current entity which will be deleted.
     */
    void deleteTrackingById(int id) throws DAOException;

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id            - id number of the record (row) in the database table..
     * @return              - an entity from a database table according to the incoming id number.
     */
    Tracking getTrackingById(String id) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param tracking   - the current entity of user which will be updated.
     */
    void updateTrackingById(String id, Tracking tracking) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param tracking      - the current entity of activity which will be updated.
     */
    void updateTrackingStatusAndTimeByID (Tracking tracking) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param tracking      - the current entity of activity which will be updated.
     */
    void updateTrackingStatusByID (Tracking tracking) throws DAOException;

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @param user - user which activities will have retrieved from tracking table in DB.
     * @return - list of all entities from a database table.
     */
    List<Tracking> getTrackingByClientId(User user) throws DAOException;
}
