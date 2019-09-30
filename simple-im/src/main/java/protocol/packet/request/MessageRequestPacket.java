package protocol.packet.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import protocol.packet.Packet;

import static protocol.cmd.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message =  message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
