package com.counterbell.agent.server;

import com.counterbell.agent.server.statemachine.ServicesRegistryStateMachine;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 29/10/16.
 */
@Configuration
public class CounterBellServer {

    @Bean(name = "counterBellServerNode", destroyMethod = "shutdown")
    @Scope("singleton")
    public CopycatServer counterBellServer() throws UnknownHostException {
        Address address = new Address(InetAddress.getLocalHost().getHostAddress(), 5000);
        CopycatServer server = CopycatServer.builder(address)
                .withStateMachine(ServicesRegistryStateMachine::new)
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                .withStorage(Storage.builder()
                        .withDirectory(new File("logs"))
                        .withStorageLevel(StorageLevel.DISK)
                        .build())
                .build();

        return server;
    }
}
