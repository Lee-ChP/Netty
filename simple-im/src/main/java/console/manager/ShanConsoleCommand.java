package console.manager;

import console.ConsoleCommand;
import io.netty.channel.Channel;
import protocol.packet.request.ShanRequestPacket;

import java.util.Scanner;

public class ShanConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        ShanRequestPacket shanRequestPacket = new ShanRequestPacket();
        System.out.print("输入闪的对象 ： ");
        String targetId = sc.next();
        shanRequestPacket.setTargetId(targetId);
        channel.writeAndFlush(shanRequestPacket);
    }
}
