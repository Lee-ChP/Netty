package client;

import client.handler.LoginRequestHandler;
import client.handler.LoginResponseHandler;
import client.handler.MessageResponseHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import protocol.packet.codec.PacketDecoder;
import protocol.packet.codec.PacketEncoder;
import protocol.packet.request.LoginRequestPacket;
import protocol.packet.request.MessageRequestPacket;
import utils.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
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
                        socketChannel.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4))  //粘包
                                .addLast(new PacketDecoder())
                                //.addLast(new LoginRequestHandler())
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessageResponseHandler())
                                .addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + " : 连接服务器成功......");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重连次数用完，放弃连接！");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.err.println(new Date() + " : 连接失败，第" + order + "次重连...");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }


    //启动控制台线程从控制台获取消息
    private static void startConsoleThread(Channel channel) {

        System.out.println("控制台已经开启！");
        //标识为true则开启控制台
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
            while (!Thread.interrupted()) {
                //如果未登录，则登录
                if (!SessionUtil.hasLogin(channel)) {

                    loginRequestPacket.setUserId(UUID.randomUUID().toString());
                    loginRequestPacket.setUsername("Lee");
                    loginRequestPacket.setPassword("19930714");

                    channel.writeAndFlush(loginRequestPacket);
                    waitForLoginResponse();
                } else {
                    System.out.printf("输入对方id以及信息: 格式 [id 空格 message] \t");
                    String userId = sc.next();
                    String msg = sc.next();

                    channel.writeAndFlush(new MessageRequestPacket(userId, msg));
                }
            }
        }).start();
    }
    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}

