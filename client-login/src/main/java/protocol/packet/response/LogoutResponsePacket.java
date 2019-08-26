package protocol.packet.response;

import lombok.Data;
import protocol.packet.Packet;

import static protocol.cmd.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {
    private boolean success;
    private String reason;
    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
