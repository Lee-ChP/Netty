package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 空闲检测连接是否假死
 * 该handler放在server的最前面，防止因为inbound传播过程中出错，数据未传到最后，导致误判假死
 */
public class IMIdleStateHandler extends IdleStateHandler {

    //定义： 读的空闲时间
    private static final int READER_IDLE_TIME =15;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    /**
     * 连接假死后会调用的方法
     * @param ctx
     * @param evt
     */
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        System.out.println(READER_IDLE_TIME + "秒未读到数据，关闭连接");
        ctx.channel().close();
    }
}
