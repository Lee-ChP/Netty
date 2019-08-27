package protocol.packet.request;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMembersRequestPacket extends Packet {
    private String groupId;
    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
