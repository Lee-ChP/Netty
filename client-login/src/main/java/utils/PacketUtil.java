package utils;

import io.netty.buffer.ByteBuf;
import protocol.packet.Packet;
import protocol.packet.request.LoginRequestPacket;
import protocol.packet.request.MessageRequestPacket;
import protocol.packet.response.LoginResponsePacket;
import protocol.packet.response.MessageResponsePacket;
import protocol.serialization.JSONSerializer;
import protocol.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

import static protocol.cmd.Command.*;

/**
 * 数据包编码与解码工具类
 */
public class PacketUtil {

    private static final int MAGIC_NUMBER = 0x19930714;

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;
    public static final PacketUtil INSTANCE = new PacketUtil();
    private PacketUtil() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializeAlgorithm(), serializer);
    }

    /**
     * 编码
     * @param byteBuf
     * @param packet
     */
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // STEP1 ： 序列化 packet
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        // STEP2 ： 构建请求包
        byteBuf.writeInt(MAGIC_NUMBER); //魔数
        byteBuf.writeByte(packet.getVersion()); //版本
        byteBuf.writeByte(Serializer.DEFAULT.getSerializeAlgorithm()); // 编码方式
        byteBuf.writeByte(packet.getCommand()); //指令
        byteBuf.writeInt(bytes.length); // 真正请求数据的长度
        byteBuf.writeBytes(bytes); //真正的请求数据

        return byteBuf; // 返回编码后的数据
    }

    /**
     * 解码
     * @param byteBuf
     */
    public Packet decode(ByteBuf byteBuf) {
        //暂不处理魔数
        byteBuf.skipBytes(4);
        //暂不处理版本
        byteBuf.skipBytes(1);
        //获取编码方式
        byte serializerAlgorithm = byteBuf.readByte();
        //获取指令
        byte cmd = byteBuf.readByte();
        //获取请求数据长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = this.getRequestType(cmd);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if (requestType != null && serializer != null)
        {
            return serializer.deSerialize(requestType, bytes);
        } else
        {
            return null;
        }
    }

    private  Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
