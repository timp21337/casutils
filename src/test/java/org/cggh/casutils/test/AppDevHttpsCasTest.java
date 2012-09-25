package org.cggh.casutils.test;

public class AppDevHttpsCasTest extends CasProtectedResourceDownloaderSpec {

  public AppDevHttpsCasTest(String name) {
    super(name);
  }

  @Override
  String getProtocol() {
    return "https://";
  }

  @Override
  String getHost() {
    return "app-dev.wwarn.org";
  }

  @Override
  String getCasPort() {
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
    return "FMDMQ";//"ZAZBM";
  }
}
