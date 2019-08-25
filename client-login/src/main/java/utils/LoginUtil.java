package utils;

import attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {

    /**
     * 设置登录标志位
     * @param channel
     */
    public static void markAsLogin(Channel channel) {

        channel.attr(Attributes.LOGIN).set(true);

    }

    /**
     * 判断是否有标志位： 如果有标志位，为true通过，false拒绝
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        System.out.println("loginAttr.get() : " + loginAttr.get());
        return loginAttr.get()!= null;
    }
}
