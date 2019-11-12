package commands.implemets.user;

import commands.BasicCommand;
import constants.MessageConstants;
import constants.PathPageConstants;
import manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import servlet.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alexm on 12.11.2019.
 */
public class LogoutCommand implements BasicCommand {
    private final static Logger logger = Logger.getLogger(LogoutCommand.class);

    /**
     * This method describes the logon logic.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        Controller.flag = true;
        logger.info(MessageConstants.SUCCESS_LOGOUT);
        return ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
    }
}
