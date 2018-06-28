package sample;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
//import javax.naming.NamingEnumeration;
//import javax.naming.NamingException;
//import javax.naming.*;

//import org.jboss.ejb.client.ContextSelector;
//import org.jboss.ejb.client.EJBClientConfiguration;
//import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
//import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;
//import org.jboss.ejb.client.EJBClientContext;

public class ClientScopedContext {
    
    public static void main(String argv[]) throws Exception {
        ClientScopedContext client = new ClientScopedContext();
        client.invokeEjbByValidUser();
    }
    
    
    void invokeEjbByValidUser() throws Exception {
        
        Properties prop = new Properties();
        prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        prop.put("org.jboss.ejb.client.scoped.context", "true");
        //prop.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        prop.put("remote.connections", "1");
        prop.put("remote.connection.1.host", "127.0.0.1");
        prop.put("remote.connection.1.port", "4447");
        //prop.put("remote.connection.1.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
        prop.put("remote.connection.1.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
        prop.put("remote.connection.1.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
        prop.put("remote.connection.1.username", "guest");
        prop.put("remote.connection.1.password", "guestx");
        //prop.put("remote.connection.1.username", "user1");
        //prop.put("remote.connection.1.password", "password");
        
        // FIXME:  SSL related config parameters
        //prop.put("jboss.naming.client.remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "true");
        //prop.put("jboss.naming.client.connect.options.org.xnio.Options.SSL_STARTTLS", "true");
        prop.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED","false");
        //prop.put("remote.connection.1.connect.options.org.xnio.Options.SSL_ENABLED", "true");        
        prop.put("remote.connection.1.connect.options.org.xnio.Options.SSL_STARTTLS", "false");
        
        //prop.put("remote.clusters", "ejb");
        //prop.put("remote.cluster.ejb.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "true");
        //prop.put("remote.cluster.ejb.connect.options.org.xnio.Options.SSL_ENABLED", "true");
        //prop.put("remote.cluster.ejb.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
        //prop.put("remote.cluster.ejb.username", "ejbuser");
        //prop.put("remote.cluster.ejb.password", "ejbuser");
        
        Context context = new InitialContext(prop);
        
        String lookedup = "ejb:/ejb//HelloBean!sample.HelloRemote";
        //String lookedup = "hoge/Hello";
        HelloRemote hello = (HelloRemote) context.lookup(lookedup);
        try {
            String message = hello.hello();
            System.out.println("  ====>>>> successful invocation returned '" + message + "'");
        } catch (Exception e) {
            System.out.println("  ====>>>> invocation failed : " + e.getMessage());
        }
    }
}
