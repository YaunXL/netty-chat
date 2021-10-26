package com.yxl.excise.server.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMemoryImpl implements Session{

    private final Map<String,Channel> usernameChannelMap = new ConcurrentHashMap<>();
    private final Map<Channel,String> channelUsernameMap = new ConcurrentHashMap<>();
    private final Map<Channel,Map<String,Object>> channelAttributeMap = new ConcurrentHashMap<>();

    @Override
    public void bind(Channel channel, String username) {
        usernameChannelMap.put(username,channel);
        channelUsernameMap.put(channel,username);
        channelAttributeMap.put(channel,new ConcurrentHashMap<>());
    }

    @Override
    public void unbind(Channel channel) {
        String username = channelUsernameMap.remove(channel);
        usernameChannelMap.remove(username);
        channelAttributeMap.remove(channel);
    }

    @Override
    public Object getAttribute(Channel channel, String name) {
        return channelAttributeMap.get(channel).get(name);
    }

    @Override
    public void setAttribute(Channel channel, String name, Object value) {
        Map<String,Object> attr = new ConcurrentHashMap<>();
        attr.put(name,value);
        channelAttributeMap.put(channel,attr);
    }

    @Override
    public Channel getChannel(String name) {
        return usernameChannelMap.get(name);
    }
}
