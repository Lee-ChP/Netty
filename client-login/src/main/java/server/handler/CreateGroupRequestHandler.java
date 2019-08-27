package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import protocol.packet.request.CreateGroupRequestPacket;
import protocol.packet.response.CreateGroupResponsePacket;
import utils.IDUtil;
import utils.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();

        // 1 ： 创建一个channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(channelHandlerContext.executor());

        // 2 : 筛选出待加入群聊的用户的channel和username
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUsername());
            }
        }


        // 3 ：创建群聊创建结果的响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(IDUtil.randomId());
        createGroupResponsePacket.setUserNameList(userNameList);

        // 4 ：绑定群组
        SessionUtil.bindChannelGroup(createGroupResponsePacket.getGroupId(), channelGroup);


        // 5 : 给每一个用户发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);
        System.out.println("群创建成功 ： id [ "  + createGroupResponsePacket.getGroupId() + " ]");
        System.out.println("群成员 ： " + createGroupResponsePacket.getUserNameList());

    }
}
