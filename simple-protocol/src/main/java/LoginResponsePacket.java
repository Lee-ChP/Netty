import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

}
