package com.bulgakov.controller;

import com.bulgakov.db.dbOracle.OracleDAO;
import com.bulgakov.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Bulgakov
 * @since 23.03.2017
 */
@Controller
public class UserAccountController {

    @RequestMapping(value = "user-personal-info", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public User checkUser(@RequestParam Integer id, @RequestParam String role) {
        OracleDAO dao = new OracleDAO();
        User user = (User) dao.read(id);
        if (!user.getRole().equals(role)) {
            return null;
        } else {
            return user;
        }
    }
}
