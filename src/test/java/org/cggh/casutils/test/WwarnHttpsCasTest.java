package org.cggh.casutils.test;

public class WwarnHttpsCasTest extends CasProtectedResourceDownloaderSpec {

  public WwarnHttpsCasTest(String name) {
    super(name);
  }

  @Override
  String getProtocol() {
    return "https://";
  }

  @Override
  String getHost() {
    return "www.wwarn.org";
  }

  @Override
  String getCasPort() {
    return "443";
  }

  @Override
  String getTestZipFileUrl() { 
    return getContentUrl() + "media/curated/NJXYG/25e29608-2cf1-4df3-83fa-0a8b481d33d5.media"; 
  }
  
  @Override
  String getUser() {
    //FIXME 
    return "";
  }

  @Override
  String getPassword() {
    // FIXME
    return "";
  }
  
  @Override
  String getStudyId() {
    return "NJXYG";
  }
  
}
