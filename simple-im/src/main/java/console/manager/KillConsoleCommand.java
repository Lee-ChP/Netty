package console.manager;

import console.ConsoleCommand;
import io.netty.channel.Channel;
import protocol.packet.request.KillRequestPacket;

import java.util.Scanner;

public class KillConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        KillRequestPacket killRequestPacket = new KillRequestPacket();
        System.out.print("输入杀的对象 ： ");
        String targetId = sc.next();
        killRequestPacket.setTargetId(targetId);
        channel.writeAndFlush(killRequestPacket);
    }
}
