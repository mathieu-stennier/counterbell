package com.counterbell.agent.server.statemachine.repository;

import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.common.searchcriteria.CounterBellServiceSearchCriteria;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 11/11/16.
 */
public class OrientDBCounterBellServiceInfoRepository extends CounterBellServiceInfoRepository{


    @Override
    public void create(String containerIdentifier, CounterBellServiceInfo serviceInfo) {

    }

    @Override
    public void update(String containerIdentifier, CounterBellServiceInfo serviceInfo) {

    }

    @Override
    public void delete(String containerIdentifier, CounterBellServiceInfo serviceInfo) {

    }

    @Override
    public boolean exists(String containerIdentifier, String serviceName) {
        return false;
    }

    @Override
    public CounterBellServiceInfo find(CounterBellServiceSearchCriteria searchCriteria) {
        return null;
    }
}
