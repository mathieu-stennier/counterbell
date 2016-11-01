package com.counterbell.agent.client;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by matteo on 11/09/16.
 */
@RestController
public class ClientController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          this.applicationContext = applicationContext;
    }
}
