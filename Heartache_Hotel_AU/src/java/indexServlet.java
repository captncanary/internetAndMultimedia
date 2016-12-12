
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mre16utu - Stephen Whiddett
 */
public class indexServlet extends HttpServlet {

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
//   static int count = 0;
   protected void processRequest(HttpServletRequest request,
	     HttpServletResponse response)
	     throws ServletException, IOException, ClassNotFoundException,
	     SQLException, ParseException
   {
	response.setContentType("text/html;charset=UTF-8");
	try (PrintWriter out = response.getWriter())
	{
	   // Create a session to store form data
	   HttpSession session = request.getSession(false); // set false elsewhere

         String cmpHost= "cmp-16java03.cmp.uea.ac.uk";
         String username = "group_au"; // use your username for the database
         String pwd = "group_au"; // use your DB’s password, same as username
         String myDbName = "group_au"; //your DATABASE name, same as username
         // make a string for my DB’s connection url
         String myDbURL = ("jdbc:postgresql://" + cmpHost + "/" + myDbName);

	   // FOR HOME CONNECTION
//	   String cmpHost = "localhost:5433";
//	   String username = "postgres";
//	   String pwd = "dbpass";
//	   String myDbName = "";
//	   String myDbURL = ("jdbc:postgresql://" + cmpHost + "/" + myDbName);

	   Class.forName("org.postgresql.Driver");
	   Connection connection = DriverManager.getConnection(
									myDbURL, username, pwd);
	   Statement statement = connection.createStatement();

	   //START GET FROM FORM
	   String arriveDateString;
	   Date arriveDate;
	   int nights;
	   int rooms;
	   int adults1;
	   int children1;
	   boolean cot1;
	   String roomType1;
	   int roomNo;

	   arriveDateString = request.getParameter("fdate");
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   arriveDate = sdf.parse(arriveDateString);

	   nights = Integer.parseInt(request.getParameter("nights"));
	   rooms = Integer.parseInt(request.getParameter("rooms"));
	   adults1 = Integer.parseInt(request.getParameter("adults1"));
	   children1 = Integer.parseInt(request.getParameter("children1"));
	   cot1 = Boolean.parseBoolean(request.getParameter("cot1"));
	   roomType1 = request.getParameter("roomtype1");
	   roomType1 = convertRoomType(roomType1);
	   Calendar c = Calendar.getInstance();
	   c.setTime(arriveDate); // Now use today date.
	   c.add(Calendar.DATE, nights); // Adding 5 days
	   Date departDate = c.getTime();
	   String departDateString = sdf.format(departDate);
	   System.out.println(departDateString);

	   session.setAttribute("nights", nights);
	   session.setAttribute("rooms", rooms);
	   session.setAttribute("adults1", adults1);
	   session.setAttribute("children1", children1);
	   session.setAttribute("cot1", cot1);
	   session.setAttribute("roomType1", roomType1);
	   session.setAttribute("departDate", departDate);
	   session.setAttribute("arriveDate", arriveDate);
	   session.setAttribute("departDateStr", departDateString);
	   session.setAttribute("arriveDateStr", arriveDateString);

	   // room 2
	   if (rooms > 1)
	   {
		int adults2 = Integer.parseInt(request.getParameter("adults2"));
		int children2 = Integer.parseInt(request.getParameter("children2"));
		boolean cot2 = Boolean.parseBoolean(request.getParameter("cot2"));;
		String roomType2 = request.getParameter("roomtype2");;
		roomType2 = convertRoomType(roomType2);

		session.setAttribute("adults2", adults2);
		session.setAttribute("children2", children2);
		session.setAttribute("cot2", cot2);
		session.setAttribute("roomType2", roomType2);
	   }
	   // room 3
	   if (rooms > 2)
	   {
		int adults3 = Integer.parseInt(request.getParameter("adults3"));
		int children3 = Integer.parseInt(request.getParameter("children3"));
		boolean cot3 = Boolean.parseBoolean(request.getParameter("cot3"));;
		String roomType3 = request.getParameter("roomtype3");;
		roomType3 = convertRoomType(roomType3);

		session.setAttribute("adults3", adults3);
		session.setAttribute("children3", children3);
		session.setAttribute("cot3", cot3);
		session.setAttribute("roomType3", roomType3);
	   }
	   // room 4
	   if (rooms > 3)
	   {
		int adults4 = Integer.parseInt(request.getParameter("adults4"));
		int children4 = Integer.parseInt(request.getParameter("children4"));
		boolean cot4 = Boolean.parseBoolean(request.getParameter("cot4"));;
		String roomType4 = request.getParameter("roomtype4");;
		roomType4 = convertRoomType(roomType4);

		session.setAttribute("adults4", adults4);
		session.setAttribute("children4", children4);
		session.setAttribute("cot4", cot4);
		session.setAttribute("roomType4", roomType4);
	   }
	   //END GET FROM FORM
	   
	   // Get a list of room types requested and how many
	   HashMap<String, Integer> roomTypeRequestedHashMap = new HashMap<>();
	   HashMap<String, Integer> roomTypeAvailableHashMap = new HashMap<>();
	   for(int i=0; i < rooms; i++)
	   {
		String key = (String)session.getAttribute("roomType" + (i+1));
		if(roomTypeRequestedHashMap.containsKey(key))
		{
		   roomTypeRequestedHashMap.put(key, (roomTypeRequestedHashMap.get(key))+1);
		}
		else
		{
		   roomTypeRequestedHashMap.put(key, 1);
		}
	   }
	   System.out.println("Printing roomTypeRequestedHashMap:");
	   System.out.println(roomTypeRequestedHashMap);
	   
	   statement.execute("set schema 'hotelbooking';");
	   for(String key : roomTypeRequestedHashMap.keySet())
	   {
		int keyValue = roomTypeRequestedHashMap.get(key);
		
		// Get a list of empty rooms of correct type on stay dates requested
		String sqlStatement = "SELECT * FROM availableRoomsByDateAndType('"
			  + arriveDateString + "', '"
			  + departDateString + "','"
			  + key + "');";
		System.out.println(sqlStatement);
		// Run the query and retrieve the results
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		
		int count = 0;
		while (resultSet.next()) 
		{
		   count++;
		}
		roomTypeAvailableHashMap.put(key, count);
	   }
	   System.out.println(roomTypeAvailableHashMap);
	   System.out.println("End of Loop");

	   // Check enough rooms of correct types
	   boolean enoughRooms = true;
	   for(String key : roomTypeRequestedHashMap.keySet())
	   {
		if(roomTypeRequestedHashMap.get(key) > roomTypeAvailableHashMap.get(key))
		{
		   enoughRooms = false;
		}
	   }
	   
         session.setAttribute("rmHashmap", roomTypeRequestedHashMap);
	   System.out.println("Enough Rooms: " + enoughRooms);
	   
	   if(enoughRooms)
	   {
		RequestDispatcher view = request.getRequestDispatcher("booking.jsp");
		view.forward(request, response);
	   }
	   else
	   {
		// Get a list of empty rooms of any type on stay dates requested
		String sqlStatement = "SELECT * FROM availableRoomsAndTypeByDate('"
			  + arriveDateString + "', '"
			  + departDateString + "');";
		System.out.println(sqlStatement);
		// Run the query and retrieve the results
		ResultSet resultSet = statement.executeQuery(sqlStatement);
		int count_std_d = 0;
		int count_std_t = 0;
		int count_sup_d = 0;
		int count_sup_t = 0;
		while (resultSet.next()) 
		{
		   String r_class = resultSet.getString("r_class");
		   switch (r_class)
		   {
		   	case "std_d":
			   count_std_d++;
			   break;
		   	case "std_t":
			   count_std_t++;
			   break;
		   	case "sup_d":
			   count_sup_d++;
			   break;
		   	case "sup_t":
			   count_sup_t++;
			   break;
		   	default:
			   break;
		   }
		}
		session.setAttribute("std_d_available", count_std_d);
		session.setAttribute("std_t_available", count_std_t);
		session.setAttribute("sup_d_available", count_sup_d);
		session.setAttribute("sup_t_available", count_sup_t);

		
		// TODO respond with no rooms available of type selected
		System.out.println("Insufficient rooms available of type requested");
		
		// If no rooms available return to front page
            session.setAttribute("noRooms", true);
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
		view.forward(request, response);
	   }

	   // Close the database connection once finished
	   connection.close();
	}
   }

   // Method to convert roomtype to SQL format roomtype
   private String convertRoomType(String roomtype)
   {
	String roomType = roomtype;
	switch (roomType)
	{
	   case "stddouble":
		roomType = "std_d";
		break;
	   case "stdtwin":
		roomType = "std_t";
		break;
	   case "dlxdouble":
		roomType = "sup_d";
		break;
	   case "dlxtwin":
		roomType = "sup_t";
		break;
	   default:
		break;
	}
	return roomType;
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
	} catch (ClassNotFoundException | SQLException | ParseException ex)
	{
	   Logger.getLogger(indexServlet.class.getName()).log(Level.SEVERE, null, ex);
	} catch (Exception ex)
	{
	   System.out.println(ex.getMessage());
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
	} catch (ClassNotFoundException | SQLException | ParseException ex)
	{
	   Logger.getLogger(indexServlet.class.getName()).log(Level.SEVERE, null, ex);
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
