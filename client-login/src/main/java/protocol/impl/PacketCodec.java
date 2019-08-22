package protocol.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import protocol.Packet;
import protocol.Serializer;
import protocol.SerializerAlgorithm;

import java.util.HashMap;
import java.util.Map;

import static protocol.Command.LOGIN_REQUEST;
import static protocol.Command.LOGIN_RESPONSE;

/**
 * 编码
 */
public class PacketCodec {
    private static final int MAGIC_NUMBER = 0x19930714;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Byte,Class<? extends  Packet>> packetTypeMap;
    private final Map<Byte,Serializer> serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<Byte, Class<? extends Packet>>();
        serializerMap = new HashMap<Byte, Serializer>();

        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializeAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator,Packet packet) {
        //创建ByteBufduix
        ByteBuf byteBuf = byteBufAllocator.DEFAULT.ioBuffer();

        //序列化登录请求
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //实际编码过程，填充请求包
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializeAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {

        //跳过magic_number
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        //指令
        byte command = byteBuf.readByte();
        //数据包长度
        int length = byteBuf.readInt();
        //获取数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deSerialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends  Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
