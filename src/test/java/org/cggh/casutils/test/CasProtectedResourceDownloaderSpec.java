package org.cggh.casutils.test;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.cggh.casutils.CasProtectedResourceDownloader;
import org.cggh.casutils.NotFoundException;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 10 December 2010 10:21:17
 *
 */
abstract public class CasProtectedResourceDownloaderSpec extends TestCase {

  abstract String getProtocol(); 
  abstract String getHost();
  
  // Default https port is 443
  abstract String getCasPort();
  
  static final String CONTEXTPATH = "/repository";
  static final String SERVICEPATH = "/service/";
  
  String getHostAndTicketGrantingPort() { 
    return getHost() + (getCasPort() == "" ? "" : ":" + getCasPort());
  }
  
  // Note we are not specifying port
  String getServiceUrl() { 
	  return getProtocol() + getHost() + CONTEXTPATH + SERVICEPATH;
  }
  String getContentUrl() { 
	  return getServiceUrl() + "content/";
  }
  String getTestCollectionUrl() { 
    return getContentUrl() + "studies";
  }
  
  // This needs to be manually created and have the zip file from test/resources uploaded
  // Go to http://cloud1.cggh.org/repository/contributor/
  // login as cora@example.org, password bar
  
  abstract String getStudyId();
 
  String getTestStudyUrl() { 
    return getTestCollectionUrl() + "/" + getStudyId() ;
  }
  
  abstract String getTestZipFileUrl(); 


  abstract String getUser();
  abstract String getPassword();

  static final HttpClient client = new HttpClient();

  public CasProtectedResourceDownloaderSpec(String name) {
    super(name);
  }

  public void testDownloadZip() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(getProtocol(),getHostAndTicketGrantingPort(),getUser(), getPassword(), "/tmp/");
    String downloadedUrl;
    try { 
      downloadedUrl = it.download(getTestZipFileUrl()); 
    } catch (RuntimeException e) { 
      throw new RuntimeException("Have you installed the cacerts? See README.txt", e);
    }
    assertEquals(getTestZipFileUrl().substring(getTestZipFileUrl().lastIndexOf('/')),
        downloadedUrl.substring(downloadedUrl.lastIndexOf('/')));
    CasProtectedResourceDownloader bad = new CasProtectedResourceDownloader(getProtocol(),getHostAndTicketGrantingPort(),getUser(), "bad", "/tmp/");
    try { 
      bad.download(getTestZipFileUrl());
      fail("Should have bombed");
    } catch (RuntimeException e) { 
      e = null;
    }
    
  }

  public void testGetStudy() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(getProtocol(), getHostAndTicketGrantingPort(),getUser(), getPassword(), "/tmp/");
    assertEquals("file:///tmp/" + getStudyId() , it.download(getTestStudyUrl()));

    CasProtectedResourceDownloader badPasswordSupplied = new CasProtectedResourceDownloader(getProtocol(),getHostAndTicketGrantingPort(),"adam@example.org", "bair", "/tmp/"); 
    try { 
      badPasswordSupplied.download(getTestStudyUrl());
      fail("Should have bombed");
    } catch (RuntimeException e) { 
      e = null;
    }
  }
  
  
  public void testGetUrlWithParameters() throws Exception { 
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(getProtocol(),getHostAndTicketGrantingPort(),getUser(), getPassword(), "/tmp/");
    assertEquals("file:///tmp/" + getStudyId() + "_foo=bar", it.download(getTestStudyUrl() + "?foo=bar"));
  }

  /**
   * Test method for {@link org.cggh.casutils.CasProtectedResourceDownloader#download(java.lang.String)}.
   */
  public void testDownload() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(getProtocol(),getHostAndTicketGrantingPort(),getUser(), getPassword(), "/tmp/");
    assertEquals("file:///tmp/" + getStudyId(), it.download(getTestStudyUrl()));
  }

  /**
   * Test method for {@link org.cggh.casutils.CasProtectedResourceDownloader#downloadUrlToFile(java.lang.String, java.io.File)}.
   */
  public void testDownloadUrlToFile() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(getProtocol(),getHostAndTicketGrantingPort(),getUser(), getPassword(), "/tmp/");
    assertEquals(200, it.downloadUrlToFile(getTestStudyUrl(), new File("t.tmp")));
  }
  public void testDownloadBadUrlToFile() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(getProtocol(),getHostAndTicketGrantingPort(),getUser(), getPassword(), "/tmp/");
    try { 
      it.downloadUrlToFile(getProtocol() + getHostAndTicketGrantingPort() + "/repository/service/content/studies/not_there", new File("t.tmp"));
      fail("Should have bombed");
    } catch (NotFoundException e) {
      e = null;
    }
  }

  public void testBadCasProtocolTrapped() throws Exception { 
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader("http://",getHostAndTicketGrantingPort() + ":443",getUser(), getPassword(), "/tmp/");
    System.err.println(it);
    try { 
      it.downloadUrlToFile(getTestStudyUrl(), new File("t.tmp"));
      fail("Should have bombed");
    } catch (RuntimeException e) { 
      e = null;
    }
  }
  
  
}
