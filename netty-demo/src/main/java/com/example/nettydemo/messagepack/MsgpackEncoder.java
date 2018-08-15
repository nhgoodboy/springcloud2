package com.example.nettydemo.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack msgpack = new MessagePack();
        byte[] raw = msgpack.write(msg);
        out.writeBytes(raw);
    }
}
