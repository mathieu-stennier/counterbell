package com.counterbell.agent.common;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 20/11/16.
 */
public class ConditionOnAgentType implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String agentType = context.getEnvironment().getProperty("agenttype");
        String conditionnalval = (String)(metadata.getAnnotationAttributes(ConditionnalOnAgentType.class.getName()).get("value"));
        if(conditionnalval.equals("client")){
            return agentType.equals(AgentType.COUNTER_BELL_CLIENT.toString());
        }
        if(conditionnalval.equals("server")){
            return agentType.equals(AgentType.COUNTER_BELL_SERVER.toString());
        }
        return false;
    }
}
