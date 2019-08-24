import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(new Date() + " : 服务器读到数据 ->" + buf.toString(Charset.forName("UTF-8")));



   /*     //向客户端回复数据
        byte[] bytes = "已收到消息 。".getBytes(Charset.forName("UTF-8"));
        System.out.println(new Date() + " : 服务端返回数据 ");
        ByteBuf out = context.alloc().buffer();

        out.writeBytes(bytes);
        context.channel().writeAndFlush(out);*/
    }
}
