package server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
@ChannelHandler.Sharable
public class StatusChecker extends ChannelInboundHandlerAdapter {
    public static final StatusChecker INSTANCE = new StatusChecker();
    //单例模式 ： 构造函数不可对外
    private StatusChecker() {

    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        if (!ChannelManage.channelBooleanMap.get(context.channel())) {
            System.out.println("无效请求");
        } else {
            super.channelRead(context,msg);
        }
    }
}
