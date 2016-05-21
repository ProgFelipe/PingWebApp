package com.pingapp.rabbitmq;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Rabbit{

  public static Channel sendChannel;
  public static Channel reciveChannel;
  public static Connection connection;
  public Send send;
  public Recive recive;
  
  
  public static void createConnections()throws Exception {
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    connection = factory.newConnection();
	    sendChannel = connection.createChannel();
	    reciveChannel = connection.createChannel();
	  }
  
  public void closeConnection()throws Exception {
	sendChannel.close();
	connection.close();  
  }
}