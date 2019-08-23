package protocol.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import protocol.packet.request.MessageRequestPacket;
import protocol.packet.response.MessageResponsePacket;
import protocol.serialization.JSONSerializer;
import protocol.packet.request.LoginRequestPacket;
import protocol.packet.response.LoginResponsePacket;
import protocol.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

import static protocol.cmd.Command.*;

/**
 * 编码
 */
public class PacketCodec {
    private static final int MAGIC_NUMBER = 0x19930714;
    public static final PacketCodec INSTANCE = new PacketCodec();

    /**
     * 如果byte为1 ，那么对应的就是loginRequest登录请求类
     * 如果byte为2， 那么对应的就是loginResponse登录响应类
     *
     * 这个map的作用： 用于序列化与反序列化
     */
    private final Map<Byte,Class<? extends  Packet>> packetTypeMap;

    /**
     * 该map的作用： 存储一个序列化的方法，后续根据map值获取反序列化的方法
     */
    private final Map<Byte,Serializer> serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        serializerMap = new HashMap<>();

        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializeAlgorithm(), serializer);
    }

    /**
     * 编码过程
     * @return 旧方法
     */
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

    /**
     * 新的编码方法
     */
   /* public void encode(ByteBuf byteBuf, Packet packet) {

    }*/

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
