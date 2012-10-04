package org.cggh.casutils.test;

public class LocalhostHttpsCasTest extends CasProtectedResourceDownloaderSpec {

  public LocalhostHttpsCasTest(String name) {
    super(name);
  }

  String getCasProxyProtocol() {
    return "https://";
  }

  @Override
  String getCasProxyHost() {
   // return "46.137.162.221";
    // return "test-chassis.wwarn.ox.ac.uk";
   // return "test-chassis.wwarn.org";
   // return "demo.wwarn.org";
    return "localhost";
  }

  @Override
  String getServiceHostUrl() {
    return "https://localhost";
  }
  
  @Override
  String getContentUrl() { 
    return "https://localhost/";
  }
  
  @Override
  String getTestStudyUrl() { 
    return "https://localhost/sso/login";
  }
  @Override
  String getCasProxyPort() {
    return "";
  }

  @Override
  String getTestZipFileUrl() { 
    return "http://localhost:8080/httpstatus/http?status=200"; 
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
