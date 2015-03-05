package com.zhaodj.foo.rabbitmq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    
    public static final int ERR_THRESHOLD = 5;
    
    private int curIndex;
    private List<String> rabbitMQUris;
    private String exchangeName;
    private Connection connection;
    private Channel channel;
    
    private volatile boolean starting = false;
    private AtomicInteger errCount = new AtomicInteger();
    
    public Producer(List<String> rabbitMQUris, String exchangeName){
        this.rabbitMQUris = rabbitMQUris;
        this.exchangeName = exchangeName;
        restart();
    }
    
    private synchronized void restart(){
        starting = true;
        ConnectionFactory factory = new ConnectionFactory();
        for(int i = 0; i < rabbitMQUris.size(); i++){
            String uri = rabbitMQUris.get(i);
            try {
                factory.setUri(uri);
                connection = factory.newConnection();
                channel = connection.createChannel();
                channel.exchangeDeclare(exchangeName, "direct");
                curIndex = i;
                break;
            } catch (Exception e) {
                System.err.println(uri);
                e.printStackTrace();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        starting = false;
        errCount.set(0);
    };
    
    public void dispatch(String route, String message){
        try {
            channel.basicPublish(exchangeName, route, null, message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException | AlreadyClosedException ex) {
            ex.printStackTrace();
            System.err.println("err uri: " + rabbitMQUris.get(curIndex));
            int val = errCount.incrementAndGet();
            if(val > ERR_THRESHOLD && !starting){
                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        restart();
                    }
                    
                }).start();
            }
        }
    }
    
    public String getCurUri(){
        return rabbitMQUris.get(curIndex);
    }

}
