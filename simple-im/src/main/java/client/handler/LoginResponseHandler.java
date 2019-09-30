package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.response.LoginResponsePacket;
import session.Session;
import utils.SessionUtil;


/**
 * 处理登录响应二进制包：
 * SimpleChannelInboundHandler<T> 可以自动将byteBuf二进制数据转成T类型，所以这里无需像ClientHandler那样手动解码
 *
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    /**
     * 可以对比ClientHandler类中的处理代码
     * @param channelHandlerContext
     * @param loginResponsePacket
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) {
        if (loginResponsePacket.isSuccess()) {
            System.out.println("UserName : "+loginResponsePacket.getUserName() + "; UserId : " + loginResponsePacket.getUserId() + " : 登录成功 ！");
            //登录成功，绑定session
            SessionUtil.bindSession(new Session(loginResponsePacket.getUserId(),loginResponsePacket.getUserName()),channelHandlerContext.channel());
        } else {
            System.out.println("登录失败 ： " + loginResponsePacket.getReason() );
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) {
        System.out.println("客户端连接被关闭.");
    }



}
