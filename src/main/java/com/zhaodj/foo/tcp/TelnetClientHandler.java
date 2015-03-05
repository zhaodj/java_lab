/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.zhaodj.foo.tcp;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles a client-side channel.
 */
@Sharable
public class TelnetClientHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = Logger.getLogger(TelnetClientHandler.class.getName());
    private long lastPing=0L;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if(!"OK".equals(msg)){
            System.err.println(msg); 
        }else{
            System.out.println(msg);
        }
        this.lastPing=0L;   
    }
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(!(evt instanceof IdleStateEvent)){
            return;
        }
        IdleStateEvent e=(IdleStateEvent)evt;
        if(e.state()==IdleState.WRITER_IDLE){
            System.out.println("writer idle");
            ctx.channel().writeAndFlush("ping\n");
            if(lastPing==0L){
                lastPing=System.currentTimeMillis();
            }
        }else if(e.state()==IdleState.READER_IDLE){
            System.out.println("reader idle");
            if(lastPing!=0L){
                long time = (System.currentTimeMillis() - lastPing) / 1000;
                if(time>20){
                    System.out.println("client close channel");
                    ctx.close();
                }
            }
        }
        //super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
