package com.example;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class DummyTest {
    @RequestMapping("/")
    //A function without a explict association is private
    String requestMethodName() {
        return "Hello World I am here!";
    }

}
