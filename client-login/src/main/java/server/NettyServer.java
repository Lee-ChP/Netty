package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import server.handler.ServerHandler;


import java.util.Date;

public class NettyServer {
    private static final int PORT = 8082;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        nioSocketChannel.pipeline().addLast(new ServerHandler());
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
