package protocol.packet.request;

import lombok.Data;
import model.Player;
import protocol.cmd.Command;
import protocol.packet.Packet;

/**
 * 登录请求数据包
 */
@Data
public class LoginRequestPacket extends Packet {
    private String username;
    private String password;
    private Player player;

    public LoginRequestPacket() {
        this.player = new Player();
    }
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
