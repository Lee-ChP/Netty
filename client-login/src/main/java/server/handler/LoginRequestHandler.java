package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.request.LoginRequestPacket;
import protocol.packet.response.LoginResponsePacket;
import session.Session;
import utils.LoginUtil;
import utils.SessionUtil;

import java.util.Date;

/**
 * 旧代码（ClientHandler 和 ServerHandler）中，需要手动处理类型判断和对象传递，SimpleChannelInboundHandler<T>帮我们处理了，让我们专注于指令
 */

//处理登录请求

/**
 * 登录校验经过后，应该创建一个与登录用户信息映射的channel，
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket){

        System.out.println(new Date() + " : 收到客户端的登录请求......");
        System.out.println("请求用户信息 ： uid = " + loginRequestPacket.getUserId());

        //构建响应
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserId(loginRequestPacket.getUserId());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            System.out.println("将登录状态设置为true ： ");
           // LoginUtil.markAsLogin(channelHandlerContext.channel());
            SessionUtil.bindSession(new Session(loginResponsePacket.getUserId(),loginResponsePacket.getUserName()),channelHandlerContext.channel());

        } else {
            loginResponsePacket.setReason("账户密码校验失败！");
            loginResponsePacket.setSuccess(false);
        }
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return "Lee".equals(loginRequestPacket.getUsername())  && "19930714".equals(loginRequestPacket.getPassword()) && loginRequestPacket.getCommand() == 1;
    }

    //退出登录解除绑定
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
