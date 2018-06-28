<html>
<body>
  <h2>Hello <%=request.getUserPrincipal()%>!</h2>
  <h2>Hello <%=request.getSession().getAttribute("principal")%>!</h2>
  <%
    StackTraceElement[] s = Thread.currentThread().getStackTrace();
    for (int i = 0; i < s.length; i++) {
        System.out.println(i + " : " + s[i]);
    }
  %>

</body>
</html>
