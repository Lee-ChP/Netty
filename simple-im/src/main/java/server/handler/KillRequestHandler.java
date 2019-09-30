package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.Card;
import protocol.packet.request.KillRequestPacket;
import protocol.packet.response.KillResponsePacket;
import session.Session;
import utils.SessionUtil;

import java.util.List;

@ChannelHandler.Sharable
public class KillRequestHandler extends SimpleChannelInboundHandler<KillRequestPacket> {
    public static final KillRequestHandler INSTANCE = new KillRequestHandler();
    private KillRequestHandler() {
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, KillRequestPacket killRequestPacket) throws Exception {
        KillResponsePacket killResponsePacket = new KillResponsePacket();
        killResponsePacket.setVersion(killRequestPacket.getVersion());

        //获取出牌方的信息
        Session session = SessionUtil.getSession(channelHandlerContext.channel());
        System.out.println(session.getUsername() + " 正在出杀......");
        // 一系列校验通过之后
        List<Card> cardList = session.getPlayer().getHandCards();
        //牌进入弃牌堆
        for (Card card : cardList) {
            if (card.getName().equals("Kill")) {
                cardList.remove(card);
                break;
            }
        }
        //获取出牌放channel
        System.out.println("TargetId = " + killRequestPacket.getTargetId());
        Channel executorChannel = SessionUtil.getChannel(session.getUserId());
       
        //设置状态为false
        ChannelManage.channelBooleanMap.put(executorChannel, false);
        //获取目标id
        String targetId = killRequestPacket.getTargetId();
        //查找id映射的连接
        Channel targetChannel = SessionUtil.getChannel(targetId);
        //设置状态为true
        ChannelManage.channelBooleanMap.put(targetChannel,true);
       
        //响应信息
        String msg1 = "正在等待对方出闪！";
        killResponsePacket.setReason(msg1);
        killResponsePacket.setSuccess(true);
        executorChannel.writeAndFlush(killResponsePacket);
        
        //询问是否出闪
        String msg =session.getUsername() + " 对您出杀， 是否打出闪 ？";
        killResponsePacket.setReason(msg);
        killResponsePacket.setSuccess(true);
        targetChannel.writeAndFlush(killResponsePacket);
       
        
    }
}
