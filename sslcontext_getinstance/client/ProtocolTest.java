import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLContext;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class ProtocolTest {
    
    public static void main(String[] args) throws Exception {
        
        //SSLContext context = SSLContext.getInstance("TLSv1.2");
        //SSLContext context = SSLContext.getInstance("TLSv1.1");
        SSLContext context = SSLContext.getInstance("TLSv1");
        //SSLContext context = SSLContext.getInstance("TLS");
        context.init(null,null,null);
        //SSLContext context = SSLContext.getDefault();
        
        SSLSocketFactory factory = (SSLSocketFactory)context.getSocketFactory();
        //SSLSocket socket = (SSLSocket)factory.createSocket();
        SSLSocket socket = (SSLSocket)factory.createSocket("localhost", 8443);
        socket.startHandshake();
        PrintWriter out = new PrintWriter( socket.getOutputStream() );

        String[] protocols = socket.getSupportedProtocols();
        
        System.out.println("Supported Protocols: " + protocols.length);
        for(int i = 0; i < protocols.length; i++)
            {
                System.out.println("  " + protocols[i]);
            }
        
        protocols = socket.getEnabledProtocols();
        
        System.out.println("Enabled Protocols: " + protocols.length);
        for(int i = 0; i < protocols.length; i++)
            {
                System.out.println("  " + protocols[i]);
            }
    }
}
