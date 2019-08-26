package protocol.cmd;

import io.netty.buffer.ByteBuf;

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
    Byte LOGOUT_REQUEST = 5;
    Byte LOGOUT_RESPONSE = 6;
    Byte CREATE_GROUP_REQUEST = 7;
    Byte CREATE_GROUP_RESPONSE = 8;
}
