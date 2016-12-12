
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author mre16utu - Stephen Whiddett
 */
public class paymentServlet extends HttpServlet {

   /**
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
    * methods.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    * @throws java.lang.ClassNotFoundException
    * @throws java.sql.SQLException
    * @throws java.text.ParseException
    */
   protected void processRequest(HttpServletRequest request, HttpServletResponse
	     response) throws ServletException, IOException,
	     ClassNotFoundException, SQLException, ParseException
   {
	response.setContentType("text/html;charset=UTF-8");
	try (PrintWriter out = response.getWriter())
	{
	   HttpSession session2 = request.getSession(false);

         String cmpHost= "cmp-16java03.cmp.uea.ac.uk";
         String username = "group_au"; // use your username for the database
         String pwd = "group_au"; // use your DB’s password, same as your username
         String myDbName = "group_au"; //your DATABASE name, same as your username
         // make a string for my DB’s connection url
         String myDbURL = ("jdbc:postgresql://" + cmpHost + "/" + myDbName);

//	   String cmpHost = "localhost:5433";
//	   String username = "postgres"; // use your username for the database
//	   String pwd = "dbpass"; // use your DB’s password, same as your username
//	   String myDbName = ""; //your DATABASE name, same as your username
//	   // make a string for my DB’s connection url
//	   String myDbURL = ("jdbc:postgresql://" + cmpHost + "/" + myDbName);

	   Class.forName("org.postgresql.Driver");
	   Connection connection = DriverManager.getConnection(
		     myDbURL, username, pwd);
	   Statement statement = connection.createStatement();

	   //START GET FROM FORM
	   String paymentType, cardNumber, expiryDate, firstName, lastName, 
		    addressLine1, city, postcode, email, 
		    arriveDate, departDate, comments;
	   int c_no, nights;

	   paymentType = request.getParameter("pTypes");
	   cardNumber = request.getParameter("cardNo");
	   expiryDate = request.getParameter("exDate");
	   firstName = request.getParameter("fName");
	   lastName = request.getParameter("lName");
	   addressLine1 = request.getParameter("address1");
	   city = request.getParameter("city");
	   postcode = request.getParameter("pCode");
	   email = request.getParameter("email");
	   comments = request.getParameter("comments");

	   // Check if customer exists in db
	   // If so, get c_no and if not create new customer and get c_no
	   statement.execute("set schema 'hotelbooking';");

	   c_no = getCustomerNo(firstName, lastName, addressLine1, city, postcode,
		     email, paymentType, expiryDate, cardNumber,
		     statement, request, response);

	   nights = (int) session2.getAttribute("nights");
	   arriveDate = (String) session2.getAttribute("arriveDateStr");
	   departDate = (String) session2.getAttribute("departDateStr");

	   int noRooms = (int) session2.getAttribute("rooms");
	   int[] adultsPerRoom = new int[noRooms];
	   int[] childrenPerRoom = new int[noRooms];
	   boolean[] cotsInRoom = new boolean[noRooms];

	   for (int i = 0; i < noRooms; i++)
	   {
		adultsPerRoom[i] = (int) session2.getAttribute("adults" + (i + 1));
		childrenPerRoom[i] = (int) session2.getAttribute("children" + (i + 1));
		cotsInRoom[i] = (boolean) session2.getAttribute("cot" + (i + 1));
	   }

	   // Recover the hashmap of room types and quantity of each type and 
	   // convert to SQL query format
	   HashMap<String, Integer> rmHashmap
		     = (HashMap<String, Integer>) session2.getAttribute("rmHashmap");

	   String[] keys = new String[rmHashmap.size()];
	   int count = 0;
	   for (String key : rmHashmap.keySet())
	   {
		keys[count] = key;
		count++;
	   }
	   int[] values = new int[rmHashmap.size()];
	   count = 0;
	   for (String key : keys)
	   {
		values[count] = rmHashmap.get(key);
		count++;
	   }
	   // Construct the keys as an SQL query array string
	   String roomTypeKeyStr = arrayToSqlString(keys);

	   // Construct the values as an SQL query array string
	   String roomTypeValuesStr = arrayToSqlString(values);

	   // Convert arrays to SQL friendly string format
	   String adultsPerRoomStr = arrayToSqlString(adultsPerRoom);
	   String childrenPerRoomStr = arrayToSqlString(childrenPerRoom);
	   String cotsInRoomStr = arrayToSqlString(cotsInRoom);

	   // create SQL statement to enter new booking
	   String sqlStatement = "SELECT new_multi_booking(" + c_no + ", "
		     + nights + ", '" + arriveDate + "', '" + departDate + "', "
		     + adultsPerRoomStr + ", " + childrenPerRoomStr + ", "
		     + cotsInRoomStr + ", " + roomTypeKeyStr + ", "
		     + roomTypeValuesStr + ", '" + comments + "');";
	   System.out.println(sqlStatement);

	   // add booking to database
	   ResultSet resultSet = statement.executeQuery(sqlStatement);
	   resultSet.next();
	   String new_booking = resultSet.getString("new_multi_booking");
	   new_booking = new_booking.substring(1, new_booking.length() - 1);
	   session2.setAttribute("new_multi_booking", new_booking);
	   String[] split = new_booking.split(",");
	   int b_ref = Integer.parseInt(split[0]);
	   session2.setAttribute("b_ref", b_ref);
	   double b_cost = Double.parseDouble(split[1]);
	   session2.setAttribute("b_cost", b_cost);
	   // Close the database connection once finished
	   connection.close();

	   RequestDispatcher view
		     = request.getRequestDispatcher("confirmation.jsp");
	   view.forward(request, response);
	}
	//END GET FROM FORM
   }

   private String arrayToSqlString(int[] intArray)
   {
	String sqlString = "ARRAY[";
	for (int i = 0; i < intArray.length; i++)
	{
	   sqlString += intArray[i];
	   if (i < (intArray.length - 1))
	   {
		sqlString += ",";
	   }
	}
	sqlString += "]::INTEGER[]";
	return sqlString;
   }

   private String arrayToSqlString(String[] strArray)
   {
	String sqlString = "ARRAY['";
	for (int i = 0; i < strArray.length; i++)
	{
	   sqlString += strArray[i];
	   if (i < (strArray.length - 1))
	   {
		sqlString += "','";
	   }
	}
	sqlString += "']::TEXT[]";
	return sqlString;
   }

   private String arrayToSqlString(boolean[] boolArray)
   {
	String sqlString = "ARRAY[";
	for (int i = 0; i < boolArray.length; i++)
	{
	   sqlString += boolArray[i];
	   if (i < (boolArray.length - 1))
	   {
		sqlString += ",";
	   }
	}
	sqlString += "]::BOOLEAN[]";
	return sqlString;
   }

   private int getCustomerNo(String firstName, String lastName,
	     String addressLine1, String city, String postcode, String email,
	     String paymentType, String expiryDate, String cardNumber,
	     Statement statement, HttpServletRequest request,
	     HttpServletResponse response) throws SQLException,
	     ServletException, IOException
   {
	boolean temp = true;
	boolean insert = true;
	int c_no = 0;
	while (temp)
	{

	   String sqlStatement = "SELECT * FROM customer" + " WHERE c_name = '"
		     + firstName + " " + lastName + "'" + " AND c_address = '"
		     + addressLine1 + ", " + city + " " + postcode + "';";
	   System.out.println(sqlStatement);
	   // Run the query and retrieve the results
	   ResultSet resultSet = statement.executeQuery(sqlStatement);
	   // Check available rooms are returned and inform customer or proceed
	   // to booking
	   String results = "";
	   if (!resultSet.isBeforeFirst() && insert)
	   {
		// Create a new customer
		System.out.println("Creating new customer");

		//statement.execute("set schema 'hotelbooking';");
		sqlStatement = "INSERT INTO customer (c_name, c_email, c_address, "
			  + "c_cardtype, c_cardexp, c_cardno)"
			  + " VALUES('" + firstName + " " + lastName + "','" + email 
			  + "','" + addressLine1 + ", " + city + " " + postcode 
			  + "', '" + paymentType + "', '" + expiryDate + "', '" 
			  + cardNumber + "');";
		System.out.println(sqlStatement);
		// Run the query and retrieve the results
		statement.execute(sqlStatement);
		insert = false;
	   } else
	   {
		while (resultSet.next())
		{
		   results = results + resultSet.getString("c_no") + "<br>";
		   System.out.println("Customer No: " 
								+ resultSet.getString("c_no"));
		   c_no = Integer.parseInt(resultSet.getString("c_no"));
		}
		temp = false;
	   }
	}
	return c_no;
   }

   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   /**
    * Handles the HTTP <code>GET</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
	     throws ServletException, IOException
   {
	try
	{
	   processRequest(request, response);
	} catch (ClassNotFoundException ex)
	{
	   Logger.getLogger(paymentServlet.class.getName()).log(Level.SEVERE, null, ex);
	} catch (SQLException ex)
	{
	   Logger.getLogger(paymentServlet.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ParseException ex)
	{
	   Logger.getLogger(paymentServlet.class.getName()).log(Level.SEVERE, null, ex);
	}
   }

   /**
    * Handles the HTTP <code>POST</code> method.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
	     throws ServletException, IOException
   {
	try
	{
	   processRequest(request, response);
	} catch (ClassNotFoundException ex)
	{
	   Logger.getLogger(paymentServlet.class.getName()).log(Level.SEVERE, null, ex);
	} catch (SQLException ex)
	{
	   Logger.getLogger(paymentServlet.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ParseException ex)
	{
	   Logger.getLogger(paymentServlet.class.getName()).log(Level.SEVERE, null, ex);
	}
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo()
   {
	return "Short description";
   }// </editor-fold>

}
