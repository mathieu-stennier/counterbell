package com.counterbell.agent.server.statemachine.repository;

import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.common.searchcriteria.CounterBellServiceSearchCriteria;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 13/11/16.
 */
public abstract class CounterBellServiceInfoRepositoryTest{

    protected CounterBellServiceInfoRepository repo;

    @Before
    public abstract void init();

    @After
    public abstract void cleanup();

    @Test
    public void testCreationServiceAndExists(){
        repo.create("6",new CounterBellServiceInfo("test","mysql"));
        Assert.assertTrue(repo.exists("6","test"));
    }

    @Test
    public void testNotExists(){
        Assert.assertTrue(!repo.exists("6","test"));
    }

    @Test
    public void testDelete(){
        repo.create("6",new CounterBellServiceInfo("test","mysql"));
        repo.delete("6","test");
        Assert.assertTrue("Test service shouldn't exist since we've deleted it",!repo.exists("6","test"));
    }

    @Test
    public void testFind(){
        repo.create("6",new CounterBellServiceInfo("test","mysql"));
        CounterBellServiceSearchCriteria criteria = new CounterBellServiceSearchCriteria();
        criteria.setContainerId("6");
        criteria.setName("test");
        criteria.setType("mysql");
        CounterBellServiceInfo serv = repo.find(criteria);
        Assert.assertEquals("Service name should be the same", "test", serv.getName());
        Assert.assertEquals("Service type should be the same", "mysql", serv.getType());
    }

    @Test
    public void testBackupRestore() throws IOException {
        repo.create("6",new CounterBellServiceInfo("test","mysql"));
        repo.backup(new FileOutputStream("/tmp/testBackup.db"));
        repo.delete("6","test");
        Assert.assertTrue("Test service shouldn't exist since we've deleted it",!repo.exists("6","test"));
        repo.restore(new FileInputStream("/tmp/testBackup.db"));
        CounterBellServiceSearchCriteria criteria = new CounterBellServiceSearchCriteria();
        criteria.setContainerId("6");
        criteria.setName("test");
        criteria.setType("mysql");
        CounterBellServiceInfo serv = repo.find(criteria);
        Assert.assertEquals("Service name should be the same", "test", serv.getName());
        Assert.assertEquals("Service type should be the same", "mysql", serv.getType());
    }
}
