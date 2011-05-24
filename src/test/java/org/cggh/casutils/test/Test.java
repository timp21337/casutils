package org.cggh.casutils.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

/**
 * Expect this to show login page, if  Exception: 
 * java.lang.RuntimeException: 
 * javax.net.ssl.SSLHandshakeException: 
 * sun.security.validator.ValidatorException: 
 * PKIX path building failed: 
 * sun.security.provider.certpath.SunCertPathBuilderException: 
 * unable to find valid certification path to requested target
 *  at org.cggh.casutils.CasProtectedResourceDownloader.getTicketGrantingTicket(CasProtectedResourceDownloader.java:181)
 *  
 *  then you have not installed ssl certificate into the relevant cacerts file.
 * 
 * @author RWH
 */
public class Test {

  public static final String TARGET_HTTPS_SERVER = "cloud1.cggh.org";
  public static final int TARGET_HTTPS_PORT = 443;

  public static void main(String[] args) throws Exception {

    Socket socket = SSLSocketFactory.getDefault().
         createSocket(TARGET_HTTPS_SERVER, TARGET_HTTPS_PORT);
    try {
      Writer out = new OutputStreamWriter(
            socket.getOutputStream(), "ISO-8859-1");
      out.write("GET /sso/login HTTP/1.1\r\n");
      out.write("Host: " + TARGET_HTTPS_SERVER + ":" +
             TARGET_HTTPS_PORT + "\r\n");
      out.write("Agent: SSL-TEST\r\n");
      out.write("\r\n");
      out.flush();
      BufferedReader in = new BufferedReader(
            new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
      String line = null;
      while ((line = in.readLine()) != null) {
        System.out.println(line);
      }
    } finally {
      socket.close();
    }
  }
}
