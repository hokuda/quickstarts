import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLContext;
import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;

import java.net.ServerSocket;
import java.net.Socket;

import java.security.KeyStore;
import java.io.*;

public class ServerProtocolTest {
    
    public static void main(String[] args) throws Exception {
        
	String keystore = "./localhost-sha256rsa.jks";
        System.setProperty("javax.net.ssl.keyStore", keystore);
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        KeyStore tKeyStore = KeyStore.getInstance("JKS");
        char[] tKeyStorePassword = "password".toCharArray();
        tKeyStore.load(new FileInputStream(keystore), tKeyStorePassword);
        KeyManagerFactory kmfactory = KeyManagerFactory.getInstance("SunX509");
        kmfactory.init(tKeyStore, tKeyStorePassword);
        //SSLContext sslcontext = SSLContext.getInstance("TLSv1.2", "SunJSSE");
        //SSLContext sslcontext = SSLContext.getInstance("TLSv1.2", "SunJSSE");
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(kmfactory.getKeyManagers(), null, null);
        //SSLContext sslcontext = SSLContext.getDefault();
        //sslcontext.init(tKeyManagerFactory.getKeyManagers(), null, null);
        SSLServerSocketFactory tServerSocketFactory = sslcontext.getServerSocketFactory();
        ServerSocket serversocket = tServerSocketFactory.createServerSocket(8443, 10, java.net.InetAddress.getByName("127.0.0.1"));
        //SSLServerSocket tServerSocket = tServerSocketFactory.createServerSocket(8443, 10, java.net.InetAddress.getByName("127.0.0.1"));
        //Socket tSocket;
        //while(true){
        //tSocket = tServerSocket.accept();
            //}
        String[] protocols = ((SSLServerSocket)serversocket).getSupportedProtocols();
        
        System.out.println("Supported Protocols: " + protocols.length);
        for(int i = 0; i < protocols.length; i++)
            {
                System.out.println("  " + protocols[i]);
            }
        
        protocols = ((SSLServerSocket)serversocket).getEnabledProtocols();
        
        System.out.println("Enabled Protocols: " + protocols.length);
        for(int i = 0; i < protocols.length; i++)
            {
                System.out.println("  " + protocols[i]);
            }

        /////////////////////////////////////////////////////////////
        if(true){
            Socket client = serversocket.accept();
            System.out.println( "Clientから接続されました。" );
            
            BufferedReader in  = new BufferedReader( 
                                                    new InputStreamReader ( client.getInputStream() ) );
            BufferedWriter out = new BufferedWriter( 
                                                    new OutputStreamWriter( client.getOutputStream() ) );
            
            String msg = in.readLine();
            
            System.out.println("★★★Clientからのメッセージ:" + msg );
            out.write( "Hello Client\n" );  // クライアントに文字列送信
            out.flush();
        }
    }
}


