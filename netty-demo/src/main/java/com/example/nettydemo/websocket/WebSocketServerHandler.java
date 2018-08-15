package com.example.nettydemo.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }

        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s  frame types not supported", frame.getClass().getName()));
        }

        String request = ((TextWebSocketFrame) frame).text();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(String.format("%s received %s", ctx.channel(), request));
        }

        ctx.channel().write(new TextWebSocketFrame(request + " , 欢迎使用Netty WebSocket服务，现在时刻：" + new Date().toString()));
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {
        if (!msg.getDecoderResult().isSuccess() || (!"websocket".equals(msg.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, msg, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8085/websocket", null, false);
        handshaker = wsFactory.newHandshaker(msg);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), msg);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
