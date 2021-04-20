package com.msb.demo.impl;

import com.msb.demo.interfaces.ITestB;
import com.msb.demo.interfaces.ITestC;
import com.msb.jsonboot.annotation.*;

@Component
public class ITestBImpl implements ITestB {

    @Autowired
    ITestC iTestC;

    @Override
    public void testB() {
        System.out.println("ITestB");
    }
}