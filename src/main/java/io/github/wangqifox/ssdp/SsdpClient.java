package io.github.wangqifox.ssdp;

import io.github.wangqifox.ssdp.listener.DiscoveryClientListener;
import io.github.wangqifox.ssdp.model.DiscoveryRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.SocketUtils;

import java.nio.charset.StandardCharsets;

import static io.github.wangqifox.ssdp.config.SsdpConfig.ADDRESS;
import static io.github.wangqifox.ssdp.config.SsdpConfig.PORT;

/**
 * @author wangqi
 * @date 2021/1/7
 */
public class SsdpClient {
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final Channel channel;

    public SsdpClient(DiscoveryClientListener discoveryClientListener) throws InterruptedException {
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new SsdpClientHandler(discoveryClientListener));

        channel = bootstrap.bind(0).sync().channel();
    }

    public void start() {
        channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(new DiscoveryRequest().toContent(), StandardCharsets.UTF_8),
                SocketUtils.socketAddress(ADDRESS, PORT)));
    }

    public void stop() {
        bossGroup.shutdownGracefully();
    }
}
