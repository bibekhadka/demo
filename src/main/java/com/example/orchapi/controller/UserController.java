package com.example.orchapi.controller;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @CrossOrigin(origins = "http://localhost")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@RequestParam(value = "email") String email) {

        return "Hello" + email;
    }

    @CrossOrigin(origins = "http://localhost")
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin(@RequestParam(value = "email") String email) {
        return "Welcome" + email;
    }

    @CrossOrigin(origins = "http://localhost")
    @RequestMapping(value = "{id}/logout", method = RequestMethod.GET)
    public String logout(@PathVariable(value = "id") String userId) {
        return "Bye";
    }

    @CrossOrigin(origins = "http://localhost")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/agreement")
    public String getTermsAndConditions(@PathVariable(value = "id") String userId) {
        return "Terms and Conditions";
    }

    @CrossOrigin(origins = "http://localhost")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/privacy")
    public String getPrivacy(@PathVariable(value = "id") String userId) {
        return "Privacy";
    }

    @CrossOrigin(origins = "http://localhost")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/important")
    public String getImportant(@PathVariable(value = "id") String userId) {
        return "Important secure code executed!!";
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    String invalidResource(HttpServletResponse response, Exception e) {
        response.setHeader("message", e.getMessage());
        return "Sorry";
    }

}
