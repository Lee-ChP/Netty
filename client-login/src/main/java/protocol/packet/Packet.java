package protocol.packet;

import lombok.Data;

@Data
public abstract class Packet {
    /**
     * 协议版本号
     */
    private Byte version = 1;

    /**
     * 获取指令
     */
    public abstract byte getCommand();
}
