package com.counterbell.agent.server.statemachine;

import com.counterbell.agent.common.CounterBellService;
import com.counterbell.agent.common.searchcriteria.CounterBellServiceSearchCriteria;
import io.atomix.copycat.Query;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class FindServiceQuery implements Query<CounterBellService> {
    CounterBellServiceSearchCriteria searchCriteria;

    public FindServiceQuery(CounterBellServiceSearchCriteria searhCriteria) {
         this.searchCriteria = searhCriteria;
    }

    public CounterBellServiceSearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(CounterBellServiceSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}
