package com.counterbell.agent.server;

import com.counterbell.agent.common.ConditionnalOnAgentType;
import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.server.statemachine.repository.CounterBellServiceInfoRepository;
import io.atomix.copycat.server.CopycatServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 02/11/16.
 */
@RestController
@ConditionnalOnAgentType("server")
public class ServerController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    CopycatServer clusterServer;

    @Autowired
    CounterBellServiceInfoRepository counterBellServiceInfoRepository;

    @RequestMapping("/")
    public String home() {
        JsonArrayBuilder arrayBuilderServices = Json.createArrayBuilder();
        for(CounterBellServiceInfo service : counterBellServiceInfoRepository.iterate("1")){
            arrayBuilderServices.add(Json.createObjectBuilder()
                    .add("name",service.getName())
                    .add("type",service.getType()).build()
            );
        }
        JsonObject serverState = Json.createObjectBuilder()
                .add("server ip",clusterServer.state().name())
                .add("leader",clusterServer.cluster().leader().serverAddress().toString())
                .add("services", arrayBuilderServices.build()).build();

        return serverState.toString();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
