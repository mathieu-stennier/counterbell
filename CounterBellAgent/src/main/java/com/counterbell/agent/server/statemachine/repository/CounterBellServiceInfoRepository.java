package com.counterbell.agent.server.statemachine.repository;

import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.common.searchcriteria.CounterBellServiceSearchCriteria;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 11/11/16.
 */
public abstract class CounterBellServiceInfoRepository {
    public abstract void create(String containerIdentifier, CounterBellServiceInfo serviceInfo);
    public abstract void update(String containerIdentifier, CounterBellServiceInfo serviceInfo);
    public abstract void delete(String containerIdentifier, CounterBellServiceInfo serviceInfo);
    public abstract boolean exists(String containerIdentifier, String serviceName);
    public abstract CounterBellServiceInfo find(CounterBellServiceSearchCriteria searchCriteria);

    public void createOrUpdate(String containerIdentifier, CounterBellServiceInfo serviceInfo){
        if(!exists(containerIdentifier,serviceInfo.getName())){
            create(containerIdentifier,serviceInfo);
        }
        else{
            update(containerIdentifier,serviceInfo);
        }
    }
}
