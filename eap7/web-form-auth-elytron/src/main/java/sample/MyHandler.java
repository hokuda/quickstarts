package sample;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.handlers.ServletRequestContext;

import org.jboss.security.SecurityContextAssociation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Principal;

public class MyHandler implements HttpHandler {
    
    private HttpHandler next;
    
    public MyHandler(HttpHandler next) {
        this.next = next;
    }
    
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        ServletRequestContext servletContext = exchange.getAttachment(ServletRequestContext.ATTACHMENT_KEY);
        HttpServletRequest request = servletContext.getOriginalRequest();
        HttpServletResponse response = servletContext.getOriginalResponse();

        System.err.println("user principal in request = " + request.getUserPrincipal());
        System.err.println("user principal in security context  = " + org.jboss.security.SecurityContextAssociation.getPrincipal() );
        System.err.println("user principal in session  = " + (Principal)request.getSession().getAttribute("reservedprincipal"));
        System.err.println("current subject = " + org.jboss.security.SecurityContextAssociation.getSubject() );

        System.err.println("Invoking Handler ... ");
        try {
            do_nothing(request, response);
        }
        catch (Exception e) {
            System.err.println(e);
            throw e;
        }
        
        if (!response.isCommitted()) {
            next.handleRequest(exchange);
        }   
        System.err.println("Invoked Handler ... ");
        
        servletContext = exchange.getAttachment(ServletRequestContext.ATTACHMENT_KEY);
        request = servletContext.getOriginalRequest();
        response = servletContext.getOriginalResponse();

        //reserve the principal in the session
        if (request.getUserPrincipal() != null) {
            request.getSession().setAttribute("reservedprincipal", request.getUserPrincipal());
        }
        System.err.println("user principal in request = " + request.getUserPrincipal());
        System.err.println("user principal in security context  = " + org.jboss.security.SecurityContextAssociation.getPrincipal() );
        System.err.println("user principal in session  = " + (Principal)request.getSession().getAttribute("reservedprincipal"));
        System.err.println("current subject = " + org.jboss.security.SecurityContextAssociation.getSubject() );
    }

    public void do_nothing(HttpServletRequest req, HttpServletResponse res) {
        //do nothing
    }
}
