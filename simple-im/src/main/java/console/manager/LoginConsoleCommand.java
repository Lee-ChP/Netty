package console.manager;

import console.ConsoleCommand;
import io.netty.channel.Channel;
import protocol.packet.request.LoginRequestPacket;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner sc, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.println("输入用户名与密码登录 :");
        loginRequestPacket.setUsername(sc.nextLine());
        loginRequestPacket.setPassword(sc.nextLine());

        //发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    /**
     * 等待登录响应
     */
    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
