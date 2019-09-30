package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import protocol.packet.codec.PacketDecoder;
import protocol.packet.codec.PacketEncoder;
import server.handler.*;
import server.spliter.Spliter;

import java.util.Date;

public class NettyServer {
    private static final int PORT = 8082;

    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                      
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        
                        ch.pipeline().addLast(new Spliter()); //拆包
                        
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE); // 心跳检测，不需要验证，所以放在AuthHandler之前
                        
                        ch.pipeline().addLast( AuthHandler.INSTANCE);
                        ch.pipeline().addLast(StatusChecker.INSTANCE);  //连接状态检测
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                      
                    }
                });

        bind(serverBootstrap);
    }

    private static void bind(final ServerBootstrap serverBootstrap) {
        serverBootstrap.bind(NettyServer.PORT).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + " : 绑定端口 [ " + NettyServer.PORT + " ] 成功!");
            } else {
                System.err.println(new Date() + " : 绑定端口 [ " + NettyServer.PORT + " ] 失败!");
            }
        });
    }
}
