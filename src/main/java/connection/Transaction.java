package connection;

import exeptions.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by alexm on 07.11.2019.
 */
public interface Transaction {
    void execute(Connection connection) throws SQLException, DAOException;
}
