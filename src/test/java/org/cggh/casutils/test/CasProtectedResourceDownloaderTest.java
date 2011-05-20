/**

 * 
 */
package org.cggh.casutils.test;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.cggh.casutils.CasProtectedResourceDownloader;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 10 December 2010 10:21:17
 *
 */
public class CasProtectedResourceDownloaderTest extends TestCase {

  static final String PROTOCOL = "http://";
  static final String HOST = "cloud1.cggh.org";
  //static final String HOST = "www.wwarn.org";
  static final String PORT = "80";
  static final String HOST_N_PORT = HOST + ":" + PORT;
  static final String CONTEXTPATH = "/repository";
  static final String SERVICEPATH = "/service/";
  
  // FIXME Service url elides port 
  
  static final String SERVICE_URL = PROTOCOL + HOST  + CONTEXTPATH + SERVICEPATH;
  static final String CONTENT_URL = SERVICE_URL + "content/";
  static final String TEST_COLLECTION_URL = CONTENT_URL + "studies";
  
  // This needs to be manually created and have the zip file from test/resources uploaded
  // Go to http://cloud1.cggh.org/repository/contributor/
  // login as cora@example.org, password bar
  
  static final String TEST_STUDY_URL = TEST_COLLECTION_URL + "/ZAZBM";
  // test study on wwarn.org
  //static final String TEST_STUDY_URL = TEST_COLLECTION_URL + "/NJXYG";  
  
  static final String TEST_ZIP_FILE_URL = CONTENT_URL + "media/curated/ZAZBM/d5064bae-870b-4ebc-8013-b282af180983.media"; 

  static final String USER = "adam@example.org";
  static final String PASSWORD = "bar";

  static final HttpClient client = new HttpClient();

  public CasProtectedResourceDownloaderTest(String name) {
    super(name);
  }

  public void testDownloadZip() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(PROTOCOL,HOST_N_PORT,USER, PASSWORD, "/tmp/");
    assertEquals("file:///tmp/d5064bae-870b-4ebc-8013-b282af180983.media",
        it.download(TEST_ZIP_FILE_URL));
    CasProtectedResourceDownloader bad = new CasProtectedResourceDownloader(PROTOCOL,HOST_N_PORT,USER, "bad", "/tmp/");
    try { 
      bad.download(TEST_ZIP_FILE_URL);
    } catch (RuntimeException e) { 
      e = null;
    }
    
  }

  public void testGetStudy() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(PROTOCOL, HOST_N_PORT,USER, PASSWORD, "/tmp/");
    assertEquals("file:///tmp/ZAZBM", it.download(TEST_STUDY_URL));

    CasProtectedResourceDownloader bad = new CasProtectedResourceDownloader(PROTOCOL,HOST_N_PORT,"adam@example.org", "bair", "/tmp/"); 
    try { 
      bad.download(TEST_STUDY_URL);
      fail("Should have bombed");
    } catch (RuntimeException e) { 
      e = null;
    }
  }
  
  
  public void testGetUrlWithParameters() throws Exception { 
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(PROTOCOL,HOST_N_PORT,USER, PASSWORD, "/tmp/");
    assertEquals("file:///tmp/ZAZBM_foo=bar", it.download(TEST_STUDY_URL + "?foo=bar"));
  }

  /**
   * Test method for {@link org.cggh.casutils.CasProtectedResourceDownloader#download(java.lang.String)}.
   */
  public void testDownload() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(PROTOCOL,HOST_N_PORT,USER, PASSWORD, "/tmp/");
    assertEquals("file:///tmp/ZAZBM", it.download("http://cloud1.cggh.org/repository/service/content/studies/ZAZBM"));
  }

  /**
   * Test method for {@link org.cggh.casutils.CasProtectedResourceDownloader#downloadUrlToFile(java.lang.String, java.io.File)}.
   */
  public void testDownloadUrlToFile() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(PROTOCOL,HOST_N_PORT,USER, PASSWORD, "/tmp/");
    assertEquals(200, it.downloadUrlToFile(TEST_STUDY_URL, new File("t.tmp")));
  }
  public void testDownloadBadUrlToFile() throws Exception {
    CasProtectedResourceDownloader it = new CasProtectedResourceDownloader(PROTOCOL,HOST_N_PORT,USER, PASSWORD, "/tmp/");
    try { 
      it.downloadUrlToFile(PROTOCOL + HOST_N_PORT + "/repository/service/content/studies/not_there", new File("t.tmp"));
      fail("Should have bombed");
    } catch (RuntimeException e) { 
      e = null;
    }
  }

  public void testVerisign() throws Exception { 
    HttpClient httpclient = new HttpClient();
    GetMethod httpget = new GetMethod("https://www.verisign.com/"); 
    try { 
      httpclient.executeMethod(httpget);
      assertEquals("HTTP/1.1 200 OK", httpget.getStatusLine().toString());
    } finally {
      httpget.releaseConnection();
    }
  }

  
  
}
