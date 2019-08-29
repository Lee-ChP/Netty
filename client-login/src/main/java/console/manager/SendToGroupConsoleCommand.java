package console.manager;

import console.ConsoleCommand;
import io.netty.channel.Channel;
import protocol.packet.request.SendToGroupRequestPacket;
import utils.SessionUtil;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        System.out.println("发送群组消息 [格式： 群id 空格 消息]");
        String groupId = sc.next();
        String msg = sc.next();
        String username = SessionUtil.getSession(channel).getUsername();
        SendToGroupRequestPacket requestPacket = new SendToGroupRequestPacket();
        requestPacket.setGroupId(groupId);
        requestPacket.setMsg(msg);
        requestPacket.setUsername(username);
        channel.writeAndFlush(requestPacket);
    }
}
