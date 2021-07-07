<html>
<body>
  <h2>Hello <%=request.getUserPrincipal()%>!</h2>
  <h2>Hello <%=request.getSession().getAttribute("reservedprincipal")%>!</h2>
  <%
//    StackTraceElement[] s = Thread.currentThread().getStackTrace();
//    for (int i = 0; i < s.length; i++) {
//        System.out.println(i + " : " + s[i]);
//    }
  %>

<%@ page import="java.util.*"%>
<%@ page import="javax.security.auth.Subject"%>
<%@ page import="javax.security.jacc.PolicyContext"%>
<%@ page import="java.security.Principal"%>
<%@ page import="org.jboss.security.SimpleGroup"%>

<%@ page import="org.wildfly.security.authz.Roles"%>

<%@ page import="java.text.SimpleDateFormat"%>

<%
String username = org.wildfly.security.auth.server.SecurityDomain.getCurrent().getCurrentSecurityIdentity().getPrincipal().getName();
System.out.println(username);

Roles roles = org.wildfly.security.auth.server.SecurityDomain.getCurrent().getCurrentSecurityIdentity().getRoles();

for (String role: roles) {
    System.out.println(role);
}
%>

<!--
List<String> groups = new ArrayList<String>();

Subject subject = (Subject) PolicyContext.getContext("javax.security.auth.Subject.container");

out.println("Subject : " + subject);
for (Principal principal : subject.getPrincipals()) {
	if (principal.getName().startsWith("Roles")) {
		SimpleGroup group = (SimpleGroup) principal;
		
		Enumeration<Principal> list = group.members();
		while(list.hasMoreElements()) {
			Principal p = list.nextElement();
			groups.add(p.getName());
		}
		break;
	}
}
out.println("Groups : " + groups);
%-->

</body>
</html>
