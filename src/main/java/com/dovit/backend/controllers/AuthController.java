package com.dovit.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ramón París
 * @since 02-10-2019
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/hello-world")
    public String helloWorld(){
        return "Hello World!";
    }

}
