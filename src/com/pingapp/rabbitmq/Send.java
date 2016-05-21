package com.pingapp.rabbitmq;
import com.rabbitmq.client.Channel;

public class Send{
	
	  private final static String QUEUE_NAME = "pongChannel";
	  Channel channel;
	  
	  public void setSendChannel(Channel sendChannel){
		  channel = sendChannel;
	  }
	  
	  public void sendMultipleMessage(int number)throws Exception {
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    for(int c = 1; c <= number; c++){
    	String message = "Message #"+c;
        System.out.println("(Ping): Message Sended '" + message + "'");
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
	    }
	  }
}
