package io.github.wangqifox.ssdp.listener;

import io.github.wangqifox.ssdp.model.DiscoveryRequest;
import io.github.wangqifox.ssdp.model.DiscoveryResponse;
import io.github.wangqifox.ssdp.model.NotifyAlive;
import io.github.wangqifox.ssdp.model.NotifyBye;

import java.net.InetAddress;
import java.util.List;

/**
 * @author wangqi
 * @date 2021/1/12
 */
public interface DiscoveryServerListener {
    List<DiscoveryResponse> onDiscoveryRequest(DiscoveryRequest discoveryRequest, InetAddress remoteAddress);

    void onNotifyAlive(NotifyAlive notifyAlive, InetAddress remoteAddress);

    void onNotifyBye(NotifyBye notifyBye, InetAddress remoteAddress);
}
