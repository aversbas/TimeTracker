package commands.implemets.admin;

import commands.BasicCommand;
import constants.MessageConstants;
import constants.Parameters;
import constants.PathPageConstants;
import entity.Activity;
import entity.ActivityStatus;
import entity.Tracking;
import entity.User;
import manager.ConfigManagerPages;
import org.apache.log4j.Logger;
import service.ActivityService;
import service.TrackingService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by alexm on 12.11.2019.
 */
public class AddActivityToUserCommand implements BasicCommand {
    private static final Logger logger = Logger.getLogger(CreateActivityCommand.class);

    /**
     * This method describes the adding new activities logic.
     * The method uses methods of the RequestParameterIdentifier and AdminService.
     *
     * @param request - request which will be processed.
     * @return - a page which user will be directed to.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(false);
        String activityId = request.getParameter(Parameters.ACTIVITY_ID);
        String overviewUserId = request.getParameter(Parameters.USER_ID);
        try {
            User overviewUser = UserService.getInstance().getUserById(overviewUserId);
            UserService.getInstance().setAttributeOverviewUserToSession(overviewUser, session);
            Activity addActivityToUser = ActivityService.getInstance().getActivityById(activityId);
            Tracking tracking = new Tracking(overviewUser, addActivityToUser, ActivityStatus.NEW_ACTIVITY,
                    null, "00:00:00",0L,0L,0L);
            TrackingService.getInstance().registerTracking(tracking);
            List<Tracking> trackingList = TrackingService.getInstance().getAllTracking();
            TrackingService.getInstance().setAttributeTrackingListToSession(trackingList, session);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ADMIN_PAGE_PATH_CLIENT_OVERVIEW);
            logger.info(MessageConstants.SUCCESS_ADDING_ACTIVITY);
        } catch (SQLException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageConstants.DATABASE_ACCESS_ERROR);
            page = ConfigManagerPages.getInstance().getProperty(PathPageConstants.ERROR_PAGE_PATH);
            logger.error(MessageConstants.DATABASE_ACCESS_ERROR);
        }
        return page;
    }
}
