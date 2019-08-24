package client;

import client.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import protocol.packet.PacketCodec;
import protocol.packet.request.MessageRequestPacket;
import utils.LoginUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8082;

    public static void main(String[] args) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host,port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + " : 连接成功！ 开始启动控制台线程......");
                Channel channel =((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0){
                System.err.println("重连次数用完，放弃连接！");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.err.println(new Date() + " : 连接失败，第" + order +"次重连...");

                bootstrap.config().group().schedule(()-> connect(bootstrap, host,port,retry -1), delay, TimeUnit.SECONDS);
            }
        });
    }

    //启动控制台线程从控制台获取消息
    private static void startConsoleThread(Channel channel) {

        new Thread(()-> {
           while (!Thread.interrupted()) {
               if (LoginUtil.hasLogin(channel)) {
                   System.out.println("输入消息发送至服务端:");
                   Scanner sc = new Scanner(System.in);
                   String line = sc.nextLine();

                   MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                   messageRequestPacket.setMessage(line);
                   ByteBuf byteBuf = PacketCodec.INSTANCE.encode(channel.alloc(),messageRequestPacket);
                   channel.writeAndFlush(byteBuf);
               }
           }
        }).start();
    }

}

