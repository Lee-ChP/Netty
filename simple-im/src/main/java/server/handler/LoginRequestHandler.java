package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.request.LoginRequestPacket;
import protocol.packet.response.LoginResponsePacket;
import session.Session;
import utils.IDUtil;
import utils.LoginUtil;
import utils.SessionUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 旧代码（ClientHandler 和 ServerHandler）中，需要手动处理类型判断和对象传递，SimpleChannelInboundHandler<T>帮我们处理了，让我们专注于指令
 */

//处理登录请求

/**
 * 登录校验经过后，应该创建一个与登录用户信息映射的channel，
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    
    //单例模式 ： 构造函数不可对外
    private LoginRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket){
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());
        
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IDUtil.randomId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[ " + loginRequestPacket.getUsername() + " 登录成功 ！]");
            System.out.println("Player Info [Player : " + loginRequestPacket.getPlayer().getUserId() + " \n\thandCards :[ " + loginRequestPacket.getPlayer().getHandCards().toString() + " ]\n");
            SessionUtil.bindSession(new Session(loginResponsePacket.getUserId(),loginResponsePacket.getUserName(),loginRequestPacket.getPlayer()),channelHandlerContext.channel());
            if (loginRequestPacket.getPlayer().getIndex() == 1) {
                ChannelManage.putChannel(channelHandlerContext.channel(), true);
            } else {
                ChannelManage.putChannel(channelHandlerContext.channel(), false);
            }

            System.out.println(loginRequestPacket.getPlayer().getIndex());
            System.out.println("Check Status ：" + ChannelManage.checkStatus(channelHandlerContext.channel()));
            System.out.println("ChannelManageMap : " + ChannelManage.channelBooleanMap);
            
        } else {
            loginResponsePacket.setReason("账户密码校验失败！");
            loginResponsePacket.setSuccess(false);
        }
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    //退出登录解除绑定
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
