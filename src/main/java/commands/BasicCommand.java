package commands;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alexm on 12.11.2019.
 */
public interface BasicCommand {
    /**
 * The basic method for all mysqldaoimpl of the interface.
 *
 * @param request       - request which will be processed.
 * @return              - a page which user will be directed to.
 */
    String execute(HttpServletRequest request);
        }
