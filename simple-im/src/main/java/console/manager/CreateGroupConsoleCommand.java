package console.manager;

import console.ConsoleCommand;
import io.netty.channel.Channel;
import protocol.packet.request.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USER_ID_SPLITER = ",";
    @Override
    public void exec(Scanner sc, Channel channel) {

        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        System.out.println("[拉人群聊 ： ] 输入userId列表，使用英文逗号符分割；");
        String userId = sc.next();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userId.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
