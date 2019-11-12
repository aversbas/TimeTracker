package commands.implemets.user;

import commands.BasicCommand;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entity.Activity;
import entity.Tracking;
import entity.User;
import manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import service.ActivityService;
import service.TrackingService;
import service.UserService;
import util.RequestParameterIdentifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexm on 12.11.2019.
 */
public class LoginCommand implements BasicCommand {
    private final static Logger logger = Logger.getLogger(LoginCommand.class);

    /**
     * This method describes the logon logic. The method uses methods of the RequestParameterIdentifier and AdminService
     * classes and works according to the following steps:
     * - getting an user object from request object using login and password saved in the corresponding request
     * object using the <i>getUserLoginPasswordFromRequest(...)<i/> method;
     * - checking user's authorization using the <i>checkUserAuthorization(...)</i> method;
     * - if the user is authorized the user's are created using the <i>getUserByLogin(...)<i/> method;
     * - generating the page according to the user's type (client or admin).
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        User user = RequestParameterIdentifier.getUserLoginPasswordFromRequest(request);
        HttpSession session = request.getSession(false);
        try {
            if (UserService.getInstance().checkUserAuthorization(user.getLogin(), user.getPassword())) {
                List<Activity> activityAdminList = ActivityService.getInstance().getAllActivities();
                List<Tracking> trackingList = TrackingService.getInstance().getAllTracking();
                List<User> userList = UserService.getInstance().getAllUser();
                user = UserService.getInstance().getUserByLogin(user.getLogin());
                UserService.getInstance().setAttributeToSession(activityAdminList, trackingList, userList, session);
                switch (user.getUserType().getUserType()) {
                    case "admin":
                        UserService.getInstance().setAttributeAdminToSession(user, session);
                        page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
                        break;
                    case "client":
                        UserService.getInstance().setAttributeClientToSession(user, session);
                        page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
                        break;
                    default:
                        page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
                        break;
                }
                logger.info(MessageConstants.SUCCESS_LOGIN);
            } else {
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_PASSWORD, MessageConstants.WRONG_LOGIN_OR_PASSWORD);
            }
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
