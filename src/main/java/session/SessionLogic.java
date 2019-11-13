package session;

import servlet.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by alexm on 07.11.2019.
 */
public class SessionLogic {
    private static final int timeLive = 30 * 60;

    public static HttpSession getSession(HttpServletRequest request) {
        HttpSession session = null;
        if (Controller.flag) {
            session = request.getSession();
            session.setMaxInactiveInterval(timeLive);
            Controller.flag = false;
        } else {
            session = request.getSession(false);
        }
        return session;
    }

    public static boolean isSessionNotAlive(HttpSession session) {
        if (session == null) {
            return true;
        }
        return false;
    }
}
