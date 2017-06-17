package com.bulgakov.controller;

import com.bulgakov.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bulgakov
 * @since 11.03.2017
 */
@Controller
public class ExitController {

    @RequestMapping(value = "/exit" , method = RequestMethod.GET)
    public ModelAndView returnToLoginPage(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(cleanBrauserCookie("id"));
        response.addCookie(cleanBrauserCookie("name"));
        response.addCookie(cleanBrauserCookie("role"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    private static Cookie cleanBrauserCookie(String cookieName){
        Cookie cookieToDelete = new Cookie(cookieName, null);
        cookieToDelete.setMaxAge(0);
        return cookieToDelete;
    }
}
