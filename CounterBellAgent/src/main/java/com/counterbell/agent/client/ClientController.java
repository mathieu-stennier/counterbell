package com.counterbell.agent.client;

import com.counterbell.agent.common.ConditionnalOnAgentType;
import com.counterbell.agent.common.CounterBellRequestMetaData;
import com.counterbell.agent.common.CounterBellServiceInfo;
import com.counterbell.agent.common.searchcriteria.CounterBellServiceSearchCriteria;
import com.counterbell.agent.server.statemachine.FindServiceQuery;
import com.counterbell.agent.server.statemachine.RegisterServiceCommand;
import io.atomix.copycat.client.CopycatClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by matteo on 11/09/16.
 */
@RestController
@ConditionnalOnAgentType("client")
public class ClientController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private CopycatClient copycatClient;

    @RequestMapping("/")
    public String homeClient() {
        CounterBellRequestMetaData metaData = new CounterBellRequestMetaData("1");
        CounterBellServiceInfo serviceInfo =  new CounterBellServiceInfo("testservice","mysql");
        copycatClient.submit(new RegisterServiceCommand(metaData,serviceInfo)).join();
        Object value = copycatClient.submit(new FindServiceQuery(metaData, new CounterBellServiceSearchCriteria("1","testservice","mysql"))).join();
        return value.toString();
    }

    @RequestMapping(value = "/{containerId}/services/", method = RequestMethod.GET, produces = "application/json")
    public String services(){

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          this.applicationContext = applicationContext;
    }
}
