package com.counterbell.agent.server.statemachine;

import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.server.statemachine.repository.CounterBellServiceInfoRepository;
import com.counterbell.agent.server.statemachine.repository.MapDBCounterBellServiceInfoRepository;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.Snapshottable;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.snapshot.SnapshotReader;
import io.atomix.copycat.server.storage.snapshot.SnapshotWriter;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class ServicesRegistryStateMachine extends StateMachine implements Snapshottable{

    private CounterBellServiceInfoRepository serviceInfoRepository;

    public ServicesRegistryStateMachine(){
        this.serviceInfoRepository = new MapDBCounterBellServiceInfoRepository("/tmp/counterBellDB");
    }

    public ServicesRegistryStateMachine(CounterBellServiceInfoRepository serviceInfoRepository){
        this.serviceInfoRepository = serviceInfoRepository;
    }

    public Object registerService(Commit<RegisterServiceCommand> commit){
        try {
            serviceInfoRepository.createOrUpdate(commit.operation().getRequestMetaData().getContainerIdentifier(),commit.operation().getServiceInfos());
            return null;
        } finally {
            commit.close();
        }
    }

    public CounterBellServiceInfo findService(Commit<FindServiceQuery> commit){
        try {
            return serviceInfoRepository.find(commit.operation().getSearchCriteria());
        } finally {
            commit.close();
        }
    }

    @Override
    public void snapshot(SnapshotWriter snapshotWriter){

    }

    @Override
    public void install(SnapshotReader snapshotReader) {

    }
}


