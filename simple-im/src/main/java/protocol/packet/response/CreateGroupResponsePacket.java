package protocol.packet.response;

import lombok.Data;
import protocol.packet.Packet;

import java.util.List;

import static protocol.cmd.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket  extends Packet {

    private boolean success;
    private String groupId;
    private List<String> userNameList;


    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
