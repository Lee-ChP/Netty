package console.manager;

import console.ConsoleCommand;
import io.netty.channel.Channel;
import protocol.packet.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.println("发送消息给指定用户：");
        String toUserId = sc.next();
        String msg = sc.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, msg));
    }
}
