package com.counterbell.agent.server.statemachine;

import com.counterbell.agent.common.CounterBellService;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.StateMachine;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class ServicesRegistryStateMachine extends StateMachine {
    Map<String,CounterBellService> serviceRegistryMap = new HashMap<>();

    public Object registerService(Commit<RegisterServiceCommand> commit){
        try {
            serviceRegistryMap.put(commit.operation().getName(), commit.operation().getService());
            return null;
        } finally {
            commit.close();
        }
    }

    public CounterBellService findService(Commit<FindServiceQuery> commit){
        try {
            return serviceRegistryMap.get(commit.operation().getSearchCriteria().getName());
        } finally {
            commit.close();
        }
    }
}


