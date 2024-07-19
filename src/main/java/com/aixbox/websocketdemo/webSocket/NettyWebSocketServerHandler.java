package com.aixbox.websocketdemo.webSocket;

import cn.hutool.extra.spring.SpringUtil;
import com.aixbox.websocketdemo.service.WebSocketService;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebSocketService webSocketService;

    // 当web客户端连接后，触发该方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.webSocketService = getService();
    }

    /**
     * 心跳检查
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //判断事件为IdleStateEvent
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 事件状态为读空闲
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                // 关闭用户的连接
                ctx.channel().close();
            }
        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            //握手完成，将连接放入缓存
            this.webSocketService.connect(ctx.channel());
        }
        //执行后续责任链
        super.userEventTriggered(ctx, evt);
    }



    // 读取客户端发送的请求报文
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //前端可以发送一个json，根据不同的type，做不同的处理
        //打印接收到的消息
        log.info("收到客户端消息：{}", msg.text());
        //向请求回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器收到消息：" + msg.text()));
    }

    private WebSocketService getService() {
        return SpringUtil.getBean(WebSocketService.class);
    }
}
