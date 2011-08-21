 mvn clean install war:war

 should build a jar and a war
 
 
 Note that Jenkins will use its own JDK unless you tell it otherwise. 
 
 Cloud1 has a self signed certificate: beware.
 
 Installing certifiacte
 ======================
 
 Note that this fails, without error:
 
 keytool -import -file src/main/resources/ssl-cert-snakeoil.pem -alias cloud1

 You need to specify cacerts explicitly (default password = changeit):
 
 $cd /cygdrive/c/Program Files/Java/jdk1.6.0_24/jre/lib/security
 $ keytool -import -file /Users/timp/workspace/casutils/src/main/resources/ssl-cert-snakeoil.pem \ 
   -alias cloud1 -keystore  ./cacerts
Enter keystore password:
Owner: CN=cloud1.cggh.org
Issuer: CN=cloud1.cggh.org
Serial number: f01c6ab885f5c5f5
Valid from: Wed May 18 14:24:53 BST 2011 until: Sat May 15 14:24:53 BST 2021
Certificate fingerprints:
         MD5:  3F:F4:21:86:3E:4B:37:6D:68:18:C2:16:91:EE:C4:58
         SHA1: 86:36:80:18:CA:F7:9D:6B:21:08:AF:E4:58:F4:B0:63:C5:DF:BA:D4
         Signature algorithm name: SHA1withRSA
         Version: 1
Trust this certificate? [no]:  yes
Certificate was added to keystore

Much the same on Ubuntu: 
cd /etc/java-6-sun/security
sudo keytool -import -file ~/workspace/casutils/src/main/resources/ssl-cert-snakeoil.pem -alias cloud1 -keystore  ./cacerts

And on OSX

cd /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/lib/security/

keytool -import -file /Users/timp/workspace/casutils/src/main/resources/ssl-cert-snakeoil.pem -alias cloud1 -keystore  ./cacerts

