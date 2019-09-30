package server.handler;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class ChannelManage {
    public static Map<Channel, Boolean> channelBooleanMap = new HashMap<>();
    
    public static void putChannel(Channel channel, boolean status) {
        channelBooleanMap.put(channel, status);
    }
    
    public static boolean checkStatus(Channel channel) {
        return channelBooleanMap.get(channel);
    }
}
