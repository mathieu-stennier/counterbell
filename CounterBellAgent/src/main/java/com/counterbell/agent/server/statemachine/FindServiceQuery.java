package com.counterbell.agent.server.statemachine;

import com.counterbell.agent.common.CounterBellService;
import com.counterbell.agent.common.searchcriteria.ServiceSearchCriteria;
import io.atomix.copycat.Query;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 23/10/16.
 */
public class FindServiceQuery implements Query<CounterBellService> {
    ServiceSearchCriteria searchCriteria;

    public FindServiceQuery(ServiceSearchCriteria searhCriteria) {
         this.searchCriteria = searhCriteria;
    }

    public ServiceSearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(ServiceSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }
}
