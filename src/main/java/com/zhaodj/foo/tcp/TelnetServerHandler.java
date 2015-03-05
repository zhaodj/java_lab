/*
 * Copyright 2012 The Netty Project The Netty Project licenses this file to you
 * under the Apache License, version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the
 * License at: http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package com.zhaodj.foo.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetAddress;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles a server-side channel.
 */
@Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = Logger
            .getLogger(TelnetServerHandler.class.getName());

    private static final ChannelGroup channels = new DefaultChannelGroup(
            GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send greeting for a new connection.
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName()
                + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.write("Your remote address is:"+ctx.channel().remoteAddress()+" \r\n");
        ctx.flush();
        channels.add(ctx.channel());
        System.out.println(this);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request)
            throws Exception {
        System.out.println(ctx);
        //心跳包
        if ("ping".equals(request)) {
            ctx.channel().writeAndFlush("OK\n");
        } else {
            System.out.println("channels size:" + channels.size());
            for (Channel c: channels) {
                if (c != ctx.channel()) {
                    c.writeAndFlush("[" + c.remoteAddress() + "] " + request
                            + "\n");
                } else {
                    c.writeAndFlush("[you] " + request + "\n");
                }
            }
        }
        if ("bye".equals(request.toLowerCase())) {
            ctx.close();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("unregistered:" + ctx.channel().remoteAddress());
        ctx.fireChannelUnregistered();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("inactive:" + ctx.channel().remoteAddress());
        ctx.fireChannelInactive();
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx)
            throws Exception {
        System.out.println("writability changed:"
                + ctx.channel().remoteAddress());
        ctx.fireChannelWritabilityChanged();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }
        IdleStateEvent e = (IdleStateEvent) evt;
        if (e.state() == IdleState.READER_IDLE) {
            System.out.println("server reader idle:"+ctx.channel().remoteAddress());
            ctx.close();
        }
        //super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("exception:" + cause.getMessage() + ","
                + ctx.channel().remoteAddress());
        logger.log(Level.WARNING, "Unexpected exception from downstream.",
                cause);
        ctx.close();
    }
}
