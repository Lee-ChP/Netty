package client.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.packet.request.MessageRequestPacket;
import protocol.packet.response.LoginResponsePacket;
import status.LoginStatus;
import utils.LoginUtil;

import java.util.Scanner;


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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket)  {
        if (loginResponsePacket.isSuccess()) {
            System.out.println("登录成功！");
            LoginStatus.isLogin = true;
            LoginUtil.markAsLogin(channelHandlerContext.channel());
            startConsoleThread(channelHandlerContext.channel());
        } else {
            System.out.println("登录失败 ： " + loginResponsePacket.getReason() );
            LoginStatus.isLogin = false;
            LoginUtil.markAsLogin(channelHandlerContext.channel());
        }
    }

    //启动控制台线程从控制台获取消息
    private static void startConsoleThread(Channel channel) {
        //标识为true则开启控制台
        if (LoginUtil.hasLogin(channel)) {
            new Thread(()-> {
                while (!Thread.interrupted()) {
                    System.out.println("输入消息发送至服务端:");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                    messageRequestPacket.setMessage(line);
                    channel.writeAndFlush(messageRequestPacket);
                }
            }).start();
        }
    }

}
