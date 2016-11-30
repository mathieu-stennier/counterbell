package com.counterbell.agent.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 11/11/16.
 */
public class CounterBellRequestMetaData implements Serializable{
    private String containerIdentifier;
    private HashMap<String,Object> otherMetaData;

    public CounterBellRequestMetaData(String containerIdentifier) {
        this.containerIdentifier = containerIdentifier;
        otherMetaData = new HashMap<>();
    }

    public CounterBellRequestMetaData(String containerIdentifier, HashMap<String, Object> otherMetaData) {
        this.containerIdentifier = containerIdentifier;
        this.otherMetaData = otherMetaData;
    }

    public String getContainerIdentifier() {
        return containerIdentifier;
    }

    public void setContainerIdentifier(String containerIdentifier) {
        this.containerIdentifier = containerIdentifier;
    }

    public HashMap<String, Object> getOtherMetaData() {
        return otherMetaData;
    }

    public void setOtherMetaData(HashMap<String, Object> otherMetaData) {
        this.otherMetaData = otherMetaData;
    }
}
