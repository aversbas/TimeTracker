import dao.daoImpl.UserDAOImpl;
import exeptions.DAOException;

/**
 * Created by alexm on 10.11.2019.
 */
public class Main {
    public static void main(String[] args) throws DAOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.getAll().forEach(System.out::println);

//        UserTypeDAOImpl userTypeDAO = new UserTypeDAOImpl();
//        userTypeDAO.getAll().forEach(System.out::println);


    }
}
