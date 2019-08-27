package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.packet.request.JoinGroupRequestPacket;
import protocol.packet.response.JoinGroupResponsePacket;
import utils.SessionUtil;


public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        String groupId = joinGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup =  SessionUtil.getChannelGroup(groupId);
        channelGroup.add(channelHandlerContext.channel());

        //构造响应消息
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setGroupId(groupId);
        joinGroupResponsePacket.setSuccess(true);
        channelHandlerContext.channel().writeAndFlush(joinGroupResponsePacket);

    }
}
