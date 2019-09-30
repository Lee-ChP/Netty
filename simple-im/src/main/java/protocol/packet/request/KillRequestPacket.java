package protocol.packet.request;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.KILL_REQUEST;

/**
 * 杀请求
 */
@Data
public class KillRequestPacket extends Packet {
    private String targetId;
    
    @Override
    public Byte getCommand() {
        return KILL_REQUEST;
    }
}
