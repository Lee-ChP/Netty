package server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.packet.request.SendToGroupRequestPacket;
import protocol.packet.response.SendToGroupResponsePacket;
import utils.SessionUtil;

@ChannelHandler.Sharable
public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {

    public static final SendToGroupRequestHandler INSTANCE = new SendToGroupRequestHandler();
    //单例模式 ： 构造函数不可对外
    private SendToGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupRequestPacket sendToGroupRequestPacket) throws Exception {
        String groupId = sendToGroupRequestPacket.getGroupId();
        String msg = sendToGroupRequestPacket.getMsg();
        String user = sendToGroupRequestPacket.getUsername();

        SendToGroupResponsePacket responsePacket = new SendToGroupResponsePacket();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            responsePacket.setReason("查无此群");
            responsePacket.setSuccess(false);
            channelHandlerContext.channel().writeAndFlush(responsePacket);
            return;
        } else {
            responsePacket.setSuccess(true);
        }
        responsePacket.setMsg(msg);
        responsePacket.setFromUser(user);

        channelGroup.stream().forEach(channel -> {
            if (channel != channelHandlerContext.channel()) {
                channel.writeAndFlush(responsePacket);
            }
        });
    }
}
