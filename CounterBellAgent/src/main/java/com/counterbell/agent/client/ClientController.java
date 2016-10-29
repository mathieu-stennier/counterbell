package com.counterbell.agent.client;

import com.google.code.gossip.GossipMember;
import com.google.code.gossip.GossipService;
import io.atomix.copycat.server.CopycatServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 * Created by matteo on 11/09/16.
 */
@RestController
public class ClientController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private GossipService gossipService;

    @Autowired
    private CopycatServer counterBellServer;

    @RequestMapping("/")
    public String home() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for(GossipMember member : gossipService.get_gossipManager().getMemberList())  {
           JsonObjectBuilder obj = Json.createObjectBuilder()
                   .add("ip",member.getHost())
                   .add("port", member.getPort())
                   .add("heartbeat",member.getHeartbeat());
            arrBuilder.add(obj.build());
        }

        return arrBuilder.build().toString();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          this.applicationContext = applicationContext;
    }
}
