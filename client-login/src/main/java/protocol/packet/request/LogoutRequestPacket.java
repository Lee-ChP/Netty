package protocol.packet.request;

import protocol.packet.Packet;

import static protocol.cmd.Command.LOGOUT_REQUEST;

public class LogoutRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
