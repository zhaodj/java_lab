package com.zhaodj.foo.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.googlecode.protobuf.pro.duplex.PeerInfo;
import com.googlecode.protobuf.pro.duplex.client.DuplexTcpClientPipelineFactory;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
import com.zhaodj.foo.protobuf.DemoProtocol.CentralService;
import com.zhaodj.foo.protobuf.DemoProtocol.Host;
import com.zhaodj.foo.protobuf.DemoProtocol.Result;
import com.zhaodj.foo.protobuf.DemoProtocol.Session;
import com.zhaodj.foo.protobuf.DemoProtocol.User;

public class CentralServiceImpl extends CentralService {
    
    private static Map<String,Session> SESSION_MAP=new ConcurrentHashMap<String, Session>();

    @Override
    public void allocate(RpcController controller, User request,
            RpcCallback<Host> done) {
        Session session=SESSION_MAP.get(request.getId());
        if(session!=null){
            
        }
        Host host=Host.newBuilder().setHost("127.0.0.1").setPort(8001).build();
        System.out.println("allocate host:"+host.toString());
        done.run(host);
    }

    @Override
    public void record(RpcController controller, Session request,
            RpcCallback<Result> done) {
        SESSION_MAP.put(request.getUserId(), request);
        System.out.println("record session:"+request);
        Result result=Result.newBuilder().build();
        done.run(result);
    }

    @Override
    public void remove(RpcController controller, User request,
            RpcCallback<Result> done) {
        SESSION_MAP.remove(request.getId());
        System.out.println("remove session:"+request.getId());
        Result result=Result.newBuilder().build();
        done.run(result);
    }
    
    private void noticeHostCloseConnection(Session session){
        PeerInfo client = new PeerInfo("clientHostname", 1234);
        PeerInfo server = new PeerInfo("serverHostname", 8888);
        DuplexTcpClientPipelineFactory clientFactory = new DuplexTcpClientPipelineFactory(client);
        RpcServerCallExecutor executor = new ThreadPoolCallExecutor(3, 100);
        clientFactory.setRpcServerCallExecutor(executor);
        clientFactory.setConnectResponseTimeoutMillis(10000);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.handler(clientFactory);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000);
        bootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
        bootstrap.option(ChannelOption.SO_RCVBUF, 1048576);
    }

}
