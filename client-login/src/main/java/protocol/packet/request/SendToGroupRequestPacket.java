package protocol.packet.request;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.SEND_TO_GROUP_REQUEST;

@Data
public class SendToGroupRequestPacket extends Packet {
    private String groupId;
    private String msg;
    private String username;
    @Override
    public Byte getCommand() {
        return SEND_TO_GROUP_REQUEST;
    }
}
