package com.counterbell.agent.client;

import com.counterbell.agent.common.AgentConfiguration;
import com.counterbell.agent.common.ConditionnalOnAgentType;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 29/10/16.
 */
@Configuration
@ConditionnalOnAgentType("client")
public class CounterBellClientConfiguration {

    @Autowired
    private AgentConfiguration config;

    @Bean(name = "counterBellClientNode", destroyMethod = "close")
    @Scope("singleton")
    public CopycatClient counterBellClient() throws UnknownHostException {
        CopycatClient client = CopycatClient.builder()
                .withTransport(new NettyTransport())
                .build();

        if (!config.getInitialMembers().isEmpty()){
            Set<Address> adr = new HashSet<>();
            for (InetSocketAddress sockadr : config.getInitialMembers()){
                adr.add(new Address(sockadr));
            }
            client.connect(adr).join();
        }

        return client;
    }
}
