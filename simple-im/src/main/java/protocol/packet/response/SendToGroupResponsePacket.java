package protocol.packet.response;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.SEND_TO_GROUP_RESPONSE;

@Data
public class SendToGroupResponsePacket extends Packet {
    private boolean success;
    private String reason;
    private String msg;
    private String fromUser;
    @Override
    public Byte getCommand() {
        return SEND_TO_GROUP_RESPONSE;
    }
}
