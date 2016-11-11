package com.counterbell.agent.server.statemachine;

import com.counterbell.agent.common.CounterBellRequestMetaData;
import com.counterbell.agent.common.CounterBellServiceInfo;
import io.atomix.copycat.Command;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class RegisterServiceCommand implements Command<Object> {
    private CounterBellRequestMetaData requestMetaData;
    private CounterBellServiceInfo serviceInfos;

    public RegisterServiceCommand(CounterBellRequestMetaData requestMetaData, CounterBellServiceInfo serviceInfos){
        this.requestMetaData = requestMetaData;
        this.serviceInfos = serviceInfos;
    }

    public CounterBellRequestMetaData getRequestMetaData() {
        return requestMetaData;
    }

    public void setRequestMetaData(CounterBellRequestMetaData requestMetaData) {
        this.requestMetaData = requestMetaData;
    }

    public CounterBellServiceInfo getServiceInfos() {
        return serviceInfos;
    }

    public void setServiceInfos(CounterBellServiceInfo serviceInfos) {
        this.serviceInfos = serviceInfos;
    }
}
