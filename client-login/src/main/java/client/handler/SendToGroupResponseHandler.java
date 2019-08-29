package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.response.SendToGroupResponsePacket;

public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupResponsePacket sendToGroupResponsePacket) throws Exception {
        if (sendToGroupResponsePacket.isSuccess()) {
            String msg = sendToGroupResponsePacket.getMsg();
            String user = sendToGroupResponsePacket.getFromUser();
            System.out.println("收到消息 ： " + msg + " [ from : " + user + " ]");
        } else {
            System.out.println("群消息发送失败， 原因： " + sendToGroupResponsePacket.getReason());
        }
    }
}
