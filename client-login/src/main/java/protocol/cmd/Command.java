package protocol.cmd;

/**
 * 协议头部信息 —— 指令
 */
public interface Command {
    /**
     * 登录请求标识 ： 1
     * 登录返回标识 ： 2
     */
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
}