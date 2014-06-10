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
  
  public App()throws Exception{
	  readFiles();
  }
  
  public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)throws IOException{
	  for(Tweets tweet:listTweets){
          StringWriter sw = new StringWriter();
          ObjectMapper mapper = new ObjectMapper();
          Writer writer = response.getWriter();
          writer.write("<html><body>");
          mapper.writeValue(sw, tweet);
          writer.write(sw.toString());
          writer.write("</body></html>");
          response.flushBuffer();
	    }
  }
  
  private static void readFiles()throws Exception{
      ObjectMapper mapper = new ObjectMapper();
      File dir = new File("/home/dc/TwitterServer/tweetfiles");
      File []listFiles = dir.listFiles();
      for(File f:listFiles){
        List<String> list = Files.readLines(f, Charset.defaultCharset());
       
        for(String s:list){
        	listTweets.add(mapper.readValue(s, Tweets.class));
        }
        System.out.println("num tweets:"+listTweets.size());
      }
    
  }

  class TweetQueue {
	  
  }
  
  public static void main( String[] args ) throws Exception
  {
	App a = new App();
    System.out.println( "Starting Server port localhost:8081" );
    Server server = new Server(8081);
    server.setHandler(a);
    server.start();
    server.join();
  }
}
