package commands.implemets.admin;

import commands.BasicCommand;
import constants.PathPageConstants;
import manager.ConfigManagerPages;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alexm on 12.11.2019.
 */
public class BackAdminCommand implements BasicCommand {

    /**
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH);
        return page;
    }
}
