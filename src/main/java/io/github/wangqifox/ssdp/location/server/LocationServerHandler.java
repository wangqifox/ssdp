package io.github.wangqifox.ssdp.location.server;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import io.github.wangqifox.ssdp.location.model.Device;
import io.github.wangqifox.ssdp.location.model.Root;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @author wangqi
 * @date 2021/1/11
 */
public class LocationServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final Root root;

    public LocationServerHandler(Root root) {
        this.root = root;
    }

    private String genResponse() {
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(Root.class);
        xStream.processAnnotations(Device.class);
        return xStream.toXML(root);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        boolean keepAlive = HttpUtil.isKeepAlive(req);
        FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(), OK,
                Unpooled.wrappedBuffer(genResponse().getBytes(StandardCharsets.UTF_8)));
        response.headers()
                .set(CONTENT_TYPE, "text/xml")
                .setInt(CONTENT_LENGTH, response.content().readableBytes());

        if (keepAlive) {
            if (!req.protocolVersion().isKeepAliveDefault()) {
                response.headers().set(CONNECTION, KEEP_ALIVE);
            }
        } else {
            response.headers().set(CONNECTION, CLOSE);
        }

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
