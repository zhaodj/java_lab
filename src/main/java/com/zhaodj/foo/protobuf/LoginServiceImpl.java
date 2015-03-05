package com.zhaodj.foo.protobuf;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.zhaodj.foo.protobuf.DemoProtocol.LoginService;
import com.zhaodj.foo.protobuf.DemoProtocol.Result;
import com.zhaodj.foo.protobuf.DemoProtocol.Token;

public class LoginServiceImpl extends LoginService {

    @Override
    public void validate(RpcController controller, Token request,
            RpcCallback<Result> done) {
        if(controller.isCanceled()){
            done.run(null);
            return;
        }
        System.out.println("validate token:"+request.getToken());
        Result result=Result.newBuilder().build();
        done.run(result);
    }

}
