<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="resources/css/login.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <script src="resources/scripts/checkAuth.js"></script>
    <script src="resources/scripts/adminAccount.js"></script>
    <script src="resources/scripts/userAccount.js"></script>
    <script src="resources/scripts/managerAccount.js"></script>
    <script src="resources/scripts/customerAccount.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            $('#error-form').submit(goToLoginPage());
        });
    </script>
</head>
<body>
<div id="blackHeader"></div>
<div> id="wrapper">
    <header>
        <h2>Bulgakov Anton</h2>
        <div id="menuButtons">
            <input id="personalInfo-menu" type="button" value="Personal" class="menu-buttons" style="display:none" onclick="showPersonalUserInfo()"/>
            <input id="project-menu" type="button" value="Projects" class="menu-buttons" style="display:none"/>
            <input id="task-menu" type="button" value="Tasks" class="menu-buttons" style="display:none"/>
            <input id="request-menu" type="button" value="Requests" class="menu-buttons" style="display:none"/>
            <input id="users-menu" type="button" value="Users" class="menu-buttons" style="display:none" onclick="showAllPersons()"/>
        </div>
        <div id="nameSurname" style="display:none">
            <div id="username"></div>
            <form id="exit" method="get" action="/exit"><input type="submit" value="Exit"></form>
        </div>
    </header>

    <div id="login">
        <form id ="login-form">
            <span class="fontawesome-user"></span>
            <input type="text" placeholder="Login" id="email" name="email"/> <br/>
            <span class="fontawesome-lock"></span>
            <input type="password" placeholder="Password" id="password" name="pass"/> <br/>
            <input type="button" value="Login" onclick="checkUser(event)">
        </form>

        <form id="error-form" style="display:none">
            <div id="errorMessage">
                Error authorization
            </div>
            <input type="submit" value="Go to login page">
        </form>
    </div>

    <div id="personal-info"></div>

    <div id="second-block"></div>
</div>
</body>
</html>
