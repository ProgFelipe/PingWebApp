package com.pingapp.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pingapp.rabbitmq.Recive;
import com.pingapp.rabbitmq.Send;
import com.pingapp.rabbitmq.Rabbit;

/**
 * Servlet implementation class Ping
 */
@WebServlet(name="Ping", urlPatterns={"/Send"}, asyncSupported=true)
public class Ping extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Rabbit rabbitmq;
    /**
     * @throws Exception 
     * @see HttpServlet#HttpServlet()
     */
    public Ping() throws Exception {
        super();
        if(rabbitmq == null){
          rabbitmq = new Rabbit();
		  rabbitmq.createConnections();
		  rabbitmq.send = new Send();
		  rabbitmq.recive = new Recive();
		  rabbitmq.send.setSendChannel(rabbitmq.sendChannel);
		  rabbitmq.recive.setReciveChannel(rabbitmq.reciveChannel);
		  rabbitmq.recive.getJunkYard();
        }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String number = request.getParameter("number");
		//response.getWriter().append("Served at: "+number).append(request.getContextPath());
		//Call Async rabbitMq
		final AsyncContext asyncContext = request.startAsync(request, response);

		    new Thread() {

		      @Override
		      public void run() {
		        try {
		        	if(number != null && !number.isEmpty()){
				          ServletResponse response = asyncContext.getResponse();
				    	  int numberOfMessages = Integer.parseInt(number);
				          rabbitmq.send.sendMultipleMessage(numberOfMessages);
				          asyncContext.complete();
		        	}
		        } catch (Exception e) {
		          throw new RuntimeException(e);
		        }
		      }
		    }.start();

	          response.setContentType("text/plain");
	          PrintWriter out = response.getWriter();
	          out.printf("(Ping): Message #%s sended",number);
	          out.flush();
	          
		    asyncContext.addListener(new AsyncListener() {

		    	  @Override
		    	  public void onTimeout(AsyncEvent event) throws IOException {
		    	  }

		    	  @Override
		    	  public void onStartAsync(AsyncEvent event) throws IOException {
		    	  }

		    	  @Override
		    	  public void onError(AsyncEvent event) throws IOException {
		    	  }

		    	  @Override
		    	  public void onComplete(AsyncEvent event) throws IOException {
		    		  
		    	  }
		    	  
		    	}, request, response);
		//Response
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType( "text/html; charset=UTF8" );
        PrintWriter out = response.getWriter();
        for(int c = 0; c < rabbitmq.recive.response.size(); c++){
        	out.println("<p>"+rabbitmq.recive.response.get(c)+"</p>");
        }
        
        out.flush();
	}

}
