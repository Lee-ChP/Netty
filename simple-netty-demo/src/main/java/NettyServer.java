import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {

    public static void main(String[] args) {
        //STEP1: 创建线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        //STEP2: 创建启动引导类
        ServerBootstrap bootstrap = new ServerBootstrap();

        //STEP3: 在启动引导类中：
        bootstrap.group(boss, worker) // 配置线程组、
                .channel(NioServerSocketChannel.class) // 通过channel指定io模型【io / nio】、 替换成OioServerSocketChannel.class去声明阻塞式io
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 定义新进数据处理逻辑
                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());

                    }
                });

     /*   //STEP4: 配置好启动引导类之后，绑定端口 (异步)
        bootstrap.bind(8000)
                //添加一个监听器去判断是否绑定成功
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("端口绑定成功： {8000}" );
                        } else {
                            System.out.println("端口绑定失败！");
                        }
                    }
                });*/

        //自动绑定递增端口
        bind(bootstrap,8081);
    }

    //自定义一个自增绑定端口方法（如果给定的初始端口无法绑定，则端口号自增1直至绑定成功）
    private static void bind(final ServerBootstrap bootstrap, final int port) {
        bootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.printf("端口绑定成功： { %d } ", port);
                } else {
                    System.out.printf("绑定绑定失败： { %d }, 正在尝试绑定 { %d } 端口 ", port, port+1);
                    bind(bootstrap, port+1);
                }
            }
        });
    }
}
