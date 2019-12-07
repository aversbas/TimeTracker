package dao;

import dao.daoImpl.ActivityDAOImpl;
import dao.daoImpl.TrackingDAOImpl;
import dao.daoImpl.UserDAOImpl;
import dao.idao.ActivityDAO;
import dao.idao.TrackingDAO;
import dao.idao.UserDAO;

/**
 * Created by alexm on 11.11.2019.
 */
public class DaoFactory {

    public ActivityDAO getActivityDao() {
        return ActivityDAOImpl.getInstance();
    }

    public TrackingDAO getTrackingDao() {
        return TrackingDAOImpl.getInstance();
    }

    public UserDAO getUserDao() {return UserDAOImpl.getInstance(); }
}
