package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import utils.LoginUtil;
import utils.SessionUtil;

public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {

        if (!SessionUtil.hasLogin(context.channel())) {
            context.channel().close();
        } else {
            //登录成功后，移除校验器，避免重复使用
            context.pipeline().remove(this);
            super.channelRead(context, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
