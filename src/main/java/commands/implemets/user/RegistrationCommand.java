package commands.implemets.user;

import commands.BasicCommand;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entity.User;
import manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import service.UserService;
import servlet.Controller;
import util.RequestParameterIdentifier;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Created by alexm on 12.11.2019.
 */
public class RegistrationCommand implements BasicCommand {
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

    /**
     * This method describes the registration logic. The method uses methods of the RequestParameterIdentifier and AdminService.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        User user = RequestParameterIdentifier.getUserFromRequest(request);
        try {
            if (RequestParameterIdentifier.areFieldsFilled(request)) {
                if (UserService.getInstance().isUniqueUser(user)) {
                    UserService.getInstance().registerUser(user);
                    request.setAttribute(Parameters.SUCCESS_REGISTRATION, MessageConstants.SUCCESS_REGISTRATION);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.LOGIN_PAGE_PATH);
                    logger.info(MessageConstants.SUCCESS_REGISTRATION);
                    Controller.flag = true;
                } else {
                    request.setAttribute(Parameters.USER_UNIQUE_ERROR, MessageConstants.USER_EXISTS);
                    page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
                }
            } else {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageConstants.EMPTY_FIELDS);
                page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
            }
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
