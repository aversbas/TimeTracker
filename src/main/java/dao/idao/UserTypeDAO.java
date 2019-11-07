package dao.idao;

import entity.UserType;
import exeptions.DAOException;

/**
 * Created by alexm on 06.11.2019.
 */
public interface UserTypeDAO extends AbstractDAO<UserType> {

    /**
     * This method deletes an existing record (row) in a database table.
     *
     * @param id            - id number of the current entity which will be deleted.
     */
    void deleteById(int id) throws DAOException;

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param id            - id number of the record (row) in the database table..
     * @return              - an entity from a database table according to the incoming id number.
     */
    UserType getById(int id) throws DAOException;

    /**
     * This method reads and returns information from a record (row) of a database table.
     *
     * @param type            - id number of the record (row) in the database table..
     * @return              - an entity from a database table according to the incoming id number.
     */
    UserType getByType(String type) throws DAOException;

    /**
     * This method updates an existing record (row) in a database table.
     *
     * @param userType          - the current entity of user which will be updated.
     */
    void update(UserType userType) throws DAOException;

}
