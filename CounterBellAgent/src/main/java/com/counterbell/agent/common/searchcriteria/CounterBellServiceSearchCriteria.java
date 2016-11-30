package com.counterbell.agent.common.searchcriteria;

import java.io.Serializable;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class CounterBellServiceSearchCriteria implements Serializable {
    private String containerId;
    private String name;
    private String type;

    public CounterBellServiceSearchCriteria() {
    }

    public CounterBellServiceSearchCriteria(String containerId, String name, String type) {
        this.containerId = containerId;
        this.name = name;
        this.type = type;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
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
}
