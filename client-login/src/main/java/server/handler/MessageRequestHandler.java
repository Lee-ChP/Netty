package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.request.MessageRequestPacket;
import protocol.packet.response.MessageResponsePacket;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) {

        /*
          构建消息响应 ： 将客户端的消息原封不动返回
         */
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage("服务端回复： " + messageRequestPacket.getMessage());

        System.out.println("收到客户端消息： " + messageRequestPacket.getMessage());

        channelHandlerContext.channel().writeAndFlush(responsePacket);
    }
}
