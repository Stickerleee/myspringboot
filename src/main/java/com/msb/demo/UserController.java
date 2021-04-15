package com.msb.demo;

import com.msb.jsonboot.annotation.*;

@NewController(value = "/user")
public class UserController {

    @GetMapping("/hello")
    @PostMapping("/helloWorld")
    public void hello(){

    }

    @PostMapping("/hi")
    public void hi(){

    }

    @GetMapping("/goodThis")
    public void goodThis(@RequestParam(value = "name") String name, Integer age){
        System.out.println(name);
        System.out.println(age);
    }


}
