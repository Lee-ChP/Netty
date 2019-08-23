package client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.packet.Packet;
import protocol.packet.request.LoginRequestPacket;
import protocol.packet.response.LoginResponsePacket;
import protocol.packet.PacketCodec;
import protocol.packet.response.MessageResponsePacket;
import utils.LoginUtil;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext context) {
        System.out.println(new Date() + " : 正在发送登录请求......");

        //创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("leecp");
        loginRequestPacket.setPassword("leecp");

        //编码
        ByteBuf buf = PacketCodec.INSTANCE.encode(context.alloc(),loginRequestPacket);

        //写数据
        context.channel().writeAndFlush(buf);

    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        Packet packet = PacketCodec.INSTANCE.decode(buf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                LoginUtil.markAsLogin(context.channel());
                System.out.println(new Date() + " 客户端登录成功！");
            } else {
                System.out.println(new Date() + "客户端登录失败！" + " 原因 ： " + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + " : 收到服务端消息: " + messageResponsePacket.getMessage() );
        }

    }




}
