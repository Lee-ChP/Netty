package utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import protocol.attribute.Attributes;

public class LoginUtil {

    /**
     * 设置登录标志位
     * @param channel
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 判断是否有标志位： 如果有标志位，不管标志位的值是什么，都表示已经登录过
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr != null;
    }
}
