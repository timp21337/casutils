package org.cggh.casutils.test;

public class WwarnHttpsCasTest extends CasProtectedResourceDownloaderSpec {

  public WwarnHttpsCasTest(String name) {
    super(name);
  }

  @Override
  String getCasProxyProtocol() {
    return "https://";
  }

  @Override
  String getCasProxyHost() {
    return "www.wwarn.org";
  }

  @Override
  String getCasProxyPort() {
    return "443";
  }

  @Override
  String getServiceHostUrl() {
    return "https://www.wwarn.org";
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
