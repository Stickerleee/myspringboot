package com.msb.demo.impl;

import com.msb.demo.interfaces.ITestB;
import com.msb.demo.interfaces.ITestC;
import com.msb.myspringboot.annotation.ioc.Autowired;
import com.msb.myspringboot.annotation.ioc.Component;

@Component
public class ITestBImpl implements ITestB {

	@Autowired
	ITestC iTestC;

	@Override
	public void testB() {
		System.out.println("ITestB");
	}
}
