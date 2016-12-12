<%-- 
    Document   : booking
    Created on : 29-Nov-2016, 16:17:33
    Author     : mre16utu
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
   <head>
	<meta http-equiv="Content-type" content="text/html;charset=utf-8" />
	<title>Heartache Hotel Website</title>

	<link type="text/css" rel="stylesheet" href="main.css">
	<style type="text/css"></style>

   </head>

   <body>
	<%
	   HttpSession session2 = request.getSession(false);
	%>
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
	   </ul>
	</nav>

	<div id="main">
	   <div class="textContent">
		
		<form id="nameForm1" action="bookingServlet" method="POST">
		   <fieldset> <legend><h2>Check Booking Details</h2></legend>

			<div class="bookingDetails">
			   <p id="bookingDets"> 

				<%
				   
				   int rooms = (Integer) session2.getAttribute("rooms");
				   out.println("You're booking " + rooms);
				   String room = "room";
				   if (rooms > 1)
				   {
					room = "rooms";
				   }
				   out.println(" " + room + " for");

				   int nights = (Integer) session.getAttribute("nights");
				   out.println(nights);
				   String night = "night";
				   if (nights > 1)
				   {
					night = "nights";
				   }
				   out.println(" " + night + ".<br><h3>Arriving</h3>");

				   Date arriveDate = (Date) session.getAttribute("arriveDate");
				   String arriveDateStr = arriveDate.toString().substring(0,10) 
					     + ", " + arriveDate.toString().substring(24);
				   out.println(arriveDateStr + "<br>Check in from 3pm<br><h3>Leaving</h3>");

				   Date departDate = (Date) session.getAttribute("departDate");
				   String departDateStr = departDate.toString().substring(0,10) 
					     + ", " + departDate.toString().substring(24);
				   out.println(departDateStr + "<br>Check out by 12pm<br>");
				%>
			   </p>
			</div>
			<div class="roomDetails">
			   <p id="roomDets"></p>
			   <h2>Room details</h2>
			   <%
				// Create arrays to store details for each room
				String[] roomType = new String[rooms];
				int[] adults = new int[rooms];
				int[] children = new int[rooms];
				boolean[] cot = new boolean[rooms];
				
				// Keep track of likely booking cost
				double totalCost = 0;
				
				// Loop through room details and print to webpage
				for (int i = 0; i < rooms; i++)
				{
				   out.println("<h3>Room" + (i+1) +"</h3>");
				   
				   roomType[i] = (String) session.getAttribute("roomType" + (i+1));
				   String test = roomType[i];
				   if (roomType[i].equals("std_t"))
				   {
					roomType[i] = "Standard Twin Room";
					totalCost += (nights * 62);
				   } 
				   else if (roomType[i].equals("std_d"))
				   {
					roomType[i] = "Standard Double Room";
					totalCost += (nights * 65);
				   } 
				   else if (roomType[i].equals("sup_t"))
				   {
					roomType[i] = "Deluxe Twin Room";
					totalCost += (nights * 75);
				   } 
				   else if (roomType[i].equals("sup_d"))
				   {
					roomType[i] = "Deluxe Double Room";
					totalCost += (nights * 77);
				   }

				   out.println(roomType[i]);
				   out.println("<br>");

				   adults[i] = (Integer) session.getAttribute("adults" + (i+1));
				   out.println(adults[i]);
				   String adult = "adult";
				   if (adults[i] > 1)
				   {
					adult = "adults";
				   }
				   out.println(" " + adult + "<br>");

				   children[i] = (Integer) session.getAttribute("children" + (i+1));
				   if (children[i] > 0)
				   {
					out.println(children[i] + " child<br>");
				   }

				   cot[i] = (Boolean) session.getAttribute("cot" + (i+1));
				   System.out.println(cot[i]);
				   if (cot[i])
				   {
					out.println("1 cot");
				   }
				}
				DecimalFormat df = new DecimalFormat("0.00");
				out.println("<br>Total Cost of Stay: £" + df.format(totalCost));

			   %>
			</div>
			<input id="submit" type="submit" value="Proceed to Payment" >
		   </fieldset>
		</form>



		<div class ="textContent">
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
		   out.println("<p id=\"footerText\">Page last updated "+ date +" by Team AU</p>");
		%>
	   </footer>

   </body>

</html>
