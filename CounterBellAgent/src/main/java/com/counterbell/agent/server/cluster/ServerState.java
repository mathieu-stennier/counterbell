package com.counterbell.agent.server.cluster;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 29/11/16.
 */
public enum ServerState {
    LEADER,     // Represents the state of a server which is actively coordinating and replicating logs with other servers.
    FOLLOWER,   // Represents the state of a server participating in normal log replication.
    CANDIDATE,  // Represents the state of a server attempting to become the leader.
    PASSIVE,    // Represents the state of a server in the process of catching up its log.
    RESERVE,    // Represents the state of a server that is a reserve member of the cluster.
    INACTIVE    // Represents the state of an inactive server.
}
