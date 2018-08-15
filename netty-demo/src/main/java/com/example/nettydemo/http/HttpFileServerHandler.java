package com.example.nettydemo.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.File;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if(!msg.getDecoderResult().isSuccess()){
            sendError(ctx, BAD_REQUEST);
            return;
        }

        if(msg.getMethod() != HttpMethod.GET){
            sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }

        final String uri = msg.getUri();
//        final String path = sanitizeUri(uri);
//        if(path == null){
//            sendError(ctx, FORBIDDEN);
//            return;
//        }

//        File file = new File(path);
//        if(file.isHidden() || !file.exists()){
//            sendError(ctx, FORBIDDEN);
//            return;
//        }
    }



    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
//        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
