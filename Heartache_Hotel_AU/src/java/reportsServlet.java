/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
 * @author Stephen
 */
public class reportsServlet extends HttpServlet {

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
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	     throws ServletException, IOException, ClassNotFoundException, SQLException, ParseException
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

	   statement.execute("set schema 'hotelbooking';");
	   
	   
	   // Get date from form
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   String dateString = request.getParameter("fdate");
	   Date date = sdf.parse(dateString);
	   
	   // Set up calendar so we can iterate date over the week
	   Calendar c = Calendar.getInstance();
	   c.setTime(date);
	   String weekBeginning;

	   HashMap<Integer, Integer> roomOccupancy = new HashMap<>();
	   HashMap<Integer, Integer> roomRates = new HashMap<>();
	   ResultSet resultSet;
	   String sqlStatement;
	   for(int i = 0; i < 7; i++)
	   {
		weekBeginning = sdf.format(c.getTime());
		// create SQL statement
		sqlStatement = "SELECT * FROM weeklyReport('" + weekBeginning +"')";
		System.out.println(sqlStatement);

		resultSet = statement.executeQuery(sqlStatement);

		while(resultSet.next())
		{
		   int r_no = resultSet.getInt("r_no");
		   int price = resultSet.getInt("price");
		   if(roomOccupancy.keySet().contains(r_no))
		   {
			roomOccupancy.put(r_no, roomOccupancy.get(r_no)+1);
		   }
		   else
		   {
			roomOccupancy.put(r_no, 1);
			roomRates.put(r_no, price);
		   }
		}
		c.add(Calendar.DATE, 1);
	   }
	   
	   HashMap<Integer, Integer> roomIncome = new HashMap<>();
	   for(Integer r_no : roomOccupancy.keySet())
	   {
		roomIncome.put(r_no, roomOccupancy.get(r_no)*roomRates.get(r_no));
	   }
	   System.out.println(roomOccupancy);
	   System.out.println(roomRates);
	   System.out.println(roomIncome);
	   session2.setAttribute("roomsReport", roomOccupancy);
	   
	   ArrayList<Integer> roomNumbers = new ArrayList<>(roomOccupancy.keySet());
	   Collections.sort(roomNumbers);
	   
	   String report = "7 day report for week beginning: "+ dateString 
		           +"<br><br><table><tr><th>Room No</th>"
		           + "<th>Nights Occupied</th>"
			     + "<th>Income £</th></tr>";

   	   double income = 0;
	   DecimalFormat df = new DecimalFormat("0.00");

	   for(Integer r_no : roomNumbers)
	   {
		report += "<tr><td>" + r_no + "</td><td>" + roomOccupancy.get(r_no) 
			 + "</td><td>" + roomIncome.get(r_no) + ".00</td></tr>";
		income += roomIncome.get(r_no);
	   }
	   report += "</table><br><br> Number of unoccupied rooms: " 
		     + (32-roomNumbers.size()) + "<br>Total room income for week: £" 
		     + df.format(income);
	   System.out.println(report);
	   RequestDispatcher view
		     = request.getRequestDispatcher("reports.jsp");
	   request.setAttribute("message", report);
	   view.forward(request, response);
	   // Close the database connection once finished
	   connection.close();
 
	}
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
	   Logger.getLogger(reportsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
	   Logger.getLogger(reportsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
