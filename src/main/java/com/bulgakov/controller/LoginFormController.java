package com.bulgakov.controller;

import com.bulgakov.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static com.bulgakov.db.dbOracle.OracleUtils.checkLoginPassword;
import static com.bulgakov.db.dbOracle.OracleUtils.getShortInfo;

/**
 * @author Bulgakov
 * @since 10.03.2017
 */
@Controller
@SessionAttributes({"role","id","name"})
public class LoginFormController {

    @ModelAttribute("role")
    public String get() {return "none";}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "check-user", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public PersonShortInfo checkUser(@RequestParam String email, @RequestParam String pass) {
        Integer personId = checkLoginPassword(email, pass);
        if (personId != null) {
            PersonShortInfo shortInfo;
            Person personTmp = getShortInfo(personId);
            if (personTmp instanceof User) {
                User userShortInfo = (User) personTmp;
                shortInfo = new PersonShortInfo(userShortInfo.getPersonId(), userShortInfo.getName() + " " + userShortInfo.getSurname(), userShortInfo.getRole());
            } else {
                Customer customerShortInfo = (Customer) personTmp;
                shortInfo = new PersonShortInfo(customerShortInfo.getPersonId(), customerShortInfo.getName(), "Customer");
            }
            return shortInfo;
        } else {
            return null;
        }
    }

}
