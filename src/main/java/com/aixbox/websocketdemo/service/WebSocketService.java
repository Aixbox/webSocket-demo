package com.aixbox.websocketdemo.service;


import io.netty.channel.Channel;

public interface WebSocketService {

    /**
     * 处理所有ws连接的事件
     *
     * @param channel
     */
    void connect(Channel channel);

}
