<%-- 
    Document   : payment
    Created on : 02-Dec-2016, 14:05:10
    Author     : mre16utu
--%>

<%@page import="java.util.Date"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" >
    <head>
        <meta http-equiv="Content-type" content="text/html;charset=utf-8" />
        <title>Heartache Hotel Website</title>

        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="payment.js" ></script>
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
            </ul>
        </nav>

        <!-- TO DO - add a section at top of page displaying cost -->  

        <div id="main">

            <form id="paymentForm" action="paymentServlet" method="POST">
                <fieldset> <legend> Payment Details </legend><br>

                    Payment type <select id="pTypes" name="pTypes">
                        <option value="V">Visa</option>
                        <option value="MC">Mastercard</option>
                        <option value="A">American Express</option> 
                    </select><br><br>
                    Card number <input type="text" id="cardNo" name="cardNo" value="1234567812345678"
                                       size="16" maxlength="16" value="" required><br><br>
                    Expiration date <input type="month" id="exDate" name="exDate" value="12/16"
                                           value="" required><br><br><br><br>
                    First name <input type="text" id="fName" name="fName" value="Ann"
                                      size="16" maxlength="40" value="" required><br><br>
                    Last name <input type="text" id="lName" name="lName" value="Hinchcliffe"
                                     size="16" maxlength="40" value="" required><br><br>
                    Address line 1 <input type="text" id="address1" name="address1" value="81 New Road"
                                          size="16" maxlength="150" value="" required><br><br>
                    City <input type="text" id="city" name="city" value="Acle"
                                size="16" maxlength="40" value="" required><br><br>
                    Postcode <input type="text" id="pCode" name="pCode" value="NR13 7GH"
                                    size="16" maxlength="10" value="" required><br><br>
                    Email <input type="email" id="email" name="email" value="AHinchcliffe@123.com"
                                 size="16" maxlength="60" value="" required><br><br>

			  
			  Additional Comments <br><textarea rows="4" cols="35" name="comments" maxlength="300"></textarea><br><br>
			  
                    <input id="submit" type="submit" value="Confirm Details"><br><br>
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
		   out.println("<p id=\"footerText\">Page last updated "+ date +" by Team A</p>");
		%>
        </footer>

    </body>

</html>
