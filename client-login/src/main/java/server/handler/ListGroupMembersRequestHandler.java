package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.packet.request.ListGroupMembersRequestPacket;
import protocol.packet.response.ListGroupMembersResponsePacket;
import session.Session;
import utils.SessionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {
        String groupId = listGroupMembersRequestPacket.getGroupId();
        //构建获取成员列表响应返回到客户端
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            responsePacket.setReason("查无此群");
            responsePacket.setSuccess(false);
        } else {
            // 遍历群成员对应的session，构造群成员信息
            responsePacket.setSuccess(true);
            List<Session> sessionList = new ArrayList<>();

            for (Channel channel : channelGroup) {
                Session session = SessionUtil.getSession(channel);
                sessionList.add(session);
            }

            List<String> members = sessionList.stream().map(session -> session.getUsername()).collect(Collectors.toList());

            responsePacket.setGroupMembers(members);
        }

        channelHandlerContext.channel().writeAndFlush(responsePacket);
    }
}
