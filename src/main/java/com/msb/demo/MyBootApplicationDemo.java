package com.msb.demo;

import com.msb.myspringboot.MyBootApplication;
import com.msb.myspringboot.annotation.boot.ComponentScan;

@ComponentScan("com.msb.demo")
public class MyBootApplicationDemo {
	public static void main(String[] args) {
		MyBootApplication.run(MyBootApplicationDemo.class, args);
	}
}
