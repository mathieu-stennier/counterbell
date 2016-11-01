package com.counterbell.agent.common;

import com.counterbell.agent.common.exception.InvalidParameterException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 01/11/16.
 *
 * CounterBellAgentConfiguration class created to load the configuration of the CounterBell Agent via OS env and spring properties.
 */
@Component
public class AgentConfiguration implements ApplicationContextAware{

    private static final String ipAndPortPattern = "[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}:[0-9]{1,5}";
    private static final String COUNTERBELL_BOOTSTRAP = "counterbell.bootstrapcluster";

    private ApplicationContext appContext;

    @Value("${agenttype}")
    private String _commandLineAgentType;

    @Value("${bootstrapcluster:false}")
    private String _bootstrapCluster;

    @Value("${joinmembers:}")
    private String _initialMembers;


    private AgentType agentType;
    private Boolean shouldBootstrap;
    private Set<InetSocketAddress> initialMembers = new HashSet<>();

    @PostConstruct
    public void extractCommandLineArgs(){
        this.agentType = AgentType.valueOf(_commandLineAgentType);
        this.shouldBootstrap = Boolean.getBoolean(_bootstrapCluster);
        if(_initialMembers.length() != 0){
            for(String adr : _initialMembers.split(";")){
                if(!adr.matches(ipAndPortPattern)){
                    throw new InvalidParameterException("'"+adr+"' is not a valid IP address + port.");
                }
                String hostnameOrIP = adr.split(":")[0];
                int port = Integer.parseInt(adr.split(":")[1]);
                initialMembers.add(new InetSocketAddress(hostnameOrIP,port));
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public Boolean getShouldBootstrap() {
        return shouldBootstrap;
    }

    public void setShouldBootstrap(Boolean shouldBootstrap) {
        this.shouldBootstrap = shouldBootstrap;
    }

}
