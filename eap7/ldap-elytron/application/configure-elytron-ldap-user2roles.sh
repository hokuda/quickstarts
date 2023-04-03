#!/bin/bash
# Batch script to enable elytron for the quickstart application in the JBoss EAP server
BINDDN="CN=Administrator,CN=Users,DC=EXAMPLE1,DC=COM"
PASSWD='secret'
LDAPURL="ldap://35.77.104.129"
BASEDN="CN=Users,DC=Example1,DC=COM"
REALMNAME="ldap-realm"
SECURITYDOMAINNAME="ldap-security-domain"


./bin/jboss-cli.sh -c << EOS
/subsystem=logging/console-handler=CONSOLE:write-attribute(name=level,value=TRACE)
/subsystem=logging/logger=org.wildfly.elytron:add(level=TRACE)
/subsystem=logging/logger=org.wildfly.security:add(level=TRACE)
/subsystem=elytron/dir-context=ldap-dir-context:add(url="$LDAPURL",principal="$BINDDN",credential-reference={clear-text="$PASSWD"}, referral-mode=ignore)

# note: if filter-base-dn=contextRootDN, the error happens, because of referral
#/subsystem=elytron/ldap-realm=$REALMNAME:add(dir-context=ldap-dir-context,direct-verification="true",identity-mapping={rdn-identifier="sAMAccountName", attribute-mapping=[{filter-base-dn="CN=Users,$BASEDN",filter="(& (objectClass=group)(member={1}))",from="cn",to="Roles",role-recursion="5"}], search-base-dn="$BASEDN", use-recursive-search="true"})
#/subsystem=elytron/ldap-realm=$REALMNAME:add(dir-context=ldap-dir-context,direct-verification="true",identity-mapping={rdn-identifier="sAMAccountName", attribute-mapping=[{filter-base-dn="$BASEDN",filter="(& (objectClass=group)(member={1}))",from="cn",to="Roles",role-recursion="5"}], search-base-dn="$BASEDN", use-recursive-search="true"})

# user -> role mapping (1/2)
/subsystem=elytron/ldap-realm=$REALMNAME:add(dir-context=ldap-dir-context,direct-verification="true",identity-mapping={rdn-identifier="sAMAccountName", attribute-mapping=[{filter-base-dn="$BASEDN",filter="(& (objectClass=user)(sAMAccountName={0}))",from="sAMAccountName",to="Roles",role-recursion="0"}], search-base-dn="$BASEDN", use-recursive-search="true"})

/subsystem=elytron/simple-role-decoder=from-roles-attribute:add(attribute=Roles)
#/subsystem=elytron/security-domain=$SECURITYDOMAINNAME:add(realms=[{realm=$REALMNAME,role-decoder=from-roles-attribute}],default-realm=$REALMNAME,permission-mapper=default-permission-mapper)

# user -> role mapping (2/2)
/subsystem=elytron/mapped-role-mapper=defaultRole:add(keep-mapped=true, keep-non-mapped=true, role-map={"user1" => ["ADMINISTRATOR", "ADMIN", "group1"]})
/subsystem=elytron/security-domain=$SECURITYDOMAINNAME:add(realms=[{realm=$REALMNAME,role-decoder=from-roles-attribute}],default-realm=$REALMNAME,permission-mapper=default-permission-mapper,role-mapper=defaultRole)

/subsystem=elytron/configurable-sasl-server-factory=ldap-sasl-server-factory:add(sasl-server-factory=elytron)
/subsystem=elytron/sasl-authentication-factory=ldap-sasl-authentication:add(sasl-server-factory=ldap-sasl-server-factory,security-domain=$SECURITYDOMAINNAME,mechanism-configurations=[{mechanism-name=PLAIN,mechanism-realm-configurations=[{realm-name=$REALMNAME}]}])
/subsystem=elytron/configurable-sasl-server-factory=ldap-sasl-server-factory:list-add(name=filters, value={pattern-filter=PLAIN})


/subsystem=remoting/http-connector=http-remoting-connector:write-attribute(name=sasl-authentication-factory,value=ldap-sasl-authentication)
/subsystem=remoting/http-connector=http-remoting-connector:undefine-attribute(name=security-realm)
/subsystem=ejb3/application-security-domain=other:add(security-domain=$SECURITYDOMAINNAME)
reload
EOS

