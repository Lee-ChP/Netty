package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.request.MessageRequestPacket;
import protocol.packet.response.MessageResponsePacket;
import session.Session;
import utils.SessionUtil;

/**
 * 发送消息 ： 获取接收方的channel， 将自己的基本用户信息打包进消息中发送过去
 */

@ChannelHandler.Sharable

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
    //单例模式 ： 构造函数不可对外
    private MessageRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) {

        /*
          构建消息响应 ： 将客户端的消息原封不动返回
         */
        // 1: 拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(channelHandlerContext.channel());

        // 2: 通过消息发送方的会话消息构建要发送的消息
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUserName(session.getUsername());
        responsePacket.setMessage(messageRequestPacket.getMessage());

        // 3: 拿到消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4: 将消息发给接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {

            toUserChannel.writeAndFlush(responsePacket);
        } else {
            System.err.println("[ " + messageRequestPacket.getToUserId() + " ] is offline, send message failed!");
        }

        //channelHandlerContext.channel().writeAndFlush(responsePacket);
    }
}
