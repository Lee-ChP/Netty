import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends  Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<Byte, Class<? extends Packet>>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap = new HashMap<Byte, Serializer>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(),serializer);
    }

    //编码
    public ByteBuf encode(Packet packet) {
        //创建 ByteBuf对象
        ByteBuf buf = ByteBufAllocator.DEFAULT.ioBuffer(); //ioBuffer返回一个适配io读写的内存，会尽可能创建一个直接内存而不受jvm管理，效果更高

        //序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 实际编码过程
        buf.writeInt(MAGIC_NUMBER);
        buf.writeByte(packet.getVersion());
        buf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buf.writeByte(packet.getCommand());
        buf.writeInt(bytes.length);
        buf.readBytes(bytes);

        return buf;
    }

    //解码

    public Packet decode(ByteBuf byteBuf) {
        //跳过magic number 读指针后移
        byteBuf.skipBytes(4); // int 占四个字节

        //跳过版本号 读指针后移
        byteBuf.skipBytes(1);

        //序列化算法标识 读指针后移
        byte serializeAlgorithm = byteBuf.readByte();

        //获取指令
        byte commad = byteBuf.readByte();

        //数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends  Packet> requestType = getRequestType(commad);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType,bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
