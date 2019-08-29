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
                       /* //nioSocketChannel.pipeline().addLast(new ServerHandler());
                        // inBound，处理读数据的逻辑链
                        ch.pipeline().addLast(new ServerHandlerA());
                        ch.pipeline().addLast(new ServerHandlerB());
                        ch.pipeline().addLast(new ServerHandlerC());

                        // outBound，处理写数据的逻辑链
                        ch.pipeline().addLast(new OutHandlerA());
                        ch.pipeline().addLast(new OutHandlerB());
                        ch.pipeline().addLast(new OutHandlerC());*/
                        ch.pipeline().addLast(new Spliter()); //拆包
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        //ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        //添加登录校验handler，后续handler每一次执行都需要调用一次

                        ch.pipeline().addLast( AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                       /* ch.pipeline().addLast( MessageRequestHandler.INSTANCE);
                        ch.pipeline().addLast( CreateGroupRequestHandler.INSTANCE);
                        ch.pipeline().addLast( JoinGroupRequestHandler.INSTANCE);
                        ch.pipeline().addLast( ListGroupMembersRequestHandler.INSTANCE);
                        ch.pipeline().addLast( QuitGroupRequestHandler.INSTANCE);
                        ch.pipeline().addLast( SendToGroupRequestHandler.INSTANCE);
                        ch.pipeline().addLast( LogoutRequestHandler.INSTANCE);*/
                        //ch.pipeline().addLast(new PacketEncoder());
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
