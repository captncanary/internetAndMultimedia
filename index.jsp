<%-- test
    Document   : index
    Created on : 08-Dec-2016, 14:41:09
    Author     : mre16utu
--%>
<%@page import="java.io.File"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
   <meta http-equiv="Content-type" content="text/html;charset=utf-8" />
   <title>Heartache Hotel Website</title>

   <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
   <script src="index.js" ></script>
   <script type="text/javascript"></script>
   <script src="https://maps.googleapis.com/maps/api/js?key=
          AIzaSyCy_Gr1-c--usnO2W1K82JdVnrdjz7SL0o&callback=initialize"></script>

   <link type="text/css" rel="stylesheet" href="main.css">
   <style type="text/css"></style>

</head>
<%
   HttpSession session1 = request.getSession(true);
%>
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
         <li><a href="mybooking.jsp">My Booking</a></li>
         <li><a href="leisure.html">Local Facilities and Attractions</a></li>
         <!-- <li><a href="payment.jsp">Payment</a></li>
         <li><a href="confirmation.jsp">Confirmation</a></li>
         <li><a href="#staffbooking">Staff Booking</a></li>
         <li><a href="#checkinout">Check In/Out</a></li>
         <li><a href="#housekeeping">Housekeeping</a></li> -->
      </ul>
   </nav>

   <div id="main">
	<div class ="textContent">

	   <p>Situated on the western edge of Norwich we are an independent hotel offering 
		excellent accommodation in a scenic and convenient location for you to explore Norfolk.
		<br><br>
	   </p>
	</div>

      <form id="nameForm1" action="indexServlet" method="POST">
         <fieldset> <legend> Make Booking </legend>
            Arrival Date: <input type="date" id="fdate" name="fdate" value="2016-11-01"> 
            <input id="submit" type="submit" value="Check Availability" >
            <br><br>
            Nights      : <select id="nights" name="nights" onchange="updateForNoRooms();">
               <option value="1">1</option>
               <option value="2">2</option>
               <option value="3">3</option>
               <option value="4">4</option>
               <option value="5">5</option>
               <option value="6">6</option>
               <option value="7">7</option>
               <option value="8">8</option>
               <option value="9">9</option>
               <option value="10">10</option>
               <option value="11">11</option>
               <option value="12">12</option>
               <option value="13">13</option>
               <option value="14">14</option>
               <option value="15">15+</option>
            </select>
            Rooms :       <select id="rooms" name="rooms" onchange="updateForNoRooms();">
               <option value="1">1</option>
               <option value="2">2</option>
               <option value="3">3</option>
               <option value="4">4</option>
               <option value="5">5+</option>
            </select> 
            <div id="room1">
               Adults :      <select id="adults1" name="adults1">
                  <option value="1">1</option>
                  <option value="2">2</option>
               </select> 
               Children :    <select id="children1" name="children1">
                  <option value="0">0</option>
                  <option value="1">1</option>
			<option value="2">2</option>
               </select> 
               <input type="checkbox" id="cot1" name="cot1" onclick="refreshCheckboxValue(this)" value="false">Cot&emsp;
               Room Type :   <select id="roomtype1" name="roomtype1" onclick="setPreviewImage('roomtype');">
                  <option value="stddouble">Standard Double @£65/nt</option>
                  <option value="stdtwin">Standard Twin @£62/nt</option>
                  <option value="dlxdouble">Deluxe Double @£77/nt</option>
                  <option value="dlxtwin">Deluxe Twin @£75/nt</option>
               </select>
            </div>
            <br><br>
            <div id ="room2">
               Adults :      <select id="adults2" name="adults2">
                  <option value="1">1</option>
                  <option value="2">2</option>
               </select> 
               Children :    <select id="children2" name="children2">
                  <option value="0">0</option>
                  <option value="1">1</option>
			<option value="2">2</option>
               </select>
               <input type="checkbox" id="cot2" name="cot2" onclick="refreshCheckboxValue(this)" value="false">Cot&emsp;
               Room Type :   <select id="roomtype2" name="roomtype2" onclick="setPreviewImage('roomtype2');">
                  <option value="stddouble">Standard Double @£65/nt</option>
                  <option value="stdtwin">Standard Twin @£62/nt</option>
                  <option value="dlxdouble">Deluxe Double @£77/nt</option>
                  <option value="dlxtwin">Deluxe Twin @£75/nt</option>
               </select>
            </div>
            <br><br>
            <div id ="room3">
               Adults :      <select id="adults3" name="adults3">
                  <option value="1">1</option>
                  <option value="2">2</option>
               </select> 
               Children :    <select id="children3" name="children3">
                  <option value="0">0</option>
                  <option value="1">1</option>
			<option value="2">2</option>
               </select>
               <input type="checkbox" id="cot3" name="cot3" onclick="refreshCheckboxValue(this)" value="false">Cot&emsp;
               Room Type :   <select id="roomtype3" name="roomtype3" onclick="setPreviewImage('roomtype3');">
                  <option value="stddouble">Standard Double @£65/nt</option>
                  <option value="stdtwin">Standard Twin @£62/nt</option>
                  <option value="dlxdouble">Deluxe Double @£77/nt</option>
                  <option value="dlxtwin">Deluxe Twin @£75/nt</option>
               </select>
            </div>
            <br><br>
            <div id ="room4">
               Adults :      <select id="adults4" name="adults4">
                  <option value="1">1</option>
                  <option value="2">2</option>
               </select> 
               Children :    <select id="children4" name="children4">
                  <option value="0">0</option>
                  <option value="1">1</option>
			<option value="2">2</option>
               </select>
               <input type="checkbox" id="cot4" name="cot4" onclick="refreshCheckboxValue(this)" value="false">Cot&emsp;
               Room Type :   <select id="roomtype4" name="roomtype4" onclick="setPreviewImage('roomtype4');">
                  <option value="stddouble">Standard Double @£65/nt</option>
                  <option value="stdtwin">Standard Twin @£62/nt</option>
                  <option value="dlxdouble">Deluxe Double @£77/nt</option>
                  <option value="dlxtwin">Deluxe Twin @£75/nt</option>
               </select>
            </div>
            <div id ="room5">
               For group bookings of 5+ rooms please call us on 01603 555555 and we will be happy to help.
            </div>
            <div id ="night15">
               For stays longer than 14 nights please call us on 01603 555555 and we will be happy to help.
            </div>
            <div id ="available">
               <%
			try
			{
			   if ((Boolean) session1.getAttribute("noRooms"))
			   {

				int std_d = (Integer) session.getAttribute("std_d_available");
				int std_t = (Integer) session.getAttribute("std_t_available");
				int sup_d = (Integer) session.getAttribute("sup_d_available");
				int sup_t = (Integer) session.getAttribute("sup_t_available");
				int totalRooms = std_d + std_t + sup_d + sup_t;
				if (totalRooms > 0)
				{
				   out.println("Insufficient rooms available of the type"
					     + " requested.<br>"
					     + "Current availability on your chosen dates:");
				   if (std_d > 0)
				   {
					out.println("<br>Standard Double: " + std_d);
				   }
				   if (std_t > 0)
				   {
					out.println("<br>Standard Twin: " + std_t);
				   }
				   if (sup_d > 0)
				   {
					out.println("<br>Deluxe Double: " + sup_d);
				   }
				   if (sup_t > 0)
				   {
					out.println("<br>Deluxe Twin: " + sup_t);
				   }
				} else
				{
				   out.println("No rooms available on selected dates. "
					     + "Please try alternative dates or call us "
					     + "for assistance.");
				}

			   }
			   session1.setAttribute("noRooms", false);
			   //out.println((boolean)session1.getAttribute("noRooms"));
			} catch (Exception e)
			{
			   // Do nothing as it just means session value not present
			}
			//session1.setAttribute("noRooms", false);
			//out.println((String)session1.getAttribute("noRooms"));
               %>
            </div>
         </fieldset>
      </form>


      <div class="gallery">
         <h2>Hotel Images</h2>
         <div class="thumbnails">
            <img onmouseover="setCaption('dlxtwin');" name="deluxe_twin" src="images/deluxe_twin.jpg" alt="deluxe twin" />
            <img onmouseover="setCaption('dlxdouble');" name="deluxe_double" src="images/deluxe_double.jpg" alt="deluxe double" />
            <img onmouseover="setCaption('stdtwin');" name="standard_twin" src="images/standard_twin.jpg" alt="standard twin" />
            <img onmouseover="setCaption('stddouble');" name="standard_double" src="images/standard_double.jpg" alt="standard double" />
            <img onmouseover="setCaption('pool');" name="pool" src="images/pool.jpg" alt="pool" />
            <img onmouseover="setCaption('gym');" name="gym" src="images/gym.jpg" alt="gym" />
         </div>
         <div class="preview" align="center">
            <figure>
               <img id ="preview" name="preview" src="images/standard_double.jpg" alt="preview"/>
               <figcaption id="previewcaption">Standard Double Room</figcaption>
            </figure>
         </div>
      </div>



      <div class ="textContent">
         <h2><a id="map">Hotel Information</a></h2>
         <p>The Heartache Hotel is located in a beautiful rural setting on the 
		western edge of Norwich. Set in an 18th century building, the 
		interior has been fully modernised to provide our guests with luxury
		accommodation. We have 32 rooms with a choice of standard or deluxe 
		and twin or double rooms. All rooms sleep a maximum of 2 adults. 
		Additionally, sofa beds and a cot are available for up to 2 children 
		per room. Children must be accompanied by at least 1 adult. 
		Pets are not permitted.<br> Our facilities include a bar and a la 
		carte dining area catering to you for breakfast, lunch and dinner at
		additional cost. Access to our private gym and swimming pool is 
		included in your stay.</p>

         <h2><a id="map">View Hotel Location on a Map</a></h2>
	   <div id="map-canvas" ></div>



         <h2><a id="Directions">Directions</a></h2>
         <p>From West, follow A11, at junction with A47 turn left towards 
		Swaffham. Exit at Longwater.</p>
         <p>From North or South, follow A47. Exit at Longwater.</p>

         <h2><a id="parking">Hotel Parking</a></h2>
         <p>Free on-site parking is available for all of our guests.</p>




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

