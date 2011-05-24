package org.cggh.casutils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;

/**
 * A servlet which downloads to location set in init parameters.
 * 
 * Copyright Oxford University.
 * 
 * @author timp
 * @since 29/03/11 13:49
 */
public class CasProtectedResourceDownloadServlet extends HttpServlet {
  /* Eclipse generated */
  private static final long serialVersionUID = -8045447899425192481L;
  
  private CasProtectedResourceDownloader downloader = null;

  @Override
  public void init() throws ServletException {
    super.init();
    downloader = new CasProtectedResourceDownloader(
        getInitParameter("ticketGrantingProtocol"),
        getInitParameter("ticketGrantingHostAndPort"),
        getInitParameter("username"),
        getInitParameter("password"), 
        getInitParameter("tempDownloadDirectory")); 
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try { 
      String url = request.getParameter("url");
      if (url == null)
        throw new RuntimeException("Missing url parameter");
      downloader.download(url);
      response.getWriter().print("OK");
    } catch (Exception e) { 
      error(e, response);
    }
  }

  private void error(Exception e, HttpServletResponse response) throws IOException {
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println(" <head>");
    out.println("  <title>");
    out.println(e.getMessage());
    out.println("  </title>");
    out.println(" </head>");
    out.println(" <body>");
    out.println("  <h1>");
    out.println(e.getMessage());
    out.println("  </h1>");
    out.println("  <form>");
    out.println("   <input type='text' name='url'/>");
    out.println("   <input type='submit'/>");
    out.println("  </form>");
    out.println("<pre>");
    e.printStackTrace(out);
    out.println("</pre>");
    out.println(" </body>");
    out.println("</html>");
  }

}
