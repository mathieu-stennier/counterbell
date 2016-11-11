package com.counterbell.agent.server;

import com.counterbell.agent.common.AgentConfiguration;
import com.counterbell.agent.server.statemachine.ServicesRegistryStateMachine;
import com.counterbell.agent.server.statemachine.repository.CounterBellServiceInfoRepository;
import com.counterbell.agent.server.statemachine.repository.OrientDBCounterBellServiceInfoRepository;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 29/10/16.
 */
@Configuration
public class CounterBellServer {

    @Autowired
    private AgentConfiguration config;

    @Bean(name = "serviceInfoRepository")
    @Scope("singleton")
    public CounterBellServiceInfoRepository counterBellServiceInfoRepository(){
        return new OrientDBCounterBellServiceInfoRepository();
    }

    @Bean(name = "counterBellServerNode", destroyMethod = "shutdown")
    @Scope("singleton")
    public CopycatServer counterBellServer(CounterBellServiceInfoRepository serviceInfoRepository) throws UnknownHostException {
        Address talkToServerAddress = new Address(InetAddress.getLocalHost().getHostAddress(), config.getCopycatTalkToServerPort());
        Address talkToClientAddress = new Address(InetAddress.getLocalHost().getHostAddress(), config.getCopycatTalkToClientPort());
        CopycatServer server = CopycatServer.builder(talkToClientAddress,talkToServerAddress)
                .withStateMachine(() -> new ServicesRegistryStateMachine(serviceInfoRepository))
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                .withStorage(Storage.builder()
                        .withDirectory(new File("logs"))
                        .withStorageLevel(StorageLevel.DISK)
                        .build())
                .build();

        if(config.getShouldBootstrap()){
            server.bootstrap().join();
        }
        else if (!config.getInitialMembers().isEmpty()){
            Set<Address> adr = new HashSet<>();
            for (InetSocketAddress sockadr : config.getInitialMembers()){
                adr.add(new Address(sockadr));
            }
            server.join(adr).join();
        }

        return server;
    }
}
