import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;
import java.security.cert.*;
import java.security.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;

public class SimpleHttpsClient{
    public static void main(String[]args){
        SimpleHttpsClient client = new SimpleHttpsClient();
        /*
        String host = args[0];
        int port = new Integer(args[1]).intValue();
        client.connect(host, port);
        */
        String url = args[0];
        client.doit(url);
    }

    public void connect(String host, int port) {

        char[] key_pass = "password".toCharArray();
        String storename = "/home/hokuda/openssl/test3-rootca/new.p12";

        try{
            //SSLContext sc = SSLContext.getInstance("TLS");

            // set up client cert store
            KeyStore key_store = KeyStore.getInstance("PKCS12");
            key_store.load(new FileInputStream(storename), key_pass);
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance("SunX509");
            kmfactory.init(key_store, key_pass);

            SSLContext sslcontext= SSLContext.getInstance("TLS");
            sslcontext.init(kmfactory.getKeyManagers(),
                            new TrustManager[] {
                                new X509TrustManager() {
                                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                                                   String authType) {
                                    }
                                    
                                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                                                   String authType) {
                                    }
                                    public X509Certificate[] getAcceptedIssuers() {
                                        return new X509Certificate[0];
                                    }
                                }
                            },
                            new SecureRandom()
                            );
            
            /*
            sc.init(null, new TrustManager[] {
                    new X509TrustManager() {
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                                       String authType) {
                        }
                        
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                                       String authType) {
                        }
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
                },
                new java.security.SecureRandom());
            sc.init(null, null, new java.security.SecureRandom());
            SocketFactory factory = sc.getSocketFactory();
            */
            SocketFactory factory = sslcontext.getSocketFactory();
            SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
            //String[] suites = {"TLS_RSA_WITH_AES_256_CBC_SHA256"};
            //socket.setEnabledCipherSuites(suites);
            socket.startHandshake();
        }
        catch(Exception e){
            e.printStackTrace(System.err);
            System.exit(1);
        }
        System.exit(0);
    }
    
    public void doit(String urlString) {
        String content="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            +"<A><B>2</B></A>"; // 仮のPOST-body(XML)
        try{
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
                    public boolean verify(String string,SSLSession ssls) {
                        return true;
                    }
                });
            // アドレス設定、ヘッダー情報設定
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");         // POSTまたはGET
            con.setDoOutput(true);              // POSTのデータを後ろに付ける
            con.setInstanceFollowRedirects(false);// 勝手にリダイレクトさせない
            con.setRequestProperty("Accept-Language", "jp"); 
            con.setRequestProperty("Content-Type","text/xml;charset=utf-8");
            con.connect();
            // 送信
            PrintWriter pw = new PrintWriter(new BufferedWriter(
                                                                new OutputStreamWriter(
                                                                                       con.getOutputStream()
                                                                                       ,"utf-8")));
            pw.print(content);// content
            pw.close();       // closeで送信完了
            // body部の文字コード取得
            String contentType = con.getHeaderField("Content-Type");
            String charSet     = "Shift-JIS";//"ISO-8859-1";
            for(String elm : contentType.replace(" ", "").split(";")) {
                if (elm.startsWith("charset=")) {
                    charSet = elm.substring(8);
                    break;
                }
            }  
            // body部受信
            BufferedReader br;
            try{
                br = new BufferedReader(new InputStreamReader(
                                                              con.getInputStream(),charSet));
            }
            catch(Exception e_){
                System.out.println( con.getResponseCode()
                                    +" "+ con.getResponseMessage() );
                br = new BufferedReader(new InputStreamReader(
                                                              con.getErrorStream(),charSet));
            }
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line+"\n");
            }
            br.close();
            con.disconnect(); 
        }
        catch(Exception e){
            e.printStackTrace(System.err);
            System.exit(1);
        }
        System.exit(0);
    }

    public class TrustAllX509TrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
        
        public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                       String authType) {
        }
        
        public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                       String authType) {
        }
        
    }
}
