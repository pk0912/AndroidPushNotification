<jsp:useBean id="obj" class="beans.AdminLoginBean">
<jsp:setProperty name="obj" property="*"/>
</jsp:useBean>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%!String emp_id;%>
    <%
    		   emp_id=obj.getEmp_id();
		       int i=obj.checkLoginDetails();
		       if(i==0)
		       {
		           String a="<html><head><nav class=\"navbar-default\"><div class=\" alert alert-danger nav default\" role=\"alert\" style=\"margin-top:10px\"><h4>Login was not successful!!</h4></div></nav></head></html>";
											session.setAttribute("alert", a);
											response.sendRedirect("adminhome.html");
											out.println("<script>alert('Notification sending fail.')</script>");
		       }
		       if(i==1)
		       {
			       request.getSession(true);
			       session.setAttribute("empid",emp_id);
			       response.sendRedirect("adminpanel.jsp");
		       }		      	  
	%>
</body>
</html>