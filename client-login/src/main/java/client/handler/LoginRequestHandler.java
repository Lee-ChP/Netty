package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.packet.request.LoginRequestPacket;

import java.util.UUID;

/**
 * 发送登录请求
 */
public class LoginRequestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext context) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("Lee");
        loginRequestPacket.setPassword("199307147");

        context.channel().writeAndFlush(loginRequestPacket);
    }
}
