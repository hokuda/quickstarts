import javax.net.ssl.*;

public class ShowSupportedCipherSuites {
    public static void main(String[] args) throws Exception {
        
        SSLContext context = SSLContext.getInstance("TLSv1");
        context.init(null,null,null);
        SSLParameters params = context.getSupportedSSLParameters();

        for(String str : params.getCipherSuites()) {
            System.out.println(str);
        }
        //System.out.print(params.getCipherSuites());
        
    }
}
