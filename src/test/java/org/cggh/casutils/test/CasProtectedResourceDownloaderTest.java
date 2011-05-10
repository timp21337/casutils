/**
 * 
 */
package org.cggh.casutils.test;

import java.io.File;

import org.cggh.casutils.CasProtectedResourceDownloader;

import junit.framework.TestCase;

/**
 * @author timp
 *
 */
public class CasProtectedResourceDownloaderTest extends TestCase {

  /**
   * Test method for {@link org.cggh.casutils.CasProtectedResourceDownloader#download(java.lang.String)}.
   */
  public void testDownload() throws Exception {
    assertEquals("file:///tmp/ZAZBM", CasProtectedResourceDownloader.download("http://cloud1.cggh.org/repository/service/content/studies/ZAZBM"));
  }

  /**
   * Test method for {@link org.cggh.casutils.CasProtectedResourceDownloader#downloadUrlToFile(java.lang.String, java.io.File)}.
   */
  public void testDownloadUrlToFile() throws Exception {
    assertEquals(200, CasProtectedResourceDownloader.downloadUrlToFile("http://cloud1.cggh.org/repository/service/content/studies/ZAZBM", new File("t.tmp")));
  }

}
