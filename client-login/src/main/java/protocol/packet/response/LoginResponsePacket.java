package protocol.packet.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import protocol.cmd.Command;
import protocol.packet.Packet;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket  extends Packet {
    private boolean success;

    private String reason;
    @Override
    public byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
