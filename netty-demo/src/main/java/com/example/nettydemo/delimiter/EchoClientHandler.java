package com.example.nettydemo.delimiter;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter {

    private int counter;

    static final String ECHO_REQ = "Hi, Lilinfeng. Welcome to Netty.$_";

    public EchoClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] infos = UserInfo();
        for (UserInfo infoE : infos) {
            ctx.write(infoE);
        }
    }

    private UserInfo[] UserInfo() {
        UserInfo[] userInfos = new UserInfo[100];
        UserInfo userInfo;
        for (int i = 0; i < 100; i++) {
            userInfo = new UserInfo();
            userInfo.setA(200);
            userInfo.setB("zzxcc" + i);
            userInfos[i] = userInfo;
        }
        return userInfos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("This is " + ++counter + " times receive server : [" + msg + "]");
        System.out.println("Client receive the msgpack message : " + msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
