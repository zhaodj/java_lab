package com.zhaodj.foo.netty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class LoginServlet extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Map<String,String> ALL_USERS=new HashMap<String,String>();
    
    private String roomServerHost;
    private int roomServerPort;
    
    static{
        ALL_USERS.put("demo", "demo");
    }
    
    public LoginServlet(String host,int port){
        this.roomServerHost=host;
        this.roomServerPort=port;
    }
        
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        String path=req.getPathTranslated();
        if(path.equals("/login")){
            login(req,resp);
        }else if(path.equals("/enterRoom")){
            enterRoom(req,resp);
        }
    }
    
    /**
     * 登录
     * @param req
     * @param resp
     * @throws IOException 
     */
    private void login(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        resp.setHeader("Content-Type", "application/json;charset=UTF-8");
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        if(StringUtils.isNotBlank(username)){
            String validPass=ALL_USERS.get(username);
            if(validPass!=null&&validPass.equals(password)){
                String token=UUID.randomUUID().toString();
                resp.getWriter().write("{\"token\":\""+token+"\"}");
                return;
            }
        }
        resp.getWriter().write("{\"message\":\"用户名或密码错误\"}");
    }
    
    /**
     * 注册token到room server
     * @param token
     */
    private void registerToRoomServer(String token){
        
    }
    
    /**
     * 进入房间
     * @param req
     * @param resp
     */
    private void enterRoom(HttpServletRequest req,HttpServletResponse resp){
        String roomId=req.getParameter("roomId");
    }

}
