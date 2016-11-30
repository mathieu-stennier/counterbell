package com.counterbell.agent;

import com.counterbell.agent.client.CounterBellClientConfiguration;
import com.counterbell.agent.common.AgentType;
import com.counterbell.agent.common.exception.InvalidParameterException;
import com.counterbell.agent.server.CounterBellServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 29/10/16.
 */
@SpringBootApplication
public class CounterBellAgentApplication {
    private static final String ipAndPortPattern = "[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}:[0-9]{1,5}";

    public static void main(String[] args) {
        Map<String, Object> params = extractAndValidateCounterBellCommandLineParams(args);
        Class[] sources = null;
        List<String> agentArgs = new ArrayList<String>(Arrays.asList(args));
        if (params.get("agentType").equals("client")) {
            sources = new Class[]{CounterBellAgentApplication.class, CounterBellClientConfiguration.class};
            agentArgs.add("--agenttype=" + AgentType.COUNTER_BELL_CLIENT);
        } else {
            sources = new Class[]{CounterBellAgentApplication.class, CounterBellServerConfiguration.class};
            agentArgs.add("--agenttype=" + AgentType.COUNTER_BELL_SERVER);
        }
        if(params.containsKey("bootstrap") && (Boolean)params.get("bootstrap")){
            agentArgs.add("--bootstrapcluster=true");
        }
        if(params.containsKey("joinmembers")){
            StringBuilder strBuilder = new StringBuilder();
            for(String adr: (Iterable<String>)params.get("joinmembers")){
                strBuilder.append(adr).append(";");
            }
            strBuilder.deleteCharAt(strBuilder.length()-1);
            agentArgs.add("--joinmembers="+strBuilder);
        }
        ConfigurableApplicationContext context = SpringApplication.run(sources, agentArgs.toArray(new String[agentArgs.size()]));
    }

    /**
     * Extract the parameters
     * @param args
     * @return a map containing the parameters values
     * @throws InvalidParameterException when parameters are not correct
     */
    private static Map<String, Object> extractAndValidateCounterBellCommandLineParams(String[] args){
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
            if(arg.equals("-b") || arg.equals("-bootstrap")){
                if(res.get("agentType").equals("server")){
                    res.put("bootstrap", true);
                }
                else{
                    throw new InvalidParameterException("bootstrap option can only be performed on a server agent. Please specify -server or -s option.");
                }
            }
            if(arg.equals("-j") || arg.equals("-join")){
                if(!it.hasNext()){
                    throw new InvalidParameterException("join option should be followed by at least one server addresses separated by ';'. Example: 1.1.1.1:234;2.2.2.2:456");
                }
                String[] addressesStr = it.next().split(";");
                Set<String> addresses= new HashSet<>();
                for(String adrStr: addressesStr){
                    if(!adrStr.matches(ipAndPortPattern)){
                        throw new InvalidParameterException(String.format("'%s' is not a valid IP address with port. Should be like 1.1.1.1:123.", adrStr));
                    }
                    addresses.add(adrStr);
                }
                res.put("joinmembers",addresses);
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
