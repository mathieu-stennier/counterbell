package com.counterbell.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by matteo on 11/09/16.
 */
@Configuration
public class Agent {
    @Value("${server.port}")
    private int serverPort;

}
