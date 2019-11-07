package dao.idao;

import exeptions.DAOException;

import java.util.List;

/**
 * Created by alexm on 06.11.2019.
 */
public interface AbstractDAO<T> {
    /**
     * This method creates and inserts an entity in a database table.
     *
     * @param entity     - the current entity which has been created.
     */
    void add(T entity) throws DAOException;

    /**
     * This method reads and returns information from all records (rows) of a database table.
     *
     * @return - list of all entities from a database table.
     */
    List<T> getAll() throws DAOException;
}
