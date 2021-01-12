package io.github.wangqifox.ssdp.location.server;

import io.github.wangqifox.ssdp.config.LocationServerConfig;
import io.github.wangqifox.ssdp.location.model.Device;
import io.github.wangqifox.ssdp.location.model.Root;
import io.github.wangqifox.ssdp.location.model.SpecVersion;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * @author wangqi
 * @date 2021/1/11
 */
public class LocationServer {
    private final LocationServerConfig config;
    private final Root root;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public LocationServer(LocationServerConfig config) {
        this.config = config;

        SpecVersion specVersion = new SpecVersion();
        Device device = config.getDevice();
        device.setServiceList(config.getServiceList());
        root = new Root();
        root.setSpecVersion(specVersion);
        root.setDevice(device);
    }

    public Channel start() throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        b.option(ChannelOption.SO_BACKLOG, 1024);
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpResponseEncoder());
                        pipeline.addLast(new HttpRequestDecoder());
                        pipeline.addLast(new HttpObjectAggregator(65536));
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new LocationServerHandler(root));
                    }
                });

        return b.bind(config.getServerPort()).sync().channel();
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
