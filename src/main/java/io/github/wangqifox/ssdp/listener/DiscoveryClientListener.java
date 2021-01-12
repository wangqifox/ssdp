package io.github.wangqifox.ssdp.listener;

import io.github.wangqifox.ssdp.model.DiscoveryResponse;

import java.net.InetAddress;

/**
 * @author wangqi
 * @date 2021/1/12
 */
public interface DiscoveryClientListener {
    void onDiscoveryResponse(DiscoveryResponse discoveryResponse, InetAddress remoteAddress);
}
