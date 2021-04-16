package com.msb.demo;

import java.util.ArrayList;
import java.util.List;

import com.msb.demo.entity.User;
import com.msb.jsonboot.annotation.*;

@RestController(value = "/user")
public class UserController {

    @GetMapping("/hello")
    @PostMapping("/helloWorld")
    public void hello(){

    }

    @PostMapping("/hi")
    public void hi(){

    }

    @GetMapping("/goodThis")
    public String goodThis(@RequestParam(value = "name") String name, int age){
        System.out.println(name);
        System.out.println(age);
        List<String> list = new ArrayList<>();
        list.add("3");
        list.add("4");
        list.add("5");
        return "323414";
    }

    @PostMapping(value = "/testHttpPost")
    public String testHttpPost(@RequestBody User user, String alex){
        System.out.println(user);
        System.out.println(alex);
        return "success";
    }

    @GetMapping(value = "/exo/{name}")
    public String testPathVariable(@PathVariable("name") String name){
        System.out.println(name);
        return name;
    }

}