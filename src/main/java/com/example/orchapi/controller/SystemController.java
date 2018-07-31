package com.example.orchapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/system")
public class SystemController {

    @CrossOrigin(origins = "http://localhost")
    @RequestMapping(value = "/basic", method = RequestMethod.GET)
    public String systemUTC() {
        return "Basic system";
    }

}
