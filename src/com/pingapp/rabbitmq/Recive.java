package com.pingapp.rabbitmq;
import java.io.IOException;
import java.util.ArrayList;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recive {
	
	  private final static String QUEUE_NAME = "pingChannel";
	  public static Channel channel;
	  //public String response;
	  public ArrayList<String> response;
	  
	  public Recive(){
		response = new ArrayList<String>();  
	  }
	  
	  public void setReciveChannel(Channel reciveChannel){
		  channel = reciveChannel;
	  }
	  
	  public void setResponse(String responseMessage){
		  response.add(responseMessage);
	  }
	  
	  public void getJunkYard() throws IOException{
		    channel.queueDeclare(QUEUE_NAME, false, false, false, null);	        
		    Consumer consumer = new DefaultConsumer(channel) {
		      @Override
		      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		          throws IOException {
		        String message = new String(body, "UTF-8");
		        String outMessage = "(Ping): Pong processed : '" + message + "'";
		        System.out.println(outMessage);
		        String consumed = outMessage;
		        setResponse(consumed);
		      }
		    };
		    channel.basicConsume(QUEUE_NAME, true, consumer);
	  }
}
