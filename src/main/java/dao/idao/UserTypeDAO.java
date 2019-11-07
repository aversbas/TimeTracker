package dao.idao;

import entity.UserType;
import exeptions.DAOException;

import java.sql.Connection;

/**
 * Created by alexm on 06.11.2019.
 */
public interface UserTypeDAO extends AbstractDAO<UserType> {

    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id            - id number of the current entity which will be deleted.
     * @param connection    - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void deleteById(int id, Connection connection) throws DAOException;

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id            - id number of the record (row) in the database table..
     * @param connection    - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return              - an entity from a database table according to the incoming id number.
     */
    UserType getById(int id, Connection connection) throws DAOException;

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param type            - id number of the record (row) in the database table..
     * @param connection    - the current connection to a database. Transmitted from the service module to provide transactions.
     * @return              - an entity from a database table according to the incoming id number.
     */
    UserType getByType(String type, Connection connection) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param userType          - the current entity of user which will be updated.
     * @param connection    - the current connection to a database. Transmitted from the service module to provide transactions.
     */
    void update(UserType userType, Connection connection) throws DAOException;

}
