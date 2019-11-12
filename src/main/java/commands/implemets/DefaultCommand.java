package commands.implemets;

import commands.BasicCommand;
import constants.PathPageConstants;
import manager.ConfigManagerPages;
import servlet.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alexm on 12.11.2019.
 */
public class DefaultCommand implements BasicCommand {

    /**
     * The method describes action in the case if servlet receives a message without a command.
     * In this case user will be redirected to the main page of the application index.jsp,
     * which redirects user to the login page login.jsp.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to, in this case it will be the index.jsp.
     */
    @Override
    public String execute(HttpServletRequest request) {
        Controller.flag = true;
        return ConfigManagerPages.getInstance().getProperty(PathPageConstants.INDEX_PAGE_PATH);
    }
}
