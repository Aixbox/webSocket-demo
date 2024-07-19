package com.aixbox.websocketdemo.service.impl;

import com.aixbox.websocketdemo.service.WebSocketService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: websocket处理类
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-19 16:21
 */
@Component
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    /**
     * 所有已连接的websocket连接列表和一些额外参数
     */
    private static final ConcurrentHashMap<Channel, String> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    /**
     * 处理所有ws连接的事件
     *
     */
    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, "");
    }
}
