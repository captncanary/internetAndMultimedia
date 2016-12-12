<%-- 
    Document   : booking
    Created on : 02-Dec-2016, 15:16:16
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

	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="confirmation.js" ></script>
	<script type="text/javascript"></script>

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
		<li><a href="mybooking.jsp">My Booking</a></li>
		<li><a href="leisure.html">Local Facilities and Attractions</a></li>

	   </ul>
	</nav>

	<div id="main">
	   <div class="textContent">

		<form id="nameForm1" action="confirmServlet" method="POST">
		   <fieldset id="fieldset"> <legend>Booking Confirmation Page</legend><br>
			<div class="bookingDetails">
			   <p id="bookingDets">
			   <h3>Your Booking is Confirmed</h3>
			   <h3>Booking Details</h3>

			   Your unique booking reference is 
			   <%
				int b_ref = (Integer) session2.getAttribute("b_ref");
				
                        System.out.println(b_ref);
                        
                        
				out.println("<a id=\"b_ref\">" + b_ref + "</a><br>You should quote this if you "
					  + "need to modify or cancel your booking.<br>"
					  + "You have now booked");
                        %>
                        
                        <%
				int rooms = (Integer) session2.getAttribute("rooms");
				System.out.println(rooms);
				out.println(rooms);
				String roomsStr = "rooms";
				if (rooms == 1)
				{
				   roomsStr = "room";
				}
				out.println(" " + roomsStr + " for" );

				int nights = (Integer) session2.getAttribute("nights");
				System.out.println(nights);
				out.println(nights);
				String nightsStr = "nights";
				if (nights == 1)
				{
				   nightsStr = "night";
				}
				out.println(" " + nightsStr + " .<br><br>Your arrival date is<br>");

				String arriveDate = (String) session2.getAttribute("arriveDateStr");
				System.out.println(arriveDate);
				out.println(arriveDate + "<br>Check in from 3pm<br><br>You depart on<br>");

				String departDate = (String) session2.getAttribute("departDateStr");
				System.out.println(departDate);
				out.println(departDate);
			   %>
			   <br>Check out by 12pm<br>
			   </p>
			</div>
			<div class="roomDetails">
			   <p id="roomDets"></p>
			   <h3>Room Details</h3>
			   <%
				
				String[] roomType = new String[rooms];
				int[] adults = new int[rooms];
				int[] children = new int[rooms];
				boolean[] cot = new boolean[rooms];
				
				for(int i = 0; i < rooms; i++)
				{
				   out.println("Room "+ (i+1) +"<br>");
				   
				   roomType[i] = (String) session.getAttribute("roomType" + (i+1));
				   if (roomType[i].equals("std_t"))
				   {
					roomType[i] = "Standard Twin Room";
				   } else if (roomType[i].equals("std_d"))
				   {
					roomType[i] = "Standard Double Room";
				   } else if (roomType[i].equals("sup_t"))
				   {
					roomType[i] = "Deluxe Twin Room";
				   } else if (roomType[i].equals("sup_d"))
				   {
					roomType[i] = "Deluxe Double Room";
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
					out.println("1 cot<br>");
				   }
				   out.println("<br>");
				}
				
				

				
			   %>


			</div>

			<div class="paymentDetails">
			   <p id="paymentDets">
			   <h3>Payment Details</h3>
			   Total cost for your stay: £
			   <%
				double b_cost = (Double) session2.getAttribute("b_cost");
				DecimalFormat df = new DecimalFormat("0.00");
				System.out.println(df.format(b_cost));
				out.println(df.format(b_cost));
			   %>
			   <br><br>
			   Full payment will be taken upon check out. 
			</div>
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
		   
		   // Clear session after confirmation to avoid refresh submitting additional booking
		   session2.invalidate();
		%>
	   </footer>

   </body>

</html>
