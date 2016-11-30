package com.counterbell.agent.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class CounterBellServiceInfo implements Serializable{
    private String name;
    private String type;
    private HashMap<String, Object> serviceInfo;

    public CounterBellServiceInfo(String name, String type) {
        this.name = name;
        this.type = type;
        this.serviceInfo=new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, Object> getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(HashMap<String, Object> serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public String toString(){
        return new StringBuilder().append("[").append(name).append(",").append(type).append(",").append(serviceInfo.toString()).append("]").toString();
    }
}
