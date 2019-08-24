package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.request.LoginRequestPacket;
import protocol.packet.response.LoginResponsePacket;

import java.util.Date;

/**
 * 旧代码（ClientHandler 和 ServerHandler）中，需要手动处理类型判断和对象传递，SimpleChannelInboundHandler<T>帮我们处理了，让我们专注于指令
 */

//处理登录请求
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket){

        System.out.println(new Date() + " : 收到客户端的登录请求......");

        //构建响应
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + " : 登录成功！");
        } else {
            loginResponsePacket.setReason("账户密码校验失败！");
            loginResponsePacket.setSuccess(false);
            System.out.println("登录失败！");
        }
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return "Lee".equals(loginRequestPacket.getUsername())  && "19930714".equals(loginRequestPacket.getPassword()) && loginRequestPacket.getCommand() == 1;
    }
}
