package com.msb.demo;

import com.msb.myspringboot.annotation.ioc.Component;

@Component("TeacherServiceImpl")
public class TeacherServiceImpl implements Service {
	@Override
	public String handler() {
		return "这是教师实现类";
	}
}
