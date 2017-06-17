function checkUser(event) {
    $.ajax({
        url: 'check-user',
        data: jQuery("#login-form").serialize(),
        type: "POST",
        success: function (user) {
            if (user.id == null) {
                goToErrorForm();
            } else {
                goToAccount(user);
            }
        },
        error: function (res) {
            console.log("error");
        }
    });
    event.preventDefault();
}

function goToErrorForm() {
    var loginForm = document.getElementById("login-form");
    var errorForm = document.getElementById("error-form");
    loginForm.style.display = 'none';
    errorForm.style.display = '';
}

function goToLoginPage() {
    var loginForm = document.getElementById("login-form");
    var errorForm = document.getElementById("error-form");
    loginForm.style.display = '';
    errorForm.style.display = 'none';
}

function goToAccount(person) {
    document.cookie = "id="+person.id;
    document.cookie = "name="+person.name;
    document.cookie = "role="+person.role;
    if (person.role == "User") {
        showUserAccount();
        showPersonalUserInfo();
    }
    if (person.role == "Manager") {
        showManagerAccount();
    }
    if (person.role == "Customer") {
        showCustomerAccount();
    }
    if (person.role == "Admin") {
        showAdminAccount();
        showPersonalUserInfo();
    }
    setUserName();
}

function setUserName() {
    $("#username").html(getCookie("name"));
}

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}