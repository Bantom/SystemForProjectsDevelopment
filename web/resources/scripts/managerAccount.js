function showManagerAccount(){
    var loginForm = document.getElementById("login-form");
    var errorForm = document.getElementById("error-form");
    loginForm.style.display = 'none';
    errorForm.style.display = 'none';

    var personalInfoButton = document.getElementById("personalInfo-menu");
    var projectButton = document.getElementById("project-menu");
    var exitButton = document.getElementById("nameSurname");

    personalInfoButton.style.display = '';
    projectButton.style.display = '';
    exitButton.style.display = '';
}
