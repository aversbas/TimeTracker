package dao.idao;

import entity.User;
import exeptions.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alexm on 06.11.2019.
 */
public interface UserDAO extends AbstractDAO<User> {

    /**
     * This method checks the availability of the <i>login</i> and <i>password</i> in the <i>users</i> database table.
     *
     * @param login         - entered <i>login</i> filed of the user.
     * @param password      - entered <i>password</i> field of the user.
     * @return              - boolean value of the condition:
     *                          returns "true" if the incoming data correspond to the record of the database table;
     *                          returns "false" if the incoming data do not correspond to the record of the database table.
     */
    boolean isAuthorized(String login, String password) throws DAOException;

    /**
     * This method reads data from <i>users</i> database table, creates and returns User object according to the entered login.
     *
     * @param login         - entered <i>login</i>.
     * @return              - User object.
     */
    User getByLogin(String login) throws DAOException;

    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id            - id number of the current entity which will be deleted.
     */
    void deleteById(int id) throws DAOException;

    /**
     * This method check the uniqueness of the user.
     *
     * @param login         - entered <i>login</i>.
     * @return              - boolean value of the condition:
     *                          returns "false" if the incoming data correspond to the record of the database table;
     *                          returns "true" if the incoming data do not correspond to the record of the database table.
     * @throws DAOException
     */
    boolean checkUniqueUser(String login) throws DAOException;

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id            - id number of the record (row) in the database table..
     * @return              - an entity from a database table according to the incoming id number.
     */
    User getById(String  id) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param user          - the current entity of user which will be updated.
     */
    void update(User user) throws DAOException;

    /**
     * This method creates entity of User class from data received from ResultSet.
     *
     * @param resultSet - a database result "row" with required values.
     * @param user      - the entity of User with "null" value for setting corresponding values.
     * @return - created user with fields.
     * @throws SQLException
     */
    User createUser(ResultSet resultSet, User user) throws SQLException;
}
