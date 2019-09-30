package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.response.KillResponsePacket;

public class KillResponseHandler extends SimpleChannelInboundHandler<KillResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, KillResponsePacket killResponsePacket) throws Exception {
        System.out.println("Kill response");
        if (killResponsePacket.isSuccess()) {
            System.out.println(killResponsePacket.getReason());
        }
    }
}
