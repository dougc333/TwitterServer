package com.example;


import org.codehaus.jackson.map.ObjectMapper;

import java.util.*;
import java.io.*;
import java.nio.charset.Charset;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

//import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
 


public class App  extends AbstractHandler
{	
  private static List<Tweets> listTweets = new LinkedList<Tweets>();
  
  public App(){
	  readFiles();
  }
  /**
  protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException{
	  //put into queue to control delivery
	  while(true){
  	    for(Tweets tweet:listTweets){
          StringWriter sw = new StringWriter();
          ObjectMapper mapper = new ObjectMapper();
          Writer writer = response.getWriter();
          mapper.writeValue(sw, tweet);
          writer.write(sw.toString());
          writer.write("\n");
          response.flushBuffer();
	    }
	  }
  }
  */
  
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)throws IOException{
	  for(Tweets tweet:listTweets){
          StringWriter sw = new StringWriter();
          ObjectMapper mapper = new ObjectMapper();
          Writer writer = response.getWriter();
          writer.write("<html><body>");
          mapper.writeValue(sw, tweet);
          writer.write(sw.toString());
          writer.write("\n");
          writer.write("</body></html>");
          response.flushBuffer();
	    }
  }
  
  private static void readFiles(){
    try{
      ObjectMapper mapper = new ObjectMapper();
      File dir = new File("/home/dc/event-server-master/data/mnf");
      File []listFiles = dir.listFiles();
      for(File f:listFiles){
        List<String> list = Files.readLines(f, Charset.defaultCharset());
       
        for(String s:list){
        	//System.out.println("s:"+s);
//        	Tweets tw = mapper.readValue(s, Tweets.class);
//        	System.out.println("tw:"+tw.toString());
        	listTweets.add(mapper.readValue(s, Tweets.class));
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  class TweetQueue {
	  
  }
  
  public static void main( String[] args ) throws Exception
  {
	App a = new App();
    System.out.println( "Hello World!" );
    Server server = new Server(8081);
    server.setHandler(a);
    server.start();
    server.join();
  }
}