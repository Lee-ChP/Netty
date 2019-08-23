package server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.packet.Packet;
import protocol.packet.request.LoginRequestPacket;
import protocol.packet.request.MessageRequestPacket;
import protocol.packet.response.LoginResponsePacket;
import protocol.packet.PacketCodec;
import protocol.packet.response.MessageResponsePacket;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext context, Object msg) {
        //System.out.println(new Date() +": 客户端开始登录......");
        ByteBuf buf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(buf);

        if (packet instanceof LoginRequestPacket) {
            //登录流程
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

            loginResponsePacket.setVersion(packet.getVersion());

            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                System.out.println("登录成功！");
            } else {
                loginResponsePacket.setReason("用户名或者密码错误!");
                loginResponsePacket.setSuccess(false);
                System.out.println("登录失败！");
            }

            //登录响应
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(context.alloc(), loginResponsePacket);
            context.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            //处理消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date()+" : 收到客户端消息 ： " + messageRequestPacket.getMessage());
            //回复
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复[ " + messageRequestPacket.getMessage() + " ]");
            ByteBuf byteBuf = PacketCodec.INSTANCE.encode(context.alloc(),messageResponsePacket);
            context.channel().writeAndFlush(byteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return "leecp".equals(loginRequestPacket.getUsername())  && "leecp".equals(loginRequestPacket.getPassword()) && loginRequestPacket.getCommand() == 1;
    }
}
