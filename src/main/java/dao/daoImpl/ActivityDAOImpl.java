package dao.daoImpl;

import dao.idao.ActivityDAO;
import entity.Activity;
import exeptions.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexm on 06.11.2019.
 */
public class ActivityDAOImpl implements ActivityDAO {

    private volatile static ActivityDAOImpl instance;

    public static ActivityDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserDAOImpl.class) {
                if (instance == null) {
                    instance = new ActivityDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(Activity entity, Connection connection) throws DAOException {

    }

    @Override
    public List<Activity> getAll(Connection connection) throws DAOException {
        return null;
    }

    @Override
    public void deleteById(int id, Connection connection) throws DAOException {

    }

    @Override
    public Activity getByName(String name, Connection connection) throws DAOException {
        return null;
    }

    @Override
    public Activity getById(String id, Connection connection) throws DAOException {
        return null;
    }

    @Override
    public void update(Activity activity, Connection connection) throws DAOException {

    }

    @Override
    public boolean checkUniqueActivity(String activityName, Connection connection) throws DAOException {
        return false;
    }

    @Override
    public Activity createActivity(ResultSet resultSet, Activity activity) throws SQLException {
        return null;
    }
}
