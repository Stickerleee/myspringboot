package com.msb.demo.controller;

import com.msb.demo.interfaces.ITestA;
import com.msb.demo.interfaces.ITestB;
import com.msb.demo.interfaces.ITestC;
import com.msb.myspringboot.annotation.ioc.Autowired;
import com.msb.myspringboot.annotation.springmvc.GetMapping;
import com.msb.myspringboot.annotation.springmvc.RestController;

@RestController("/test")
public class TestController {

	@Autowired
	private ITestA testA;

	@Autowired
	private ITestB testB;

	@Autowired
	private ITestC testC;

	@GetMapping("/test")
	public void test() {
		testA.testA();
		testB.testB();
		testC.testC();
	}
}
