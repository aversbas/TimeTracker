package commands.implemets.user;

import commands.BasicCommand;
import constants.PathPageConstants;
import manager.ConfigManagerPages;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alexm on 12.11.2019.
 */
public class GotoRegistrationCommand implements BasicCommand {

    /**
     * This method describes action to redirect guest to the registration page.
     *
     * @param request   - request which will be processed.
     * @return          - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        return ConfigManagerPages.getInstance().getProperty(PathPageConstants.REGISTRATION_PAGE_PATH);
    }
}
