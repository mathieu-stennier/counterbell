package com.counterbell.agent.server.statemachine.repository;

import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.common.searchcriteria.CounterBellServiceSearchCriteria;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 11/11/16.
 */
public abstract class CounterBellServiceInfoRepository {
    public abstract void create(String containerIdentifier, CounterBellServiceInfo serviceInfo);
    public abstract void update(String containerIdentifier, CounterBellServiceInfo serviceInfo);
    public abstract void delete(String containerIdentifier, String serviceName);
    public abstract boolean exists(String containerIdentifier, String serviceName);
    public abstract CounterBellServiceInfo find(CounterBellServiceSearchCriteria searchCriteria);
    public abstract void backup(OutputStream out) throws IOException;
    public abstract void restore(InputStream in) throws IOException;
    public abstract Iterable<CounterBellServiceInfo> iterate(String containerIdentifier);

    public void createOrUpdate(String containerIdentifier, CounterBellServiceInfo serviceInfo){
        if(!exists(containerIdentifier,serviceInfo.getName())){
            create(containerIdentifier,serviceInfo);
        }
        else{
            update(containerIdentifier,serviceInfo);
        }
    }
}
