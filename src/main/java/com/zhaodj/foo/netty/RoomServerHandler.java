package com.zhaodj.foo.netty;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class RoomServerHandler extends ChannelInboundHandlerAdapter {
    
    private static Map<String,UserState> USER_STATE_MAP=new ConcurrentHashMap<String, UserState>();
    private static Map<String,Set<String>> ROOM_USERS=new ConcurrentHashMap<String,Set<String>>();
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf) msg;
        try {
            String str=in.toString(CharsetUtil.UTF_8);
            System.out.print(str);
            /*while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
                System.out.flush();
            }*/
            in.clear().writeBytes(str.getBytes());
            ctx.write(in);
            ctx.flush();
        } finally {
            //ReferenceCountUtil.release(msg); // (2)
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
