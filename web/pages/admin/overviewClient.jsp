<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spec" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin page</title>
    <link rel="stylesheet" type="text/css" href="/css/adminMain.css"/>
</head>
<body>

<div class="wrapperUserData">
    <%--table overview user activities--%>
    <fieldset>
        <legend align="center"> Client <c:out value="${sessionScope.overviewUser.firstName}
                                                ${sessionScope.overviewUser.surName}"/></legend>
        <div class="activityInfoForm">
            <table>
                <col width="200">
                <col width="150">
                <col width="100">
                <col width="150">
                <col width="200">
                <tr>
                    <th align="left">ACTIVITIES</th>
                    <th align="left">STATUS</th>
                    <th align="left">TIME</th>
                    <th align="center">ACTION</th>
                    <th align="left">NOTICE</th>
                </tr>
                <c:forEach items="${sessionScope.trackingList}" var="tracking">
                    <c:set var="userId" value="${sessionScope.overviewUser.userId}" />
                    <c:if test="${tracking.user.userId==userId}">
                        <tr>
                            <td>
                                <c:out value="${tracking.activity.activityName}"/>
                            </td>
                            <td>
                                <c:out value="${tracking.status}"/>
                            </td>
                            <td>
                                <c:out value="${tracking.elapsedTime}"/>
                            </td>
                            <td>
                                <c:set var="status" value="${tracking.status}"/>
                                <c:if test="${status=='FINISHED'}">
                                    <form class="formElement" name="actionForm" method="POST"
                                          action="controller">
                                        <input type="hidden" name="command" value="removeAct"/>
                                        <input class="buttonElement" type="submit" value="remove"/>
                                    </form>
                                </c:if>
                            </td>
                            <td>
                                <c:set var="request" value="${tracking.userRequest}"/>
                                <c:if test="${request=='REMOVE'}">
                                    waiting for admin response...
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </div>
    </fieldset>
    <%--Table available activity--%>
    <div class="wrapperTableActivity">
        <fieldset>
            <legend align="center">AVAILABLE ACTIVITIES</legend>
            <div class="activityInfoForm">
                <table style=width:330px>
                    <col width="100">
                    <c:forEach items="${sessionScope.activityAdminList}" var="activity">
                        <tr>
                            <td>
                                <form class="formElement" name="actionForm" method="POST"
                                      action="controller">
                                    <div class="wrapperButtons">
                                        <input type="hidden" name="userId" value="${sessionScope.overviewUser.userId}"/>
                                        <input type="hidden" name="activityId" value="${activity.activityId}"/>
                                        <input type="hidden" name="command" value="addActivity"/>
                                        <input class="buttonElement" type="submit" value="add activity"
                                               style="height:20px; width:80px"/>
                                    </div>
                                </form>
                            </td>
                            <td>
                                <c:out value="${activity.activityName}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td>
                            <div>
                                </br>${sessionScope.operationMessage}
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
    </div>
</div>

<!--LOGOUT-->
<div class="logoutElement">
    <form name="logout" method="POST" action="controller">
        <input type="hidden" name="command" value="logout"/>
        <input type="submit" value="Log Out"/>
    </form>
</div>
<!--BACK-->
<div class="backElement">
    <form name="backForm" method="POST" action="controller">
        <input type="hidden" name="command" value="backAdmin"/>
        <input type="submit" value="Back"/>
    </form>
</div>
</body>
</html>