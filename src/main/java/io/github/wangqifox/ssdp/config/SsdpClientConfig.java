package io.github.wangqifox.ssdp.config;

import io.github.wangqifox.ssdp.listener.DiscoveryClientListener;

import java.net.NetworkInterface;

/**
 * @author wangqi
 * @date 2021/1/12
 */
public class SsdpClientConfig {
    private NetworkInterface networkInterface;
    private DiscoveryClientListener discoveryClientListener;

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    public DiscoveryClientListener getDiscoveryClientListener() {
        return discoveryClientListener;
    }

    public void setDiscoveryClientListener(DiscoveryClientListener discoveryClientListener) {
        this.discoveryClientListener = discoveryClientListener;
    }
}
