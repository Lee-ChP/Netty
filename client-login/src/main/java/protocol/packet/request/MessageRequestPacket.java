package protocol.packet.request;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
