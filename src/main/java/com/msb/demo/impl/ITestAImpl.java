package com.msb.demo.impl;

import com.msb.demo.interfaces.ITestA;
import com.msb.demo.interfaces.ITestB;
import com.msb.jsonboot.annotation.*;

@Component
public class ITestAImpl implements ITestA {

    @Autowired
    ITestB iTestB;

    @Override
    public void testA() {
        System.out.println("ITestA");
    }
}
