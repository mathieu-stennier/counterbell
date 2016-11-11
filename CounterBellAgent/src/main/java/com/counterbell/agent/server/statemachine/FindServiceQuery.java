package com.counterbell.agent.server.statemachine;

import com.counterbell.agent.common.CounterBellRequestMetaData;
import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.common.searchcriteria.CounterBellServiceSearchCriteria;
import io.atomix.copycat.Query;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class FindServiceQuery implements Query<CounterBellServiceInfo> {
    private CounterBellRequestMetaData requestMetaData;
    private CounterBellServiceSearchCriteria searchCriteria;

    public FindServiceQuery(CounterBellRequestMetaData requestMetaData, CounterBellServiceSearchCriteria searchCriteria) {
        this.requestMetaData = requestMetaData;
        this.searchCriteria = searchCriteria;
    }

    public CounterBellRequestMetaData getRequestMetaData() {
        return requestMetaData;
    }

    public void setRequestMetaData(CounterBellRequestMetaData requestMetaData) {
        this.requestMetaData = requestMetaData;
    }

    public CounterBellServiceSearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(CounterBellServiceSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}
