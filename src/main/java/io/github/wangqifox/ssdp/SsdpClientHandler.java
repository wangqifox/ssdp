package io.github.wangqifox.ssdp;

import io.github.wangqifox.ssdp.listener.DiscoveryClientListener;
import io.github.wangqifox.ssdp.model.DiscoveryResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author wangqi
 * @date 2021/1/7
 */
public class SsdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final DiscoveryClientListener discoveryClientListener;

    public SsdpClientHandler(DiscoveryClientListener discoveryClientListener) {
        this.discoveryClientListener = discoveryClientListener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        InetAddress remoteAddress = datagramPacket.sender().getAddress();
        String response = datagramPacket.content().toString(StandardCharsets.UTF_8);
        DiscoveryResponse discoveryResponse = new DiscoveryResponse.Parser().parse(response.getBytes(StandardCharsets.UTF_8));
        discoveryClientListener.onDiscoveryResponse(discoveryResponse, remoteAddress);
    }
}
