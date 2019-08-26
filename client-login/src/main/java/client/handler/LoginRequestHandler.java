package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.packet.request.LoginRequestPacket;

/**
 * 发送登录请求 : uid  username password
 */
public class LoginRequestHandler extends ChannelInboundHandlerAdapter {
    /*@Override
    public void channelActive(ChannelHandlerContext context) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        //loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserId("fe14");
        loginRequestPacket.setUsername("Lee");
        loginRequestPacket.setPassword("19930714");


        context.channel().writeAndFlush(loginRequestPacket);
    }*/
}
