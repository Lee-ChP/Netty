package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.response.ShanResponsePacket;

public class ShanResponseHandler extends SimpleChannelInboundHandler<ShanResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ShanResponsePacket shanResponsePacket) throws Exception {
        System.out.println("Shan response");
        if (shanResponsePacket.isSuccess()) {
            System.out.println(shanResponsePacket.getReason());
        }
    }
}
