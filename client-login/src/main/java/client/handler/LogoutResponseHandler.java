package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.response.LogoutResponsePacket;
import utils.SessionUtil;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutResponsePacket logoutResponsePacket) throws Exception {
        if(logoutResponsePacket.isSuccess()) {
            SessionUtil.unBindSession(channelHandlerContext.channel());
            System.out.println("退出成功！");
        } else {
            System.out.println("退出失败！");
        }
    }
}
