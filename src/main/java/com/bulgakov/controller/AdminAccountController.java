package com.bulgakov.controller;

import com.bulgakov.db.dbOracle.OracleDAO;
import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.Customer;
import com.bulgakov.model.LightVersion.CustomerLight;
import com.bulgakov.model.LightVersion.UserLight;
import com.bulgakov.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.bulgakov.db.dbOracle.OracleUtils.*;

/**
 * @author Bulgakov
 * @since 23.03.2017
 */
@Controller
public class AdminAccountController {

    @RequestMapping(value = "list-of-users", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<UserLight> getUsers(@RequestParam String role, @RequestParam Integer id) {
        List<UserLight> listOfUsers = null;
        UserLight tmp = null;
        if ("Admin".equals(role) && id != null) {
            listOfUsers = getAllUsers();
            for (UserLight user : listOfUsers) {
                if (user.personId.equals(id)) {
                    tmp = user;
                }
            }
            listOfUsers.remove(tmp);
        }
        return listOfUsers;
    }

    @RequestMapping(value = "list-of-customers", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<CustomerLight> getCustomers(@RequestParam String role, @RequestParam Integer id) {
        List<CustomerLight> customerLightList = null;
        if ("Admin".equals(role) && id != null) {
            customerLightList = getAllCustomers();
        }
        return customerLightList;
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String deleteUser(@RequestParam Integer id) {
        if (id != null) {
            OracleDAO dao = new OracleDAO();
            dao.delete(id);
        }
        return "User was deleted!";
    }

    @RequestMapping(value = "deleteCustomer", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String deleteCustomer(@RequestParam Integer id) {
        if (id != null) {
            OracleDAO dao = new OracleDAO();
            dao.delete(id);
        }
        return "Customer was deleted!";
    }

    @RequestMapping(value = "reg-new-customer", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    public String registrationNewCustomer(@RequestParam String name, @RequestParam String pass, @RequestParam String email, @RequestParam String invoice) {
        Customer customer = new Customer(getIdForObject(),name, invoice, email, pass);
        OracleDAO dao = new OracleDAO();
        try {
            dao.create(customer);
        } catch (UnknownObjectType unknownObjectType) {
            unknownObjectType.printStackTrace();
        }
        return "Customer was created";
    }

    @RequestMapping(value = "reg-new-user", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    public String registrationNewUser(@RequestParam String name, @RequestParam String surname, @RequestParam String secondName, @RequestParam String email, @RequestParam String pass, @RequestParam String idCode, @RequestParam String qualification, @RequestParam String role) {
        OracleDAO dao = new OracleDAO();
        try {
            User user = new User(getIdForObject(), email, pass, name, surname, secondName, Double.parseDouble(idCode), qualification, role);
            dao.create(user);
        } catch (UnknownObjectType unknownObjectType) {
            unknownObjectType.printStackTrace();
        } catch (Exception e) {
            System.out.println("idCode can't became Double type!");
        }
        return "User was created";
    }

    @RequestMapping(value = "list-of-qualifications", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getListQualifications() {
        return getQualifications();
    }

    @RequestMapping(value = "list-of-roles", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getListRoles() {
        return getRoles();
    }
}
