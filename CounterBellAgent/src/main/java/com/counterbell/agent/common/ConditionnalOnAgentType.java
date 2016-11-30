package com.counterbell.agent.common;

import org.springframework.context.annotation.Conditional;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 20/11/16.
 */

@Conditional(ConditionOnAgentType.class)
public @interface ConditionnalOnAgentType {
    public String value();
    public boolean exists() default true;
}
