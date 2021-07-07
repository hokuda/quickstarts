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

<%@ page import="org.wildfly.security.authz.Roles"%>

<%
String username = org.wildfly.security.auth.server.SecurityDomain.getCurrent().getCurrentSecurityIdentity().getPrincipal().getName();
System.out.println(username);

Roles roles = org.wildfly.security.auth.server.SecurityDomain.getCurrent().getCurrentSecurityIdentity().getRoles();

for (String role: roles) {
    System.out.println(role);
}
%>

<!-- https://access.redhat.com/solutions/3368981 -->
</body>
</html>
