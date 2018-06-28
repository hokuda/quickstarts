package sample;

import java.util.*;
import org.jboss.security.vault.*;

public class Vault implements org.jboss.security.vault.SecurityVault
{
    public void init(Map<String,Object> options) throws SecurityVaultException { /* do nothing */ }
   
    public boolean isInitialized() { return true; }

    public byte[] handshake(Map<String,Object> handshakeOptions) throws SecurityVaultException
    {
        byte[] b = {'f', 'a', 'k', 'e'};
        return b;
    }
    
    public Set<String> keyList() throws SecurityVaultException
    {
        String[] keys = {"fake"};
        return new java.util.HashSet(java.util.Arrays.asList(keys));
    }
    
    public boolean exists(String vaultBlock, String attributeName) throws SecurityVaultException { return true; }
    
    public void store(String vaultBlock, String attributeName,char[] attributeValue, byte[] sharedKey) throws SecurityVaultException
    { /* do nothing */ }
    
    public char[] retrieve(String vaultBlock, String attributeName, byte[] sharedKey) throws SecurityVaultException
    { return "password".toCharArray(); }
   
    public boolean remove(String vaultBlock, String attributeName, byte[] sharedKey) throws SecurityVaultException
    { return true; }
}
