import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    // 客户端连接成功后调用
    public void channelActive(ChannelHandlerContext context) {
        System.out.println(new Date() + " : 客户端写出数据");

        // 1. 获取数据
        ByteBuf buf = getByByteBuf(context);

        // 2. 写数据
        context.channel().writeAndFlush(buf);
    }

    // 客户端读取服务端返回的数据

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        ByteBuf buf = (ByteBuf) msg;

        System.out.println(new Date() + " : 客户端已收到数据 <- " + buf.toString(Charset.forName("UTF-8")));
    }

    private ByteBuf getByByteBuf(ChannelHandlerContext context) {
        // 1. 获取二进制抽象ByteBuf
        // alloc分配一个ByteBuf的内存管理器
        ByteBuf buf = context.alloc().buffer();

        // 2. 准备数据，指定字符串集为utf-8
        byte[] bytes = "你好 netty ! ".getBytes(Charset.forName("UTF-8"));

        // 3. 填充数据到ByteBuf

        buf.writeBytes(bytes);

        return buf;
    }

}
