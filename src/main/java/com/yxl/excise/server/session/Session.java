package com.yxl.excise.server.session;

import io.netty.channel.Channel;

public interface Session {

    /**
     * 绑定会话，便于通过名字查找客户端连接
     * @param channel
     * @param username
     */
    void bind(Channel channel,String username);

    /**
     * 解绑
     * @param channel
     */
    void unbind(Channel channel);

    Object getAttribute(Channel channel,String name);

    /**
     * 设置channel的一些属性值
     * @param channel
     * @param name
     * @param value
     */
    void setAttribute(Channel channel,String name,Object value);

    /**
     * 根据联系人获取channel
     * @param name
     * @return
     */
    Channel getChannel(String name);




}
