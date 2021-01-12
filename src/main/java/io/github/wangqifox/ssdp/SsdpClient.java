package io.github.wangqifox.ssdp;

import io.github.wangqifox.ssdp.config.SsdpClientConfig;
import io.github.wangqifox.ssdp.listener.DiscoveryClientListener;
import io.github.wangqifox.ssdp.model.DiscoveryRequest;
import io.github.wangqifox.ssdp.utils.NetworkUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.SocketUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static io.github.wangqifox.ssdp.config.SsdpConfig.ADDRESS;
import static io.github.wangqifox.ssdp.config.SsdpConfig.PORT;

/**
 * @author wangqi
 * @date 2021/1/7
 */
public class SsdpClient {
    private final NioDatagramChannel nioDatagramChannel;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    public SsdpClient(SsdpClientConfig ssdpClientConfig) throws InterruptedException {
        InetAddress localAddress = NetworkUtils.getLocalAddress(ssdpClientConfig.getNetworkInterface());
        InetSocketAddress groupAddress = new InetSocketAddress(ADDRESS, PORT);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channelFactory((ChannelFactory<NioDatagramChannel>) () -> new NioDatagramChannel(InternetProtocolFamily.IPv4))
                .localAddress(localAddress, groupAddress.getPort())
                .option(ChannelOption.IP_MULTICAST_IF, ssdpClientConfig.getNetworkInterface())
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    public void initChannel(NioDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new SsdpClientHandler(ssdpClientConfig.getDiscoveryClientListener()));
                    }
                });

        nioDatagramChannel = (NioDatagramChannel) bootstrap.bind(groupAddress.getPort()).sync().channel();
        nioDatagramChannel.joinGroup(groupAddress, ssdpClientConfig.getNetworkInterface()).sync();
    }

    public void start() {
        nioDatagramChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(new DiscoveryRequest().toContent(), StandardCharsets.UTF_8),
                SocketUtils.socketAddress(ADDRESS, PORT)));
    }

    public void stop() {
        bossGroup.shutdownGracefully();
    }
}
