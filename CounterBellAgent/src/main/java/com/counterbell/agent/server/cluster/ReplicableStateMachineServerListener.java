package com.counterbell.agent.server.cluster;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 29/11/16.
 */
public interface ReplicableStateMachineServerListener {
    void onServerStateChanged(ServerState from, ServerState to);
}
