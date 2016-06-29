package sample;

import javax.annotation.Resource;
//import javax.annotation.Resources;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import org.jboss.ejb3.annotation.Clustered;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.security.jacc.PolicyContext;
import javax.security.auth.Subject;

/**
 * Session Bean implementation class HelloBean
 */
@Stateless
//@Stateful
@LocalBean
@SecurityDomain("simple")
//@Resources( {
//    @Resource(name="java:/jboss/exported/foo/SecureHelloBean", mappedName="java:module/SecureHelloBean!sample.Hello")
//})
@Clustered
public class HelloBean implements HelloRemote {

    @Resource
    private SessionContext sessionContext;
    
    /**
     * Default constructor. 
     */
    public HelloBean() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    @RolesAllowed({"guest"})
    public String hello()
    {
        String hello = null;
        
        try {
            System.out.println("[SecureHelloBean] hello() invoked by " + sessionContext.getCallerPrincipal());
            
            Subject subject = (Subject)PolicyContext.getContext("javax.security.auth.Subject.container");    
            
            hello = "Secure Hello, " + sessionContext.getCallerPrincipal() + "\n" + subject.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }
            
        return hello;
    }
}
