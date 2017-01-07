package com.counterbell.agent.server.statemachine.repository;

import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.common.searchcriteria.CounterBellServiceSearchCriteria;
import com.counterbell.agent.common.utils.IOUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.mapdb.*;
import org.mapdb.serializer.GroupSerializerObjectArray;

import java.io.*;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 13/11/16.
 */
public class MapDBCounterBellServiceInfoRepository extends CounterBellServiceInfoRepository {

    Logger logger = Logger.getLogger(MapDBCounterBellServiceInfoRepository.class);
    DB db = null;
    String dbFile="";

    public MapDBCounterBellServiceInfoRepository(String file) {
        initializeDatabase(file);
    }

    private void initializeDatabase(String file){
        db = DBMaker.fileDB(file).make();
        dbFile = file;
        logger.info("Service repository initialized (filesystem:"+file+")");
    }

    @Override
    public void create(String containerIdentifier, CounterBellServiceInfo serviceInfo) {
        HTreeMap<String,CounterBellServiceInfo> dbServiceInfo = db.hashMap(containerIdentifier, Serializer.STRING, new SerializerCounterBellServiceInfo()).createOrOpen();
        dbServiceInfo.put(serviceInfo.getName(),serviceInfo);
        db.commit();
        logger.info("Created entry : container '"+containerIdentifier+"' service '"+serviceInfo.getName()+"' with "+serviceInfo.getServiceInfo().size()+" parameters.");
    }

    @Override
    public void update(String containerIdentifier, CounterBellServiceInfo serviceInfo) {
        HTreeMap<String,CounterBellServiceInfo> dbServiceInfo = db.hashMap(containerIdentifier, Serializer.STRING, new SerializerCounterBellServiceInfo()).createOrOpen();
        dbServiceInfo.put(serviceInfo.getName(),serviceInfo);
        db.commit();
        logger.info("Updated entry : container '"+containerIdentifier+"' service '"+serviceInfo.getName()+"' with "+serviceInfo.getServiceInfo().size()+" parameters.");
    }

    @Override
    public void delete(String containerIdentifier, String serviceName) {
        HTreeMap<String,CounterBellServiceInfo> dbServiceInfo = db.hashMap(containerIdentifier, Serializer.STRING, new SerializerCounterBellServiceInfo()).createOrOpen();
        dbServiceInfo.remove(serviceName);
        db.commit();
        logger.info("Deleted entry : container '"+containerIdentifier+"' service '"+serviceName+"'");
    }

    @Override
    public boolean exists(String containerIdentifier, String serviceName) {
        HTreeMap<String,CounterBellServiceInfo> dbServiceInfo = db.hashMap(containerIdentifier, Serializer.STRING, new SerializerCounterBellServiceInfo()).createOrOpen();
        return dbServiceInfo.containsKey(serviceName);
    }

    @Override
    public CounterBellServiceInfo find(CounterBellServiceSearchCriteria searchCriteria) {
        HTreeMap<String,CounterBellServiceInfo> dbServiceInfo = db.hashMap(searchCriteria.getContainerId(), Serializer.STRING, new SerializerCounterBellServiceInfo()).createOrOpen();
        return dbServiceInfo.get(searchCriteria.getName());
    }

    @Override
    public void backup(OutputStream out) throws IOException {
        synchronized (this){
            long copied = IOUtils.copyLarge(new FileInputStream(dbFile),out);
            logger.info("Backup of repository : wrote "+copied+" bytes to filesystem.");
        }
    }

    @Override
    public void restore(InputStream in) throws IOException {
        synchronized (this){
            db.close();
            long copied = IOUtils.copyLarge(in,new FileOutputStream(dbFile));
            initializeDatabase(dbFile);
            logger.info("Restore database : read "+copied+" bytes from backup file.");
        }
    }

    @Override
    public Iterable<CounterBellServiceInfo> iterate(String containerIdentifier) {
        HTreeMap<String,CounterBellServiceInfo> dbServiceInfo = db.hashMap(containerIdentifier, Serializer.STRING, new SerializerCounterBellServiceInfo()).createOrOpen();
        return dbServiceInfo.getValues();
    }

    public void clean(){
        logger.info("Closing database.");
        db.close();
    }

    public class SerializerCounterBellServiceInfo extends GroupSerializerObjectArray<CounterBellServiceInfo> {
        @Override
        public void serialize(@NotNull DataOutput2 dataOutput2, @NotNull CounterBellServiceInfo serviceInfo) throws IOException {
            ObjectOutputStream out2 = new ObjectOutputStream(dataOutput2);
            out2.writeObject(serviceInfo);
            out2.flush();
        }

        @Override
        public CounterBellServiceInfo deserialize(@NotNull DataInput2 dataInput2, int i) throws IOException {
            try {
                ObjectInputStream e = new ObjectInputStream(new DataInput2.DataInputToStream(dataInput2));
                return (CounterBellServiceInfo)e.readObject();
            } catch (ClassNotFoundException var4) {
                throw new IOException(var4);
            }
        }
    }
}
