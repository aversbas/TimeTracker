package commands.implemets.user;

import commands.BasicCommand;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entity.User;
import manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import service.UserService;
import util.RequestParameterIdentifier;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Created by alexm on 12.11.2019.
 */
public class UpdateClientCommand implements BasicCommand {
    private final static Logger logger = Logger.getLogger(UpdateClientCommand.class);

    /**
     * This method describes actions of update information about user.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        User user = RequestParameterIdentifier.getUserLoginPasswordFromRequest(request);
        try {
            user =  UserService.getInstance().getUserByLogin(user.getLogin());
            user = RequestParameterIdentifier.updateUserFromRequest(user, request);
            UserService.getInstance().updateUser(user);
            request.getSession().setAttribute(Parameters.SUCCESS_UPDATE, Parameters.TRUE);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.CLIENT_PAGE_PATH);
        } catch (SQLException e) {
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
