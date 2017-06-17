function showUserAccount() {
    var loginForm = document.getElementById("login-form");
    var errorForm = document.getElementById("error-form");
    loginForm.style.display = 'none';
    errorForm.style.display = 'none';

    var personalInfoButton = document.getElementById("personalInfo-menu");
    var taskButton = document.getElementById("task-menu");
    var requestButton = document.getElementById("request-menu");
    var exitButton = document.getElementById("nameSurname");

    personalInfoButton.style.display = '';
    taskButton.style.display = '';
    requestButton.style.display = '';
    exitButton.style.display = '';
}

function showPersonalUserInfo(){
    var secondBlock = document.getElementById("second-block");
    secondBlock.style.display = 'none';

    var id = getCookie("id");
    var role = getCookie("role");
    if (id != null && role != 'Customer') {
        $.ajax({
            url: 'user-personal-info',
            data: {id:id, role:role},
            type: "POST",
            success: function (user) {
                var respContent = "";
                respContent += "<table id=\"personalUserInfo\">";
                respContent += "<tr><td>Name:</td><td>"+ user.name +"</td></tr>";
                respContent += "<tr><td>Surname:</td><td>"+ user.surname +"</td></tr>";
                respContent += "<tr><td>Second name:</td><td>"+ user.secondName +"</td></tr>";
                respContent += "<tr><td>Email:</td><td>"+ user.email +"</td></tr>";
                respContent += "<tr><td>Identity code:</td><td>"+ user.identityCode +"</td></tr>";
                respContent += "<tr><td>Qualification:</td><td>"+ user.qualification +"</td></tr>";
                respContent += "<tr><td>Role:</td><td>"+ user.role +"</td></tr>";
                if (user.boss != null){
                    respContent += "<tr><td>Boss:</td><td>"+ user.boss.name + " " + user.boss.surname +"</td></tr>";
                }
                respContent += "</table>";
                $("#personal-info").html(respContent);
            },
            error: function (res) {
                console.log("error");
            }
        });
        event.preventDefault();
    } else {
        goToErrorForm();
    }
}