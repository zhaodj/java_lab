package com.zhaodj.foo.rabbitmq;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClusterDemo {
    
    private static void startProducer(List<String> uris, final String exchange, final String[] routes, String threadName){
        final Producer producer = new Producer(uris, exchange);
        new Thread(new Runnable(){

            @Override
            public void run() {
                Random random = new Random();
                while(true){
                    String route = routes[random.nextInt(2)];
                    producer.dispatch(route, producer.getCurUri() + ":" + exchange + ":" + route + ":" + System.currentTimeMillis());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            
        },threadName);
    }
    
    public static void main(String[] args){
        List<String> uris = new ArrayList<String>();
        String exchange = "";
        String[] routes = new String[]{"host1", "host2"};
        startProducer(uris, exchange, routes, "p1");
        startProducer(uris, exchange, routes, "p2");
        for(String route : routes){
            new Consumer(uris, exchange, route).start();
        }
    }

}
