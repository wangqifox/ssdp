package io.github.wangqifox.ssdp.config;

import io.github.wangqifox.ssdp.listener.DiscoveryServerListener;
import io.github.wangqifox.ssdp.listener.NotifyAliveListener;
import io.github.wangqifox.ssdp.listener.NotifyByeListener;

import java.net.NetworkInterface;

/**
 * @author wangqi
 * @date 2021/1/12
 */
public class SsdpServerConfig {


    private String id;
    private NetworkInterface networkInterface;
    private NotifyAliveListener notifyAliveListener;
    private NotifyByeListener notifyByeListener;
    private DiscoveryServerListener discoveryServerListener;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    public NotifyAliveListener getNotifyAliveListener() {
        return notifyAliveListener;
    }

    public void setNotifyAliveListener(NotifyAliveListener notifyAliveListener) {
        this.notifyAliveListener = notifyAliveListener;
    }

    public NotifyByeListener getNotifyByeListener() {
        return notifyByeListener;
    }

    public void setNotifyByeListener(NotifyByeListener notifyByeListener) {
        this.notifyByeListener = notifyByeListener;
    }

    public DiscoveryServerListener getDiscoveryListener() {
        return discoveryServerListener;
    }

    public void setDiscoveryListener(DiscoveryServerListener discoveryServerListener) {
        this.discoveryServerListener = discoveryServerListener;
    }
}
