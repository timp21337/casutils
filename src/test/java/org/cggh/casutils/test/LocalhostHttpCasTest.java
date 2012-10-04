package org.cggh.casutils.test;

public class LocalhostHttpCasTest extends CasProtectedResourceDownloaderSpec {

  public LocalhostHttpCasTest(String name) {
    super(name);
  }

  String getCasProxyProtocol() {
    return "http://";
  }

  @Override
  String getCasProxyHost() {
    return "localhost";
  }

  @Override
  String getServiceHostUrl() {
    return "http://localhost:8080";
  }
  
  @Override
  String getContentUrl() { 
    return "http://localhost:8080/";
  }
  
  @Override
  String getTestStudyUrl() { 
    return "http://localhost:8080/sso/login";
  }
  @Override
  String getCasProxyPort() {
    return "8080";
  }

  @Override
  String getTestZipFileUrl() { 
    return "http://localhost:8080/manager/images/tomcat.gif"; 
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
    return "login";//"FMDMQ";//"ZAZBM";
  }

}
