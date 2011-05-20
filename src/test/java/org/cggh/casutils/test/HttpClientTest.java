package org.cggh.casutils.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocketFactory;

import junit.framework.TestCase;
/**
 * A set of tests to prove that SSL is working correctly 
 * 
 * @see http://hc.apache.org/httpclient-legacy/sslguide.html
 *
 */
public class HttpClientTest extends TestCase {
      
  public static final int    TARGET_HTTPS_PORT   = 443; 
  public static final String GET = "GET";
  public static final String POST = "POST";
  
  
  public HttpClientTest(String name) {
    super(name);
  }
  

  public  static void testGetVerisign() throws IOException, UnknownHostException, UnsupportedEncodingException {
    String httpsServer = "www.verisign.com";
    String output = requestService(GET,httpsServer );
    
    assertEquals("Have you setup trust certificates for " + httpsServer + "?\n" + 
        output, "HTTP/1.1 200 OK", output.substring(0,15));
    
    output = requestService(POST,httpsServer );
    
    assertEquals("Have you setup trust certificates for " + httpsServer + "?\n" + 
        output, "HTTP/1.1 200 OK", output.substring(0,15));
  }

  public  static void testCloud1() throws IOException, UnknownHostException, UnsupportedEncodingException {    
    String httpsServer = "cloud1.cggh.org";
    String output = requestService(GET,httpsServer );
    
    assertEquals("Have you setup trust certificates for " + httpsServer + "?\n" + 
        output, "HTTP/1.1 302 Fo", output.substring(0,15));
    
    output = requestService(POST,httpsServer );
    
    assertEquals("Have you setup trust certificates for " + httpsServer + "?\n" + 
        output, "HTTP/1.1 302 Fo", output.substring(0,15));
  }

  public  static void testWwarn() throws IOException, UnknownHostException, UnsupportedEncodingException {
    String httpsServer = "www.wwarn.org";
    String output = requestService(GET,httpsServer );
    
    assertEquals("Have you setup trust certificates for " + httpsServer + "?\n" + 
        output, "HTTP/1.1 302 Fo", output.substring(0,15));
    
    output = requestService(POST,httpsServer );
    
    assertEquals("Have you setup trust certificates for " + httpsServer + "?\n" + 
        output, "HTTP/1.1 302 Fo", output.substring(0,15));
  }

  private static String  requestService(String method, String httpsServer) throws IOException, UnknownHostException, UnsupportedEncodingException {
    Socket socket = SSLSocketFactory.getDefault().
       createSocket(httpsServer, TARGET_HTTPS_PORT);
    StringBuffer output;
     try {
       Writer out = new OutputStreamWriter(
          socket.getOutputStream(), "ISO-8859-1");
       out.write(method + " / HTTP/1.1\r\n");  
       out.write("Host: " + httpsServer + ":" + 
           TARGET_HTTPS_PORT + "\r\n");  
       out.write("Agent: SSL-TEST\r\n");  
       out.write("\r\n");  
       out.flush();  
       BufferedReader in = new BufferedReader(
          new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
       String line = null;
       output = new StringBuffer();
       while ((line = in.readLine()) != null) {
         output.append(line);
         output.append("\n");
       }
     } finally {
       socket.close(); 
     }
     return output.toString();
  }
  
}
