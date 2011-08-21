package org.cggh.casutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Get a protected resource, store locally and return a URL to local resource.
 * 
 * see https://wiki.jasig.org/display/CASUM/RESTful+API
 * 
 * @author timp
 * @since 2010-12-09 17.05
 * 
 */
public class CasProtectedResourceDownloader {

  private static final int ONE_MINUTE = 60000;

  final static String charSet = "UTF-8";

  private String username = "adam@example.org";
  private String password = "bar";
  private String ticketGrantingProtocol = "http://";
  private String ticketGrantingHostAndPort = "cloud1.cggh.org:80";
  private String ticketGrantingServiceUrl = ticketGrantingProtocol + ticketGrantingHostAndPort + "/sso/v1/tickets";
  private String tempFileDirectory = "/tmp/";
  private String tempUrlLocation = "file://" + tempFileDirectory;

  public CasProtectedResourceDownloader(String ticketGrantingProtocolIn, String ticketGrantingHostAndPortIn, 
                                        String usernameIn, String passwordIn, String tempFileDirectoryIn) {
    this.ticketGrantingProtocol = ticketGrantingProtocolIn;
    this.ticketGrantingHostAndPort = ticketGrantingHostAndPortIn;
    this.ticketGrantingServiceUrl = ticketGrantingProtocol+ ticketGrantingHostAndPort + "/sso/v1/tickets";
    this.username = usernameIn;
    this.password = passwordIn;
    this.tempFileDirectory = tempFileDirectoryIn;
    this.tempUrlLocation = "file://" + tempFileDirectory;
    
    if (!this.tempUrlLocation.endsWith("/"))
      this.tempUrlLocation = this.tempUrlLocation + "/";
    
    
  }


  @Override
  public String toString() {
    return super.toString() + " from:" + ticketGrantingServiceUrl + " to " + tempUrlLocation;
  }


  /**
   * @param uri
   *          protected resource
   */
  public String download(String uri) throws NotFoundException, IOException {
    String name = uri.substring(uri.lastIndexOf('/') + 1);
    name = name.replace('?', '_');
    downloadUrlToFile(uri, new File(tempFileDirectory, name));

    String returnUrl = tempUrlLocation + name;
    return returnUrl;
  }

  private String ticketedUri(String uri) throws IOException {
    String ticketedUri = uri;
    String ticket = getServiceTicket(ticketGrantingServiceUrl, username, password, uri);

    if (uri.indexOf('?') > -1)
      ticketedUri += "&";
    else
      ticketedUri += "?";

    ticketedUri += "ticket=" + ticket;
    return ticketedUri;
  }

  /**
   * Download the url content and save into the file.
   */
  public int downloadUrlToFile(String uri, File file) throws NotFoundException, IOException {
    String ticketedUri = ticketedUri(uri);
    System.err.println("Downloading uri " + uri + " to " + file);
    //System.err.println("Ticketted uri: " + ticketedUri);

    GetMethod get = new GetMethod(ticketedUri);
    get.setRequestHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");

    long start = System.currentTimeMillis();
    get.setFollowRedirects(true);
    MultiThreadedHttpConnectionManager hcm = new MultiThreadedHttpConnectionManager();
    hcm.getParams().setSoTimeout(ONE_MINUTE);
    HttpClient client = new HttpClient(hcm);
    int status = 0;
    try {
      client.executeMethod(get);
      status = get.getStatusCode();
      if (status == 200) {
        InputStream in = get.getResponseBodyAsStream();
        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = -1;
        while ((count = in.read(buffer)) != -1) {
          out.write(buffer, 0, count);
        }
        out.flush();
        out.close();
      } else if (status == 404) {
        throw new NotFoundException("Uri " + uri + " not found.");
      } else {
        String response = get.getResponseBodyAsString();
        throw new RuntimeException("Invalid response code (" + status + ") from server.\n" + 
            "Response (first 3k): " + response.substring(0, Math.min(3072, response.length())));
      }
    } finally {
      get.releaseConnection();
    }
    long end = System.currentTimeMillis();
    long millis = end - start;
    System.err.println(" in " + millis + " milliseconds (status " + status + ")");
    return status;
  }

  /**
   * Get the resource.
   * wget -O - -d ${TARGET}?ticket=$ST |grep "atom:feed"
   * @throws IOException 
   */

  private static String getServiceTicket(final String casServerUrl, final String username, final String password, final String service) throws IOException {
    return getServiceTicket(casServerUrl, getTicketGrantingTicket(casServerUrl, username, password), service);
  }

  private static String getServiceTicket(final String casServerUrl, final String ticketGrantingTicket, final String service) throws IOException {

    final HttpClient client = new HttpClient();

    final PostMethod post = new PostMethod(casServerUrl + "/" + ticketGrantingTicket);

    post.setRequestBody(new NameValuePair[] { new NameValuePair("service", service) });

    try {
      client.executeMethod(post);

      final String response = post.getResponseBodyAsString();

      if (post.getStatusCode() == 200)
        return response;
      else
        throw new RuntimeException("Unexpected invalid response code (" + post.getStatusCode() + ") from CAS server. \n" + 
            "Despite a ticket granting ticket having been issued by the server.\n" + 
            "Response (first 1k): " + response.substring(0, Math.min(1024, response.length())));
    } finally {
      post.releaseConnection();
    }

  }

  private static String getTicketGrantingTicket(final String ticketGrantingServiceUrl, final String username, final String password) {
    final HttpClient client = new HttpClient();

    final PostMethod post = new PostMethod(ticketGrantingServiceUrl);

    post.setRequestBody(new NameValuePair[] {
        new NameValuePair("username", username),
        new NameValuePair("password", password) });

    try {
      client.executeMethod(post);

      final String response = post.getResponseBodyAsString();

      if (post.getStatusCode() == 201) {
        final Matcher matcher = Pattern.compile(".*action=\".*/(.*?)\".*").matcher(response);

        if (matcher.matches())
          return matcher.group(1);
        else
          throw new RuntimeException("Unexpected missing action. Successful ticket granting request, but no ticket found.\n" + 
              "Response (first 1k): " + response.substring(0, Math.min(1024, response.length())));
      } else {
        throw new RuntimeException("Invalid response code (" + post.getStatusCode() + ") from CAS server " + ticketGrantingServiceUrl + "\n" + 
            "Response (first 1k): " + response.substring(0, Math.min(1024, response.length())));
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }

    finally {
      post.releaseConnection();
    }

  }
  
}
