package com.counterbell.agent.server;

import io.atomix.copycat.server.CopycatServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 02/11/16.
 */
@RestController
public class ServerController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    CopycatServer clusterServer;

    @RequestMapping("/")
    public String home() {
        JsonObject serverState = Json.createObjectBuilder()
                .add("server ip",clusterServer.state().name())
                .add("leader",clusterServer.cluster().leader().serverAddress().toString()).build();
        return serverState.toString();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
