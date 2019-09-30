package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.packet.request.HeartBeatRequestPacket;

import java.util.concurrent.TimeUnit;

/**
 * 客户端定时心跳
 */
public class HeartBeatTimeHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);

    }

    /**
     * 定时任务
     */
    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
           if (ctx.channel().isActive()) {
               ctx.writeAndFlush(new HeartBeatRequestPacket());
               scheduleSendHeartBeat(ctx);
           }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }

}
