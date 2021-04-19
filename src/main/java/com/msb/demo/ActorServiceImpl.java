package com.msb.demo;

import com.msb.jsonboot.annotation.Component;

@Component("ActorServiceImpl")
public class ActorServiceImpl implements Service{
    @Override
    public String handler() {
        return "这是主播实现类，笨蛋";
    }
}
