package com.counterbell.agent.server.cluster;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * Copyright CounterBell 2016
 * Created by matteo on 27/11/16.
 */
public interface ReplicableStateMachineServer {
    /**
     * Join a cluster of servers.
     * @param cluster collection of IP Addresses that will be used to connect to the servers.
     */
    void join(Collection<InetSocketAddress> cluster);

    /**
     * Bootstrap a cluster.
     */
    void bootstrap();

    /**
     * Bootstrap a cluster with other servers.
     * @param cluster collection of IP Addresses that will be used to connect to the servers.
     */
    void bootstrap(Collection<InetSocketAddress> cluster);

    /**
     * Leave the cluster.
     */
    void leave();

    /**
     * Shutdown the server without leaving the cluster.
     */
    void shutdown();

    /**
     * Get the socket address used to communicate with clients of the cluster.
     * @return socket address
     */
    InetSocketAddress getServerCommunicationAddress();

    /**
     * Get the socket address used to communicate with servers of the cluster.
     * @return socket address
     */
    InetSocketAddress getClientCommunicationAddress();

    /**
     * Return the current state of the server.
     * @return current state of the server.
     */
    ServerState getCurrentState();

    /**
     * Check if the server is running.
     * @return
     */
    boolean isRunning();

    /* ************* **/
    /* EVENT HANDLERS */
    /* ************* **/

    void registerServerListener(ReplicableStateMachineServerListener listener);

}
