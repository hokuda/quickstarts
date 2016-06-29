package sample;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

public class RestrictIpAddrValve extends ValveBase {
    protected String allowedAddr = null;

    public String getAddr() { return allowedAddr; }
    
    public void setAddr(String addr) { this.allowedAddr = addr; }
    
    public void invoke(Request request, Response response) throws IOException, ServletException {
        String remoteAddr = request.getRemoteAddr();
        System.out.println("Allowed Addr = " + allowedAddr);
        System.out.println("Addr of incoming request = " + remoteAddr);
        if(!remoteAddr.equals(allowedAddr)){
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        getNext().invoke(request, response);
    }
}
