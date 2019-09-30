package protocol.packet.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import protocol.cmd.Command;
import protocol.packet.Packet;

@Data
public class LoginResponsePacket  extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;
    
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
