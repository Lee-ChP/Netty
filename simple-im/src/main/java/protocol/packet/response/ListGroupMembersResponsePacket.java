package protocol.packet.response;

import lombok.Data;
import protocol.packet.Packet;

import java.util.List;

import static protocol.cmd.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {
    private List<String> groupMembers;
    private boolean success;
    private String reason;
    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
