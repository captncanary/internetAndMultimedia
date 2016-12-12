<%-- 
    Document   : reports
    Created on : 11-Dec-2016, 11:35:18
    Author     : Stephen
--%>

<%@page import="java.util.Date"%>
<%@page import="java.io.File"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
	<meta http-equiv="Content-type" content="text/html;charset=utf-8" />
	<title>Heartache Hotel Website</title>

	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="reports.js" ></script>
	<script type="text/javascript"></script>

	<link type="text/css" rel="stylesheet" href="main.css">
	<style type="text/css"></style>
   </head>
   <body>
	   <header id="top">
		<div class="right">
		   <a>Telephone: 01603 555555<br></a>
		   <a href="mailto:reception@heartachehotel.co.uk" >reception@heartachehotel.co.uk</a>
		</div>
		<div class="center">
		   <h1>Heartache Hotel, Norwich</h1>
		</div>
	   </header>

	<nav class="navlist">
	   <ul class="menuitem">
		<li><a href="index.jsp">Home</a></li>
		<li><a href="staff.jsp">Staff</a></li>
	   </ul>
	</nav>

	<div id="main">
	   <div class ="textContent">
		<p>
		   Reports Page<br><br>

		   
		   <br><br>
		</p>


		<form id="hkForm1" action="reportsServlet" method="POST">
		   <fieldset> <legend>Weekly Reports</legend>

			<input id="submit" type="submit" value="Go" >
			<br><br>
			Week Start Date: <input type="date" id="fdate" name="fdate" value="2016-12-01"> 
		   </fieldset>
		</form>

		<br><br>
		<c:if test="${not empty message}">
		   <p>${message}</p>
	      </c:if>
		<br><br>
		


	   </div>
	</div>
		
		<footer>
      <!-- Horizontal rule and update comment -->
      <hr />
	<p>Heartache Hotel, Main Street, Norwich, NR5 1AB</p>
	<%
	   String filename = application.getRealPath(request.getServletPath());
	   File file = new File(filename);
	   Date date = new Date(file.lastModified());
	   out.println("<p id=\"footerText\">Page last updated " + date + " by Team AU</p>");
	%>
   </footer>

   </body>
   
   
</html>
