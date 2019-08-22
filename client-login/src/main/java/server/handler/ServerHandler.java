package server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.Packet;
import protocol.impl.LoginRequestPacket;
import protocol.impl.LoginResponsePacket;
import protocol.impl.PacketCodec;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext context, Object msg) {
        System.out.println(new Date() +": 客户端开始登录......");
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

        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return "leecp".equals(loginRequestPacket.getUsername())  && "leecp".equals(loginRequestPacket.getPassword()) && loginRequestPacket.getCommand() == 1;
    }
}
