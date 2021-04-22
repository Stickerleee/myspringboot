package com.msb.demo;

import java.util.ArrayList;
import java.util.List;

import com.msb.demo.entity.User;
import com.msb.jsonboot.annotation.ioc.Autowired;
import com.msb.jsonboot.annotation.ioc.Qualifier;
import com.msb.jsonboot.annotation.springmvc.GetMapping;
import com.msb.jsonboot.annotation.springmvc.PathVariable;
import com.msb.jsonboot.annotation.springmvc.PostMapping;
import com.msb.jsonboot.annotation.springmvc.RequestBody;
import com.msb.jsonboot.annotation.springmvc.RequestParam;
import com.msb.jsonboot.annotation.springmvc.RestController;

@RestController(value = "/user")
public class UserController {

	@Autowired
	UserService userService;

	@Qualifier("TeacherServiceImpl")
	@Autowired
	Service service;

	@GetMapping("/hello")
	public void hello() {
		System.out.println(userService.getString());
	}

	@GetMapping(value = "/helloImpl")
	public void helloImpl() {
		System.out.println("方法进来了");
		System.out.println(service);
		String handler = service.handler();
		System.out.println(handler);
	}

	@PostMapping("/hi")
	public void hi() {

	}

	@GetMapping("/goodThis")
	public String goodThis(@RequestParam(value = "name") String name, int age) {
		System.out.println(name);
		System.out.println(age);
		List<String> list = new ArrayList<>();
		list.add("3");
		list.add("4");
		list.add("5");
		return "323414";
	}

	@PostMapping(value = "/testHttpPost")
	public String testHttpPost(@RequestBody User user, String alex) {
		System.out.println(user);
		System.out.println(alex);
		return "success";
	}

	@GetMapping(value = "/exo/{name}")
	public String testPathVariable(@PathVariable("name") String name) {
		System.out.println(name);
		return name;
	}

}
