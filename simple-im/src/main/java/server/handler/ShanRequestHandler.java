package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.Card;
import protocol.packet.request.ShanRequestPacket;
import protocol.packet.response.ShanResponsePacket;
import session.Session;
import utils.SessionUtil;

import java.util.List;

@ChannelHandler.Sharable
public class ShanRequestHandler extends SimpleChannelInboundHandler<ShanRequestPacket> {
    public static final ShanRequestHandler INSTANCE = new ShanRequestHandler();
    public ShanRequestHandler() {
        
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ShanRequestPacket shanRequestPacket) throws Exception {
        ShanResponsePacket shanResponsePacket = new ShanResponsePacket();
        shanResponsePacket.setVersion(shanRequestPacket.getVersion());

        //获取出牌方的信息
        Session session = SessionUtil.getSession(channelHandlerContext.channel());
        
        // 一系列校验通过之后
        List<Card> cardList = session.getPlayer().getHandCards();
        //牌进入弃牌堆
        for (Card card : cardList) {
            if (card.getName().equals("Shan")) {
                cardList.remove(card);
                break;
            }
        }
        Channel executorChannel = SessionUtil.getChannel(session.getUserId());

        //设置状态为false
        ChannelManage.channelBooleanMap.put(executorChannel, false);
        //获取目标id
        String targetId = shanRequestPacket.getTargetId();
        //查找id映射的连接
        Channel targetChannel = SessionUtil.getChannel(targetId);
        //设置状态为true
        ChannelManage.channelBooleanMap.put(targetChannel,true);

        //响应信息
        String msg1 = "闪现成功！";
        shanResponsePacket.setReason(msg1);
        shanResponsePacket.setSuccess(true);
        executorChannel.writeAndFlush(shanResponsePacket);

        //询问下一步操作
        String msg =session.getUsername() + " 已出闪，您的杀无效。";
        System.out.println(msg);
        shanResponsePacket.setReason(msg);
        shanResponsePacket.setSuccess(true);
        targetChannel.writeAndFlush(shanResponsePacket);
    }
}
