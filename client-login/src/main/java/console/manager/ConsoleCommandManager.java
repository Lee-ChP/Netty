package console.manager;

import console.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 控制台命令管理器
 */
public class ConsoleCommandManager implements ConsoleCommand {
    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager () {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
    }
    @Override
    public void exec(Scanner sc, Channel channel) {

        //获取第一个指令
        String cmd = sc.next();
        ConsoleCommand consoleCommand = consoleCommandMap.get(cmd);

        if (consoleCommand != null) {
            consoleCommand.exec(sc, channel);
        } else {
            System.err.println("无法识别 [ " + cmd + " ] 指令， 请重新输入 [sendToUser 、 logout、createGroup、joinGroup]");
        }
    }
}
