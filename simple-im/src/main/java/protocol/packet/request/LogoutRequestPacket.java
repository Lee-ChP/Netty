package protocol.packet.request;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
