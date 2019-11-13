<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Registration Page</title>
    <link rel="stylesheet" type="text/css" href="/css/registration.css"/>
</head>
<body>
<div style = "position:absolute; left:100px; top:200px;">
    <form name="registrationForm" method="POST" action="controller">
        <input type="hidden" name="command" value="registration"/>
        <fieldset>
            <legend align="center">Registration Form</legend>
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input class="inputElement" type="text" name="firstName" value=""/></td>
                </tr>
                <tr>
                    <td>Surname:</td>
                    <td><input class="inputElement" type="text" name="surname" value=""/></td>
                </tr>
                <tr>
                    <td>Login:</td>
                    <td><input class="inputElement" type="text" name="login" value=""/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input class="inputElement" type="password" name="password" value=""/></td>
                </tr>
            </table>
            <div class="wrapperButtons">
                <input class="buttonElement" type="submit" value="Register"/>
                <input class="buttonElement" type="reset" value="Reset"/>
                ${pageContext.session.setAttribute("backpage", "login")}
                <input type="button" value="Back" onclick='location.href="controller?command=back"'/>
            </div>
        </fieldset>
        <div class="operationMessageElement">
            </br>${operationMessage}
        </div>
    </form>
</div>
<div ></div>
</body>
</html>