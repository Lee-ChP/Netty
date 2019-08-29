package protocol.packet.response;

import protocol.packet.Packet;

import static protocol.cmd.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
