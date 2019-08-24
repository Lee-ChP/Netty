package server.spliter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import utils.PacketUtil;

public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext context, ByteBuf in) throws Exception {
        //屏蔽非本协议的客户端 —— 即魔数不同
        if (in.getInt(in.readerIndex()) != PacketUtil.MAGIC_NUMBER) {
            System.out.println("协议匹配失败！");
            context.channel().close();
            return null;
        }

        return super.decode(context,in);


    }
}
