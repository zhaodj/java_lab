package com.zhaodj.foo.rabbitmq;

import java.util.List;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {
    
    private List<String> rabbitMQUris;
    private String exchangeName;
    private String route;
    
    public Consumer(List<String> rabbitMQUris, String exchangeName, String route){
        this.rabbitMQUris = rabbitMQUris;
        this.exchangeName = exchangeName;
        this.route = route;
    }
    
    public void start(){
        for(int i = 0; i < rabbitMQUris.size(); i++){
            new Thread(new ConsumeListener(rabbitMQUris.get(i), exchangeName, route)).start();
        }
    }
    
    private static class ConsumeListener implements Runnable{

        private String uri;
        private String exchange;
        private String route;
        private int tryCount = 0;
        
        private QueueingConsumer consumer;
        
        public ConsumeListener(String uri, String exchangeName, String route){
            this.uri = uri;
            this.exchange = exchangeName;
            this.route = route;
            restart();
        }
        
        private void restart(){
            tryCount++;
            ConnectionFactory factory = new ConnectionFactory();
            try {
                factory.setUri(uri);
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.exchangeDeclare(exchange, "direct");
                String queueName = channel.queueDeclare().getQueue();

                channel.queueBind(queueName, exchange, route);

                consumer = new QueueingConsumer(channel);
                channel.basicConsume(queueName, true, consumer);
                tryCount = 0;
            } catch (Exception e1) {
                e1.printStackTrace();
                try {
                    Thread.sleep(5000);
                    if(tryCount <= 5){
                        restart();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                    String message = new String(delivery.getBody());
                    System.out.println("receive message: [" + route + "]" + message);
                } catch (Exception e) {
                    e.printStackTrace();
                    restart();
                }

            }
        }
        
    }

}
