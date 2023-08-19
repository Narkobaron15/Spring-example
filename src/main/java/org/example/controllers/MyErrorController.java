package org.example.controllers;

import org.json.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyErrorController implements ErrorController {
    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public String handleError() {
        //do something like logging
        var jo = new JSONObject();
        jo.put("status", "404 Not Found");
        jo.put("message", "Check the path you've requested");
        return jo.toString();
    }
}
