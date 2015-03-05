package com.zhaodj.foo.shiro;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;

public class PasswordDemo {
    
    public static void main(String[] args){
        PasswordService ps=new DefaultPasswordService();
        System.out.println(ps.encryptPassword("liveshow@Q#W"));
    }

}
