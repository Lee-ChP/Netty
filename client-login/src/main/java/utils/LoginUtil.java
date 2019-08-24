package utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import attribute.Attributes;
import status.LoginStatus;

public class LoginUtil {

    /**
     * 设置登录标志位
     * @param channel
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(LoginStatus.isLogin);
    }

    /**
     * 判断是否有标志位： 如果有标志位，为true通过，false拒绝
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        System.out.println("loginAttr : " + loginAttr);
        return loginAttr != null;
    }
}
