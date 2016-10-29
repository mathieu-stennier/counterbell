package com.counterbell.agent;

import com.google.code.gossip.GossipMember;
import com.google.code.gossip.GossipService;
import com.google.code.gossip.GossipSettings;
import com.google.code.gossip.RemoteGossipMember;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by matteo on 11/09/16.
 */
@Configuration
public class CounterBellAgent {
    @Value("${server.port}")
    private int serverPort;

    @Bean(name = "gossipService", destroyMethod = "shutdown")
    @Scope("singleton")
    public GossipService gossipService() throws UnknownHostException, InterruptedException {
        GossipSettings settings = new GossipSettings();
        ArrayList<GossipMember> startlist = new ArrayList<>();
        startlist.add(new RemoteGossipMember("127.0.0.1",8181,"8181",0));
        GossipService gossipService = new GossipService("127.0.0.1",serverPort+100,(serverPort+100)+"",startlist,settings,null);
        gossipService.start();
        return gossipService;
    }
}
