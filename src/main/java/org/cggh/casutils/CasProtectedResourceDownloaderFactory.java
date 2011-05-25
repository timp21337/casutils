package org.cggh.casutils;

import java.util.HashMap;

/**
 * A cache of known downloaders.
 * @author timp
 * @since 2011-05-25
 */
public final class CasProtectedResourceDownloaderFactory {
 
  
  public static HashMap<String, CasProtectedResourceDownloader> downloaders  = new HashMap<String, CasProtectedResourceDownloader>();
  
  static {
    downloaders.put(keyFromUrl("https://cloud1.cggh.org/"), 
        new CasProtectedResourceDownloader("https://", "cloud1.cggh.org:443", 
            "adam@example.org", "bar", "/tmp"));
  }

  public static CasProtectedResourceDownloader getCasProtectedResourceDownloader(String url) {
    return downloaders.get(keyFromUrl(url));
  }
  
  public static String keyFromUrl(String url) {
    String[] bits = url.toLowerCase().split("/");
    return bits[0] + bits[1] + bits[2];
  }
  
  

}
