package org.jboss.as.quickstarts.ejb_security;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import javax.security.jacc.PolicyContext;
import javax.security.auth.Subject;

/**
 * The server side security interceptor responsible for handling any security identity propagated from the client.
 *
 * @author <a href="mailto:darran.lofthouse@jboss.com">Darran Lofthouse</a>
 */
public class TestInterceptor {

    @AroundInvoke
    public Object aroundInvoke(final InvocationContext invocationContext) throws Exception {
        System.out.println("TestInterceptor invoked");
        try{
            Subject subject = (Subject)PolicyContext.getContext("javax.security.auth.Subject.container");    
            System.out.println("Subject in Interceptor: " + subject.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return invocationContext.proceed();
    }
}
