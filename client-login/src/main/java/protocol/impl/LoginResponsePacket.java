package protocol.impl;

import lombok.Data;
import protocol.Command;
import protocol.Packet;

@Data
public class LoginResponsePacket  extends Packet {
    private boolean success;

    private String reason;
    @Override
    public byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
