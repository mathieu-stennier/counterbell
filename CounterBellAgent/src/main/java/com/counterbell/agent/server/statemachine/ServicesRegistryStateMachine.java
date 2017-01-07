package com.counterbell.agent.server.statemachine;

import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.server.statemachine.repository.CounterBellServiceInfoRepository;
import com.counterbell.agent.server.statemachine.repository.MapDBCounterBellServiceInfoRepository;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.Snapshottable;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.snapshot.SnapshotReader;
import io.atomix.copycat.server.storage.snapshot.SnapshotWriter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class ServicesRegistryStateMachine extends StateMachine implements Snapshottable{

    Logger logger  = Logger.getLogger(ServicesRegistryStateMachine.class);

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
            logger.info("Received register command from session '"+commit.session()+"'");
            return null;
        } finally {
            commit.close();
        }
    }

    public CounterBellServiceInfo findService(Commit<FindServiceQuery> commit){
        try {
            logger.info("Find service command from session '"+commit.session()+"'");
            return serviceInfoRepository.find(commit.operation().getSearchCriteria());
        } finally {
            commit.close();
        }
    }

    @Override
    public void snapshot(SnapshotWriter snapshotWriter){
        try {
            logger.info("Taking snapshot");
            serviceInfoRepository.backup(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    snapshotWriter.writeByte(b);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void install(SnapshotReader snapshotReader) {
        try {
            logger.info("installing snapshot");
            serviceInfoRepository.restore(new InputStream() {
                @Override
                public int read() throws IOException {
                    return snapshotReader.readByte();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


