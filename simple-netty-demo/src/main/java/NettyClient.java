import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.concurrent.TimeUnit;


public class NettyClient {

    public static void main(String[] args) {
        //创建线程组
        NioEventLoopGroup worker = new NioEventLoopGroup();

        //创建引导类
        Bootstrap bootstrap = new Bootstrap();

        //配置引导类
        bootstrap.group(worker)
                //定义io模型
                .channel(NioSocketChannel.class)

                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //定义数据处理逻辑
                        socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0 ,4)).addLast(new FirstClientHandler());
                    }
                });

       /* //连接

        bootstrap.connect("localhost", 8081).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！");
            } else {
                System.out.println("连接失败！");
            }
        });*/
       connnect(bootstrap, 8081, 10);
    }

    //端口失败重连, 最多重连10次
    private static void connnect(Bootstrap bootstrap, final int port, int MAX_RETRY) {


        bootstrap.connect("localhost", port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else {
                System.out.printf("连接失败,正在尝试重连...... %n");
                if (MAX_RETRY < 1) {
                    System.out.println("请联系系统管理员！");
                    System.exit(1);
                }
                bootstrap.config().group().schedule(()-> connnect(bootstrap, 8081, MAX_RETRY-1), 2, TimeUnit.SECONDS);
            }
        });
    }
}
