package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.response.MessageResponsePacket;

import java.util.Date;

/**
 * 处理消息：
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) {

        System.out.println(new Date() + " : 收到服务端消息： " + messageResponsePacket.getMessage());
    }
}
