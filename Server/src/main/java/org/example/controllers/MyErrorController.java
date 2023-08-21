package org.example.controllers;

import org.json.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyErrorController implements ErrorController {
    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> handleError() {
        //do something like logging
        var jo = new JSONObject();
        jo.put("message", "Something wrong happened");
        return new ResponseEntity<>(jo.toString(), HttpStatus.BAD_REQUEST);
    }
}
