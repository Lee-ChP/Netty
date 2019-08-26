package protocol.packet.request;

import lombok.Data;
import protocol.packet.Packet;

import java.util.List;

import static protocol.cmd.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket  extends Packet {
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
