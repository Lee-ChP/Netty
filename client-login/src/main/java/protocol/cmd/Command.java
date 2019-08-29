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
    Byte LOGOUT_REQUEST = 5;
    Byte LOGOUT_RESPONSE = 6;
    Byte CREATE_GROUP_REQUEST = 7;
    Byte CREATE_GROUP_RESPONSE = 8;
    Byte JOIN_GROUP_REQUEST = 9;
    Byte JOIN_GROUP_RESPONSE = 10;
    Byte LIST_GROUP_MEMBERS_REQUEST = 11;
    Byte LIST_GROUP_MEMBERS_RESPONSE = 12;
    Byte QUIT_GROUP_REQUEST = 13;
    Byte QUIT_GROUP_RESPONSE = 14;
    Byte SEND_TO_GROUP_REQUEST = 15;
    Byte SEND_TO_GROUP_RESPONSE = 16;
    Byte HEARTBEAT_REQUEST = 17;
    Byte HEARTBEAT_RESPONSE = 18;

}
