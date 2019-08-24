package protocol.packet.request;

import lombok.Data;
import protocol.cmd.Command;
import protocol.packet.Packet;

/**
 * 登录请求数据包
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
