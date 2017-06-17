function showAdminAccount(){
    var loginForm = document.getElementById("login-form");
    var errorForm = document.getElementById("error-form");
    loginForm.style.display = 'none';
    errorForm.style.display = 'none';

    var personalInfoButton = document.getElementById("personalInfo-menu");
    var projectButton = document.getElementById("project-menu");
    var usersButton = document.getElementById("users-menu");
    var exitButton = document.getElementById("nameSurname");

    personalInfoButton.style.display = '';
    projectButton.style.display = '';
    usersButton.style.display = '';
    exitButton.style.display = '';
}

function showAllPersons() {
    var id = getCookie("id");
    var role = getCookie("role");
    if (id != null && role == 'Admin') {
        showAllCustomers(id, role);
        showAllUsers(id, role);
    } else {
        goToErrorForm();
    }
}

function showAllUsers(id, role) {
        $.ajax({
            url: 'list-of-users',
            data: {role:role, id: id},
            type: "POST",
            success: function (list) {
                var respContent = "";
                respContent += "<h2 align='center'>Users</h2>";
                respContent += "<table id='allUser'>";
                respContent += "<tr><th>Name</th><th>Surname</th><th>Second name</th><th>Email</th><th>Identity code</th><th>Qualification</th><th>Role</th><th>Edit</th><th>Delete</th></tr>";
                for (var i = 0; i < list.length; i++) {
                    respContent += "<tr><td>" + list[i].name + "</td>" +
                        "<td>" + list[i].surname + "</td>" +
                        "<td>" + list[i].secondName + " </td>" +
                        "<td>" + list[i].email + "</td>" +
                        "<td>" + list[i].identityCode + "</td>" +
                        "<td>" + list[i].qualification + "</td>" +
                        "<td>" + list[i].role + "</td>" +
                        "<td>" + "<input type=\"button\" value='Edit' onclick=\"editUser("+list[i].personId+")\">" + "</td>" +
                        "<td>" + "<input type=\"button\" value='Delete' onclick=\"deleteUser("+list[i].personId+")\">" + "</td></tr>";
                }
                respContent += "</table>";
                respContent += "<input type=\"button\" class=\"createButtons\" value=\"Create new user\" onclick =\"showNewUserForm()\">";
                $("#personal-info").html(respContent);
            },
            error: function (res) {
                console.log("error");
            }
        });
        event.preventDefault();
}

function showAllCustomers(id, role){
    var secondBlock = document.getElementById("second-block");
    secondBlock.style.display = '';

    $.ajax({
        url: 'list-of-customers',
        data: {role:role, id: id},
        type: "POST",
        success: function (list) {
            var respContent = "";
            respContent += "<h2 align='center'>Customers</h2>";
            respContent += "<table id='allCustomers'>";
            respContent += "<tr><th>Name</th><th>Email</th><th>Invoice</th><th>Edit</th><th>Delete</th></tr>";
            for (var i = 0; i < list.length; i++) {
                respContent += "<tr><td>" + list[i].name + "</td>" +
                    "<td>" + list[i].email + "</td>" +
                    "<td>" + list[i].invoice + "</td>" +
                    "<td>" + "<input type=\"button\" value='Edit' onclick=\"editCustomer("+list[i].personId+")\">" + "</td>" +
                    "<td>" + "<input type=\"button\" value='Delete' onclick=\"deleteCustomer("+list[i].personId+")\">" + "</td></tr>";
            }
            respContent += "</table>";
            respContent += "<input type=\"button\" class=\"createButtons\" value=\"Create new customer\" onclick =\"showNewCustomerForm()\">";
            $("#second-block").html(respContent);
        },
        error: function (res) {
            console.log("error");
        }
    });
    event.preventDefault();
}

function editUser(id) {

}

function deleteUser(id) {
    $.ajax({
        url: 'deleteUser',
        data: {id:id},
        type: "POST",
        success: function (status) {
            console.log(status);
            showAllPersons();
        },
        error: function (res) {
            console.log("error");
        }
    });
}

function editCustomer(id) {

}

function deleteCustomer(id) {
    $.ajax({
        url: 'deleteCustomer',
        data: {id:id},
        type: "POST",
        success: function (status) {
            console.log(status);
            showAllPersons();
        },
        error: function (res) {
            console.log("error");
        }
    });
}

function showNewUserForm () {
    var secondBlock = document.getElementById("second-block");
    secondBlock.style.display = 'none';

    var respContent = "";
    respContent += "<form id=\"regNewUserForm\">";
    respContent += "Name:<br/>";
    respContent += "<input  type=\"text\" id=\"name\" placeholder=\"Name\" name='name'/> <br/>";
    respContent += "Surname:<br/>";
    respContent += "<input  type=\"text\" id=\"surname\" placeholder=\"Surname\" name='surname'/> <br/>";
    respContent += "Second Name:<br/>";
    respContent += "<input  type=\"text\" id=\"secondName\" placeholder=\"SecondName\" name='secondName'/> <br/>";
    respContent += "Email:<br/>";
    respContent += "<input  type=\"text\" id=\"email\" placeholder=\"Email\" name='email'/> <br/>";
    respContent += "Password:<br/>";
    respContent += "<input  type=\"text\" id=\"pass\" placeholder=\"Password\" name='pass'/> <br/>";
    respContent += "Identity code:<br/>";
    respContent += "<input type=\"text\" id=\"idCode\" placeholder=\"Identity code\" name='idCode'/> <br/>";
    respContent += "Qualification:<br/>";
    $.ajax({
        url: 'list-of-qualifications',
        data: {},
        //async: false,
        type: "POST",
        success: function (list) {
            respContent += "<select name=\"qualification\">";
            for (var i = 0 ; i < list.length; i++) {
                respContent += "<option>" + list[i] + "</option>";
            }
            respContent += "</select>";
        },
        error: function (res) {
            console.log("error");
        }
    });
    respContent += "<br/>Role:<br/>";
    $.ajax({
        url: 'list-of-roles',
        data: {},
        //async: false,
        type: "POST",
        success: function (list) {
            respContent += "<select name=\"role\">";
            for (var i = 0 ; i < list.length; i++) {
                respContent += "<option>" + list[i] + "</option>";
            }
            respContent += "</select>";
        },
        error: function (res) {
            console.log("error");
        }
    });
    respContent += "<br/><input type=\"button\" value=\"Create\" onclick='regNewUser()'>";
    respContent += "</form>";
    $("#personal-info").html(respContent);
}

function showNewCustomerForm() {
    var secondBlock = document.getElementById("second-block");
    secondBlock.style.display = 'none';

    var respContent = "";
    respContent += "<form id=\"regNewCustomerForm\">";
    respContent += "Name:<br/>";
    respContent += "<input type=\"text\" id=\"name\" name =\"name\" placeholder=\"Name\"/> <br/>";
    respContent += "Email:<br/>";
    respContent += "<input type=\"text\" id=\"email\" name =\"email\" placeholder=\"Email\"/> <br/>";
    respContent += "Password:<br/>";
    respContent += "<input type=\"text\" id=\"pass\" name =\"pass\" placeholder=\"Password\"/> <br/>";
    respContent += "Invoice:<br/>";
    respContent += "<input type=\"text\" id=\"invoice\" name =\"invoice\" placeholder=\"Invoice\"/> <br/>";
    respContent += "<input type=\"button\" value=\"Create\" onclick='regNewCustomer()'>";
    respContent += "</form>";
    $("#personal-info").html(respContent);
}

function regNewCustomer(){
    $.ajax({
        url: 'reg-new-customer',
        data: jQuery("#regNewCustomerForm").serialize(),
        type: "POST",
        success: function (status) {
            console.log(status);
            showAllPersons();
        },
        error: function (res) {
            console.log("error");
        }
    });
}

function regNewUser() {
    $.ajax({
        url: 'reg-new-user',
        data: jQuery("#regNewUserForm").serialize(),
        type: "POST",
        success: function (status) {
            console.log(status);
            showAllPersons();
        },
        error: function (res) {
            console.log("error");
        }
    });
}

