package io.github.wangqifox.ssdp;

import io.github.wangqifox.ssdp.config.SsdpServerConfig;
import io.github.wangqifox.ssdp.model.NotifyAlive;
import io.github.wangqifox.ssdp.model.NotifyBye;
import io.github.wangqifox.ssdp.utils.NetworkUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.SocketUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static io.github.wangqifox.ssdp.config.SsdpConfig.ADDRESS;
import static io.github.wangqifox.ssdp.config.SsdpConfig.PORT;

/**
 * @author wangqi
 * @date 2021/1/8
 */
public class SsdpServer {
    private final NioDatagramChannel nioDatagramChannel;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    private final SsdpServerConfig ssdpServerConfig;

    public SsdpServer(SsdpServerConfig ssdpServerConfig) throws Exception {
        this.ssdpServerConfig = ssdpServerConfig;

        InetAddress localAddress = NetworkUtils.getLocalAddress(ssdpServerConfig.getNetworkInterface());

        InetSocketAddress groupAddress = new InetSocketAddress(ADDRESS, PORT);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channelFactory((ChannelFactory<NioDatagramChannel>) () -> new NioDatagramChannel(InternetProtocolFamily.IPv4))
                .localAddress(localAddress, groupAddress.getPort())
                .option(ChannelOption.IP_MULTICAST_IF, ssdpServerConfig.getNetworkInterface())
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    public void initChannel(NioDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new SsdpServerHandler(ssdpServerConfig.getDiscoveryListener()));
                    }
                });

        nioDatagramChannel = (NioDatagramChannel) bootstrap.bind(groupAddress.getPort()).sync().channel();
        nioDatagramChannel.joinGroup(groupAddress, ssdpServerConfig.getNetworkInterface()).sync();
    }

    public void start() {
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            final NotifyAlive notifyAlive = ssdpServerConfig.getNotifyAliveListener().onAlive();
            nioDatagramChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(notifyAlive.toContent(), StandardCharsets.UTF_8), SocketUtils.socketAddress(ADDRESS, PORT)));
        }, 1, 10, TimeUnit.SECONDS);
    }

    public void stop() {
        final NotifyBye notifyBye = ssdpServerConfig.getNotifyByeListener().onBye();
        nioDatagramChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(notifyBye.toContent(), StandardCharsets.UTF_8), SocketUtils.socketAddress(ADDRESS, PORT)));
        bossGroup.shutdownGracefully();
    }
}

