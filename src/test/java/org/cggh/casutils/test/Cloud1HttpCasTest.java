package org.cggh.casutils.test;

public class Cloud1HttpCasTest extends CasProtectedResourceDownloaderSpec {

  public Cloud1HttpCasTest(String name) {
    super(name);
  }

  @Override
  String getProtocol() {
    return "http://";
  }

  @Override
  String getHost() {
    return "cloud1.cggh.org";
  }

  @Override
  String getCasPort() {
    return "80";
  }

  @Override
  String getTestZipFileUrl() { 
    return getContentUrl() + "media/curated/ZAZBM/d5064bae-870b-4ebc-8013-b282af180983.media"; 
  }

  @Override
  String getUser() {
    return "adam@example.org";
  }

  @Override
  String getPassword() {
    return "bar";
  }

  @Override
  String getStudyId() {
    return "ZAZBM";
  }
  
  
}
