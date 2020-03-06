package org.training.netty;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class NettyChannelMap {

    private static Map<String, Channel> channelMap = new HashMap<>();

    public static void add(String id, Channel socket) {
        channelMap.put(id, socket);
    }

    public static Channel get(String id) {
        return channelMap.get(id);
    }

    public static void remove(Channel id) {

    }

}
