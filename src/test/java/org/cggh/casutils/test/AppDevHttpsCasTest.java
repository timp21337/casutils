package org.cggh.casutils.test;

public class AppDevHttpsCasTest extends CasProtectedResourceDownloaderSpec {

  public AppDevHttpsCasTest(String name) {
    super(name);
  }

  @Override
  String getCasProxyProtocol() {
    return "https://";
  }

  @Override
  String getCasProxyHost() {
   // return "46.137.162.221";
    // return "test-chassis.wwarn.ox.ac.uk";
   // return "test-chassis.wwarn.org";
   // return "demo.wwarn.org";
    return "app-dev.wwarn.org";
  }

  @Override
  String getServiceHostUrl() {
    //return "http://test-chassis.wwarn.ox.ac.uk:8080";
    return "http://app-dev.wwarn.org";
  }
  
  
  @Override
  String getCasProxyPort() {
    return "";
  }

  @Override
  String getTestZipFileUrl() { 
    return getContentUrl() + "media/curated/MTSUX/292bfe0f-3f53-4457-a7cb-02f4c51b25db.media";
    // "media/curated/ZAZBM/d5064bae-870b-4ebc-8013-b282af180983.media"; 
  }
  @Override
  String getUser() {
    return "admin";
  }

  @Override
  String getPassword() {
    return "bar";
  }

  @Override
  String getStudyId() {
    //return "QNSTN";
    return "ZTKKS";//"FMDMQ";//"ZAZBM";
  }

}
