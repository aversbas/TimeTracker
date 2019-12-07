<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix = "ex" uri = "WEB-INF/custom.tld"%>--%>
<html>
<head>
    <title>Clients account page</title>
    <link rel="stylesheet" type="text/css" href="/css/clientMain.css"/>
</head>
<body>
<div class="wrapperWelcomeInfo">
    <div class="welcomeElement">
        <jsp:useBean id="welcometext" scope="session" class="mybean.MyBean"/>
        This is bean answer:
        <jsp:getProperty name="welcometext" property="field"/>
        <c:out value="${welcometext.who}"/>
        <c:out value="${sessionScope.clientUser.firstName} ${sessionScope.clientUser.surName}"/>!
    </div>
</div>
<div class="wrapperPageData">
    <fieldset>
        <legend align="center">YOUR ACTIVITIES TRACKING</legend>
        <div class="activityInfoForm">
            <table>
                <tr>
                    <th align="left" width="320">ACTIVITIES</th>
                    <th align="left" width="150">STATUS</th>
                    <th>ACTION</th>
                    <th align="left" width="100">TIME</th>
                    <th align="left" width="300">NOTICE</th>
                </tr>
                <tr>
                    <c:forEach items="${sessionScope.trackingList}" var="tracking">
                    <c:set var="userId" value="${sessionScope.clientUser.userId}"/>
                    <c:if test="${tracking.user.userId==userId}">
                    <td>
                        <c:out value="${tracking.activity.activityName}"/>
                    </td>
                    <td>
                        <c:out value="${tracking.status}"/>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td>
                                        <%--<c:set var="userId" value="${sessionScope.clientUser.userId}"/>--%>
                                        <%--<c:if test="${tracking.user.userId==userId}">--%>
                                    <form class="formElement" name="actionForm" method="POST"
                                          action="controller">
                                        <div class="wrapperButtons">
                                            <input type="hidden" name="trackingId" value="${tracking.trackingId}"/>
                                            <input type="hidden" name="command" value="startTime"/>
                                            <input class="buttonElement" type="submit" value="start"/>
                                        </div>
                                    </form>
                                </td>
                                <td>
                                    <form class="formElement" name="actionForm" method="POST"
                                          action="controller">
                                        <div class="wrapperButtons">
                                            <input type="hidden" name="trackingId" value="${tracking.trackingId}"/>
                                            <input type="hidden" name="command" value="stopTime"/>
                                            <input class="buttonElement" type="submit" value="stop"/>
                                        </div>
                                    </form>
                                </td>
                                <td align="center">
                                    <form class="formElement" name="finishForm" method="POST"
                                          action="controller">
                                        <div class="wrapperButtons">
                                            <input type="hidden" name="command" value="finish"/>
                                            <input class="buttonElement" type="submit" value="finish"/>
                                        </div>
                                    </form>
                                </td>
                                <td align="center">
                                    <form class="formElement" name="finishForm" method="POST"
                                          action="controller">
                                        <div class="wrapperButtons">
                                            <input type="hidden" name="command" value="remove"/>
                                            <input class="buttonElement" type="submit" value="remove"/>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <c:out value="${tracking.elapsedTime}"/>
                    </td>
                    <td>
                            <%--<jsp:useBean id="now" class="java.util.Date"/>--%>
                            <%--<fmt:formatDate type="elapsedTime" value="${now}"/>--%>
                            <%--<c:out value="${now}"/>--%>
                    </td>
                </tr>
                </c:if>
                </c:forEach>
            </table>
            <table>
                <tr>
                    <td>
                        <form class="formElement" name="formElement" method="POST" action="controller">
                            <div class="wrapperButtons">
                                <input type="hidden" name="command" value="addActivityClient"/>
                                <input type="submit" value="add new activity" style="height:20px; width:110px"/>
                            </div>
                        </form>
                    </td>
                    <td>
                        waiting for admin response
                    </td>
                </tr>
            </table>
        </div>
    </fieldset>
</div>
<!--LOGOUT-->
<div class="logoutElement">
    <form name="logout" method="POST" action="controller">
        <input type="hidden" name="command" value="logout"/>
        <input type="submit" value="Log Out"/>
    </form>
</div>
</body>
</html>