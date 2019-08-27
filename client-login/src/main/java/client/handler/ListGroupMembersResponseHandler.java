package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.response.ListGroupMembersResponsePacket;

public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersResponsePacket listGroupMembersResponsePacket) throws Exception {
        if (listGroupMembersResponsePacket.isSuccess()) {
            System.out.print("成员 ： [ ");
            listGroupMembersResponsePacket.getGroupMembers().stream().forEach(m -> System.out.printf(m+" \t"));
            System.out.printf(" ] %n");
        } else {
            System.out.println("获取群组成员失败，原因： " + listGroupMembersResponsePacket.getReason());
        }
    }
}
