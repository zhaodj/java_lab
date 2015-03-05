package com.zhaodj.foo.protobuf;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.zhaodj.foo.protobuf.DemoProtocol.HostService;
import com.zhaodj.foo.protobuf.DemoProtocol.Result;
import com.zhaodj.foo.protobuf.DemoProtocol.User;

public class HostServiceImpl extends HostService {

    @Override
    public void disconnect(RpcController controller, User request,
            RpcCallback<Result> done) {
        System.out.println("close connect:"+request.getId());
    }

}
