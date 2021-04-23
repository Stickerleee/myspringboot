package com.msb.demo.impl;

import com.msb.demo.interfaces.ITestA;
import com.msb.demo.interfaces.ITestC;
import com.msb.myspringboot.annotation.ioc.Autowired;
import com.msb.myspringboot.annotation.ioc.Component;

@Component
public class ITestCImpl implements ITestC {

	@Autowired
	ITestA iTestA;

	@Override
	public void testC() {
		System.out.println("ITestC");
	}
}
