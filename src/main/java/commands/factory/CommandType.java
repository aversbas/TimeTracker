package commands.factory;

import commands.BasicCommand;
import commands.implemets.DefaultCommand;
import commands.implemets.admin.AddActivityToUserCommand;
import commands.implemets.admin.BackAdminCommand;
import commands.implemets.admin.CreateActivityCommand;
import commands.implemets.admin.OverviewClientCommand;
import commands.implemets.client.StartTimeCommand;
import commands.implemets.client.StopTimeCommand;
import commands.implemets.user.*;

/**
 * Created by alexm on 12.11.2019.
 */
public enum CommandType {
    /*user commands*/
    LOGIN, LOGOUT, REGISTRATION, GOTOREGISTRATION, UPDATECLIENT, BACK, DEFAULT,

    /*admin commands*/
    CREATEACTIVITY, OVERVIEWCLIENT, BACKADMIN, ADDACTIVITY,

    /*client commands*/
    STARTTIME, STOPTIME;

    /**
     * This method directs the control to the corresponding class. The transfer of the control to the corresponding class
     * is carried out by determining the value of the parameter "command" from request. The current request is generated
     * from the "form" placed on the jsp page.
     *
     * @return      - the current class will be processed.
     */
    public BasicCommand getCurrentCommand() {
        switch (this) {
            case LOGIN:
                return new LoginCommand();
            case LOGOUT:
                return new LogoutCommand();
            case REGISTRATION:
                return new RegistrationCommand();
            case GOTOREGISTRATION:
                return new GotoRegistrationCommand();
            case UPDATECLIENT:
                return new UpdateClientCommand();
            case BACK:
                return new BackCommand();
            case CREATEACTIVITY:
                return new CreateActivityCommand();
            case OVERVIEWCLIENT:
                return new OverviewClientCommand();
            case BACKADMIN:
                return new BackAdminCommand();
            case ADDACTIVITY:
                return new AddActivityToUserCommand();
            case STARTTIME:
                return new StartTimeCommand();
            case STOPTIME:
                return new StopTimeCommand();
            case DEFAULT:
                return new DefaultCommand();
            default:
                return new DefaultCommand();
        }
    }
}
