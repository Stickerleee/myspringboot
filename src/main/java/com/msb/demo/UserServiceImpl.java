package com.msb.demo;

import com.msb.jsonboot.annotation.ioc.Component;

@Component
public class UserServiceImpl implements Service {
	@Override
	public String handler() {
		return "这是学生实现类";
	}
}
