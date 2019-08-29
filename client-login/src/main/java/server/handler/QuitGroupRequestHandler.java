package server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.request.QuitGroupRequestPacket;
import protocol.packet.response.QuitGroupResponsePacket;
import utils.SessionUtil;

@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        String groupId = quitGroupRequestPacket.getGroupId();

        if (SessionUtil.getChannelGroup(groupId) == null) {
            responsePacket.setSuccess(false);
            responsePacket.setReason("查无此群.");
        } else {
            responsePacket.setSuccess(true);
            SessionUtil.unbindChannelGroup(groupId,channelHandlerContext.channel());
        }

        channelHandlerContext.channel().writeAndFlush(responsePacket);
    }
}
