/**
 * 
 */
package org.cggh.casutils.test;

import org.cggh.casutils.CasProtectedResourceDownloader;
import org.cggh.casutils.CasProtectedResourceDownloaderFactory;

import junit.framework.TestCase;

/**
 * @author timp
 * @since 2011-05-25
 */
public class CasProtectedResourceDownloaderFactoryTest extends TestCase {

  String urlWithoutProtocol = "cloud1.cggh.org/repository/service/content/media/curated/ZAZBM/d5064bae-870b-4ebc-8013-b282af180983.media";
  String u;
  public void testGetCasProtectedResourceDownloader() {
    assertTrue(CasProtectedResourceDownloaderFactory.getCasProtectedResourceDownloader("https://" + urlWithoutProtocol) instanceof CasProtectedResourceDownloader);
    
    assertNull(CasProtectedResourceDownloaderFactory.getCasProtectedResourceDownloader("http://" + urlWithoutProtocol));
    assertNull(CasProtectedResourceDownloaderFactory.getCasProtectedResourceDownloader("http://google.com"));
  }

  public void testKeyFromUrl() {
    assertEquals(
        CasProtectedResourceDownloaderFactory.keyFromUrl("https://cloud1.cggh.org/"), 
        CasProtectedResourceDownloaderFactory.keyFromUrl("https://" + urlWithoutProtocol));
  }

}
