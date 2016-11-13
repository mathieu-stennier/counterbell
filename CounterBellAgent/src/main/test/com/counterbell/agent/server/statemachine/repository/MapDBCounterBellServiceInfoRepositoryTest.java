package com.counterbell.agent.server.statemachine.repository;

import org.junit.After;
import org.junit.Before;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 13/11/16.
 */
public class MapDBCounterBellServiceInfoRepositoryTest extends CounterBellServiceInfoRepositoryTest{

    @Before
    public void init(){
        repo = new MapDBCounterBellServiceInfoRepository("/tmp/counterbell.db");
    }

    @After
    public void cleanup(){
        ((MapDBCounterBellServiceInfoRepository)repo).clean();
    }
}
