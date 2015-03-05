package com.zhaodj.foo.netty;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

public class ThreadModeClient {
	
	public static List<Channel> channels = new ArrayList<Channel>();
	
	public static Channel newClient(EventLoopGroup group, int index) throws InterruptedException {
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
				.handler(new ClientInitializer());
		Channel ch = b.connect("h01.live.netease.com",16160).sync().channel();
		ch.writeAndFlush("{\"action\":\"enter\",\"roomId\":100774,\"userId\":\"temp" + index + "\"}\r\n");
		return ch;
	}
	
	public static void main(String[] args) {
		
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			for(int i = 0;i < 20; i++) {
				channels.add(newClient(group, i));
			}
			Scanner sc = new Scanner(System.in);
			String line;
			while(true) {
				if((line = sc.nextLine()) != null){
					if("exit".equalsIgnoreCase(line)) {
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			// The connection is closed automatically on shutdown.
			group.shutdownGracefully();
		}
	}
	
	public static class ClientHandler extends SimpleChannelInboundHandler<String>{
		
		public static final int HEARTBEAT_INTERVAL=30;

        private long lastPing = 0L;

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg)
                        throws Exception {
        	System.out.println(Thread.currentThread().getName() + " " +ctx.channel().remoteAddress());
        		System.out.println(URLDecoder.decode(msg, "UTF-8"));
                this.lastPing=0L; 
        }
        
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                        throws Exception {
        	System.out.println(Thread.currentThread().getName() + " " +ctx.channel().remoteAddress());    
        	cause.printStackTrace();
                ctx.close();
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
                        throws Exception {
                if (!(evt instanceof IdleStateEvent)) {
                        return;
                }
                IdleStateEvent e = (IdleStateEvent) evt;
                long now=System.currentTimeMillis();
                if (e.state() == IdleState.WRITER_IDLE) {
                        ctx.channel().writeAndFlush("{\"t\":"+now+"}\r\n");
                        if (lastPing == 0L) {
                                lastPing = now;
                        }
                } else if (e.state() == IdleState.READER_IDLE) {
                        if (lastPing != 0L) {
                                long time = (now - lastPing) / 1000;
                                if (time > HEARTBEAT_INTERVAL) {
                                        System.out.println("client close channel");
                                        ctx.close();
                                }
                        }
                }
        }
        
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.err.println("inactive:" + ctx.channel().remoteAddress());
            ctx.close();
        }
		
	}
	
	public static class ClientInitializer extends ChannelInitializer<SocketChannel> {

		private ChannelHandler handler;

		public ClientInitializer() {
			handler = new ClientHandler();
		}

		public ClientInitializer(ChannelHandler handler) {
			this.handler = handler;
		}

		@Override
		public void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();

			pipeline.addLast("framer", new DelimiterBasedFrameDecoder(65535*2,
					Delimiters.lineDelimiter()));
			pipeline.addLast("decoder", new StringDecoder());
			pipeline.addLast("encoder", new StringEncoder());

			// 心跳
			pipeline.addLast("ping", new IdleStateHandler(
					ClientHandler.HEARTBEAT_INTERVAL,
					ClientHandler.HEARTBEAT_INTERVAL, 0, TimeUnit.SECONDS));

			// and then business logic.
			pipeline.addLast("handler", this.handler);
		}
	}


}
