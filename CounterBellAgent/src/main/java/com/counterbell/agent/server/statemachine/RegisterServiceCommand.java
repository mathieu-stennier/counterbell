package com.counterbell.agent.server.statemachine;

import com.counterbell.agent.common.CounterBellService;
import io.atomix.copycat.Command;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class RegisterServiceCommand implements Command<Object> {
    private String name;
    private CounterBellService service;

    public RegisterServiceCommand(String name, CounterBellService service){
        this.name = name;
        this.service = service;
    }

    public CounterBellService getService() {
        return service;
    }

    public String getName() {
        return name;
    }
}
