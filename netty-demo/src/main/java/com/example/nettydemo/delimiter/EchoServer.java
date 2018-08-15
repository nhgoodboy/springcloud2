package com.example.nettydemo.delimiter;

import com.example.nettydemo.messagepack.MsgpackDecoder;
import com.example.nettydemo.messagepack.MsgpackEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

    public void bind(int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
////                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
//                            ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
//                            ch.pipeline().addLast(new StringDecoder());
//                            ch.pipeline().addLast(new EchoServerHandler());
                            ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(
                                    65535, 0, 2, 0, 2));
                            ch.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                            ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8085;
        new EchoServer().bind(port);
    }
}
