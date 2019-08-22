package protocol.impl;

import lombok.Data;
import protocol.Command;
import protocol.Packet;

/**
 * 登录请求数据包
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;
    private String username;
    private String password;

    @Override
    public byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
