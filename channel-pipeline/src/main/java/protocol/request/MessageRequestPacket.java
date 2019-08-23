package protocol.request;

import lombok.Data;
import protocol.Packet;

import static protocol.command.Command.MESSAGE_REQUEST;


@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
