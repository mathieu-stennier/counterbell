package com.counterbell.agent.server.statemachine.repository;

import com.counterbell.agent.common.CounterBellServiceInfo;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 11/11/16.
 */
public class OrientDBCounterBellServiceInfoRepository implements CounterBellServiceInfoRepository{

    @Override
    public void create(CounterBellServiceInfo serviceInfo) {

    }

    @Override
    public void update(CounterBellServiceInfo serviceInfo) {

    }

    @Override
    public void delete(CounterBellServiceInfo serviceInfo) {

    }

    @Override
    public CounterBellServiceInfo findByAccountAndName(Long accountId, String name) {
        return null;
    }

    @Override
    public CounterBellServiceInfo findByAccountAndType(Long accountId, String type) {
        return null;
    }
}
