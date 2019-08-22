import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.Charset;

public class ByteBufTest {

    public static void main(String[] args) {
        // 9: 初始化容量； 100：扩容极限
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(9,100);
        print("初始化：allocate byteBuf(9, 100)", buf);

        //
        buf.writeBytes(new byte[]{1,2,3,4}); //写指针索引从0开始，后移后的索引为4
        print("writeBytes(1,2,3,4)", buf);
/*
        buf.writerIndex(12); //移动写指针到位置12， 如果未写满，写指针不得超过capacity的末尾9，所以此处报错
        print("writerIndex(12)", buf);*/

        buf.writeInt(12); //写入一个int，一个int等于4字节，所以此时写指针在位置8，可写容量还剩1字节
        print("writeInt(12)", buf);

        buf.writeBytes(new byte[]{5}); //写入一个字节，此时capacity已写满，writable返回false，再往后写则扩容
        print("writeBytes(5)",buf);

        buf.writeBytes(new byte[]{6}); // 开始扩容，capacity也会改变,根据扩容规则
        print("writeBytes(6): start extend capacity", buf);

        //get 方法不会改变读写指针
        /** 截至到写入int 12，byteBuf容器内的存储情况
         * byteBuf内容如下：
         *  index               byte-value              binary（真正存在byteBuf里的内容）
         *  0                     1                  0000 0001 (一个字节是8位)
         *  1                     2                  0000 0010
         *  2                     3                  0000 0011
         *  3                     4                  0000 0100
         *  ===============================================================================================================================
         *  index为4的时候，我们写入了int 12 ， 12的二进制： 1100， 因为int是4字节，32位，所以要补0，而每一个index只能存放4字节（因为是byte类型）
         *  ===============================================================================================================================
         *  4                     0                  0000 0000
         *  5                     0                  0000 0000
         *  6                     0                  0000 0000
         *  7                     0                  0000 1100
         *  8               未写入数据
         *
         */
        System.out.println("getByte(3) return : " + buf.getByte(3)); // byte 1字节占8位， 从3开始往后数8位： 0000 0100 返回值应该是4， 很好理解
        System.out.println("getShort(3) return : " + buf.getShort(3)); // short 2字节占16位，所以从index 3开始往后数16位出来：0000 0100 0000 0000 .转换成十进制类型：1024
        System.out.println("getInt(3) return : " + buf.getInt(3)); // short 4字节占32位，同理，从index 3开始往后数出32位出来：0000 0100 0000 0000 0000 0000 0000 0000，最后转十进制：67108864

        // set方法不改变读写指针位置,插入byte类型 2 到指定位置（buf.readableBytes() + 2），此时数据写在写指针之后,但是读指针只能读到写指针的位置，意味着这个2用都指针，是读不了的，除非写指针往后移
        buf.setByte(buf.readableBytes() + 2, 2);
        print("setBytes()", buf);

        // read方法改变读指针
        byte[] dst = new byte[buf.readableBytes()];
        buf.readBytes(dst);
        print("readBytes(" + dst.length + ") ", buf );

    }

    private static void print(String action, ByteBuf byteBuf) {
        System.out.println("After ================= " + action + " ======================= ");
        System.out.println("未扩容前的最大容量： capacity() : " + byteBuf.capacity());
        System.out.println("可扩容到的最大容量：maxCapacity() : " + byteBuf.maxCapacity());
        System.out.println("当前读指针的索引位置：readerIndex() : " + byteBuf.readerIndex());
        System.out.println("当前可读的字节容量 writeIndex - readerIndex ：readableBytes() : " + byteBuf.readableBytes());
        System.out.println("是否可读：isReadable() : " + byteBuf.isReadable());
        System.out.println("当前写指针的索引位置：writerIndex() : " + byteBuf.writerIndex());
        System.out.println("当前可写的容量：writableBytes() capacity - writeIndex: " + byteBuf.writableBytes());
        System.out.println("是否可写：isWritable() : " + byteBuf.isWritable());
        System.out.println("还剩下的最大可写容量：maxWritableBytes() : " + byteBuf.maxWritableBytes());
        System.out.println();
    }
}
