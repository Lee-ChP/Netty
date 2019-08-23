package server.inboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandlerC extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx , Object msg) throws Exception {
        System.out.println("InBoundHandlerC: " + msg);
        super.channelRead(ctx, msg);
    }
}
