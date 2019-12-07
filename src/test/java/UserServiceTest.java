/**
 * Created by alexm on 15.11.2019.
 */

//import connection.ConnectionPool;
//import dao.idao.UserDAO;
//import entity.User;
//import entity.UserType;
//import exeptions.DAOException;
//import org.junit.Test;
//import service.UserService;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class UserServiceTest {
//
//    private UserService userService;
//    private UserDAO userDAOMock;
//    private ConnectionPool connectionPoolMock;
//
////    @Before
////    public void setUp() {
////        userDAOMock = mock(UserDAO.class);
////        userService = AdminService.getInstance();
////        userService.setUserDao(userDAOMock);
////        connectionPoolMock = mock(ConnectionPool.class);
////        userService.setConnectionPool(connectionPoolMock);
////    }
//
//    @Test
//    public void checkUserAuthorizationSuccess() throws SQLException, DAOException {
//        when(connectionPoolMock.getConnection()).thenReturn(mock(Connection.class));
//        when(userDAOMock.isAuthorized(eq("admin"),eq( "admin")))
//                .thenReturn(true);
//        boolean result = userService.checkUserAuthorization("admin", "admin");
//        assertTrue(result);
//    }
//
//    @Test (expected = SQLException.class)
//    public void checkUserAuthorizationException() throws SQLException, DAOException {
//        when(connectionPoolMock.getConnection()).thenReturn(mock(Connection.class));
//        when(userDAOMock.isAuthorized(eq("admin"),eq( "admin")))
//                .thenThrow(DAOException.class);
//        boolean result = userService.checkUserAuthorization("admin", "admin");
//        assertTrue(result);
//    }
//
//    @Test
//    public void getUserByLoginSuccess() throws Exception {
//        Connection connectionMock = mock(Connection.class);
//        User user = new User((long)3, "Ievgen", "Kopachev", "admin","admin", "admin",
//                new UserType((long) 5, "admin"));
//        when(connectionPoolMock.getConnection()).thenReturn(connectionMock);
//        when(userDAOMock.getByLogin(eq("admin")))
//                .thenReturn(user);
//        User result = userService.getUserByLogin("admin");
//        assertEquals(user, result);
//    }
//
//    @Test (expected = SQLException.class)
//    public void getUserByLoginFailed() throws Exception {
//        Connection connectionMock = mock(Connection.class);
//        when(connectionPoolMock.getConnection()).thenReturn(connectionMock);
//        when(userDAOMock.getByLogin(eq("admin")))
//                .thenThrow(DAOException.class);
//        User result = userService.getUserByLogin("admin");
//    }
//    @Test
//    public void updateUser() {
//    }
//
//    @Test
//    public void isUniqueUser() {
//    }
//
//    @Test
//    public void registerUser() {
//    }
//
//    @Test
//    public void setAttributeToSession() {
//    }
//}
