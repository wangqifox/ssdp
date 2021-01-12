package io.github.wangqifox.ssdp;

import io.github.wangqifox.ssdp.listener.DiscoveryServerListener;
import io.github.wangqifox.ssdp.model.DiscoveryRequest;
import io.github.wangqifox.ssdp.model.DiscoveryResponse;
import io.github.wangqifox.ssdp.model.NotifyAlive;
import io.github.wangqifox.ssdp.model.NotifyBye;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author wangqi
 * @date 2021/1/8
 */
public class SsdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final String M_SEARCH_HEADER = "M-SEARCH";
    private static final String NTS_ALIVE = "NTS: ssdp:alive";
    private static final String NTS_BYEBYE = "NTS: ssdp:byebye";
    private final DiscoveryServerListener discoveryServerListener;

    public SsdpServerHandler(DiscoveryServerListener discoveryServerListener) {
        this.discoveryServerListener = discoveryServerListener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        String request = datagramPacket.content().toString(StandardCharsets.UTF_8);
        InetAddress remoteAddress = datagramPacket.sender().getAddress();
        if (request.startsWith(M_SEARCH_HEADER)) {
            DiscoveryRequest discoveryRequest = new DiscoveryRequest.Parser().parse(request.getBytes(StandardCharsets.UTF_8));
            List<DiscoveryResponse> discoveryResponseList = discoveryServerListener.onDiscoveryRequest(discoveryRequest, remoteAddress);
            for (DiscoveryResponse discoveryResponse : discoveryResponseList) {
                channelHandlerContext.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(discoveryResponse.toContent(), StandardCharsets.UTF_8), datagramPacket.sender()));
            }
        } else {
            if (request.contains(NTS_ALIVE)) {
                final NotifyAlive notifyAlive = new NotifyAlive.Parser().parse(request.getBytes(StandardCharsets.UTF_8));
                discoveryServerListener.onNotifyAlive(notifyAlive, remoteAddress);
            } else if (request.contains(NTS_BYEBYE)) {
                final NotifyBye notifyBye = new NotifyBye.Parser().parse(request.getBytes(StandardCharsets.UTF_8));
                discoveryServerListener.onNotifyBye(notifyBye, remoteAddress);
            }
        }
    }
}
