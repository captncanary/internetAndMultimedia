<%-- 
    Document   : housekeeping
    Created on : 10-Dec-2016, 20:55:24
    Author     : Stephen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%-- 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
--%>
<!DOCTYPE html>
<html>
   <head>
	<meta http-equiv="Content-type" content="text/html;charset=utf-8" />
	<title>Heartache Hotel Website</title>

	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="staff.js" ></script>
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
		   Housekeeping Page<br><br>

		   Room Status Key:<br>

		   A - Available (Empty and prepared for next customer)<br>
		   O - Occupied<br>
		   C - Checked Out (Ready for housekeeping)<br>
		   X - Unavailable (Housekeeping in progress)<br>
		   <br><br>
		</p>


		<form id="hkForm1" action="houseKeepingServlet" method="POST">
		   <fieldset> <legend> Get Rooms by Status </legend>

			<input id="submit" type="submit" value="Go" >
			<br><br>
			Room Status: <select id="status" name="status">
			   <option value="C">C</option>
			   <option value="A">A</option>
			   <option value="X">X</option>
			   <option value="O">O</option>
			</select>
		   </fieldset>
		</form>

		<br><br>
		<c:if test="${not empty message}">
		   <p>${message}</p>
	      </c:if>
		<br><br>
		
		
		<form id="hkForm2" action="houseKeepingUpdateServlet" method="POST">
		   <fieldset> <legend> Change Room Status </legend>

			<input id="submit" type="submit" value="Go" >
			<br><br>
			Room Number: <select id="roomNo" name="roomNo">
			   <option value="101">101</option>
			   <option value="102">102</option>
			   <option value="103">103</option>
			   <option value="104">104</option>
			   <option value="105">105</option>
			   <option value="106">106</option>
			   <option value="107">107</option>
			   <option value="108">108</option>
			   <option value="201">201</option>
			   <option value="202">202</option>
			   <option value="203">203</option>
			   <option value="204">204</option>
			   <option value="205">205</option>
			   <option value="206">206</option>
			   <option value="207">207</option>
			   <option value="208">208</option>
			   <option value="209">209</option>
			   <option value="210">210</option>
			   <option value="211">211</option>
			   <option value="212">212</option>
			   <option value="301">301</option>
			   <option value="302">302</option>
			   <option value="303">303</option>
			   <option value="304">304</option>
			   <option value="305">305</option>
			   <option value="306">306</option>
			   <option value="307">307</option>
			   <option value="308">308</option>
			   <option value="309">309</option>
			   <option value="310">310</option>
			   <option value="311">311</option>
			   <option value="312">312</option>
			</select>
			Room Status: <select id="status1" name="status1">
			   <option value="A">A</option>
			   <option value="X">X</option>
			   <option value="C">C</option>
			</select><br><br>
			Room Comments <br><textarea rows="4" cols="35" name="comments" maxlength="300"></textarea>
		   </fieldset>
		</form>
		
		<c:if test="${not empty updateMessage}">
		   <p>${updateMessage}</p>
	      </c:if>

	   </div>
	</div>

   </body>
</html>
