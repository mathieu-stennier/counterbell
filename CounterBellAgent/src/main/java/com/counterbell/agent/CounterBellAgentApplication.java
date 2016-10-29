package com.counterbell.agent;

import com.counterbell.agent.client.CounterBellClient;
import com.counterbell.agent.common.CounterBellAgentType;
import com.counterbell.agent.common.exception.InvalidParameterException;
import com.counterbell.agent.server.CounterBellServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 29/10/16.
 */
@SpringBootApplication
public class CounterBellAgentApplication {

    public static void main(String[] args) {
        Map<String, Object> params = extractParams(args);
        Class[] sources = null;
        List<String> agentArgs = new ArrayList<String>(Arrays.asList(args));
        if (params.get("agentType").equals(CounterBellAgentType.COUNTER_BELL_CLIENT)) {
            sources = new Class[]{CounterBellAgentApplication.class, CounterBellAgent.class, CounterBellClient.class};
            agentArgs.add("-counterbell.agenttype=" + CounterBellAgentType.COUNTER_BELL_CLIENT);
        } else {
            sources = new Class[]{CounterBellAgentApplication.class, CounterBellAgent.class, CounterBellServer.class};
            agentArgs.add("-counterbell.agenttype=" + CounterBellAgentType.COUNTER_BELL_SERVER);
        }
        SpringApplication.run(sources, agentArgs.toArray(new String[agentArgs.size()]));
    }

    /**
     * Extract the parameters
     * @param args
     * @return a map containing the parameters values
     * @throws InvalidParameterException when parameters are not correct
     */
    private static Map<String, Object> extractParams(String[] args){
        Map<String, Object> res = new HashMap<>();
        Iterator<String> it = Arrays.asList(args).iterator();
        while(it.hasNext()){
            String arg = it.next();
            if(arg.equals("-c") || arg.equals("-client")){
                res.put("agentType","client");
            }
            if(arg.equals("-s") || arg.equals("-server")){
                res.put("agentType","server");
            }
        }
        assertParams(res);
        return res;
    }

    /**
     * Check the validity of the CounterBell Agent command line parameters
     * @param params
     * @throws InvalidParameterException when the parameters are not correct
     */
    private static void assertParams(Map<String,Object> params){
        if(!params.containsKey("agentType")){
            throw new InvalidParameterException("Missing the agent type. This should be either client (-c || -client) or server (-s || -server)");
        }
    }
}
