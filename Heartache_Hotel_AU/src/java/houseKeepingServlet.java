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
public class houseKeepingServlet extends HttpServlet {

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
    */
   protected void processRequest(HttpServletRequest request, 
	     HttpServletResponse response) throws ServletException, IOException, 
	     ClassNotFoundException, SQLException
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
	   
	   char r_status = request.getParameter("status").charAt(0);
	   
	   // create SQL statement 
	   String sqlStatement = "SELECT * FROM room " +
					 "WHERE r_status = '" + r_status + "' " +
					 "ORDER BY r_no;";
	   System.out.println(sqlStatement);
	   
	   // 
	   ResultSet resultSet = statement.executeQuery(sqlStatement);
	   
	   String roomList = "Rooms with status '" + r_status + "'<br>";
	   while(resultSet.next())
	   {
		int r_no = resultSet.getInt("r_no");
		roomList += r_no + "<br>";
	   }
	   out.println(roomList);
	   session2.setAttribute("roomsByStatus", roomList);

	   RequestDispatcher view
		     = request.getRequestDispatcher("housekeeping.jsp");
	   request.setAttribute("message", roomList);
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
	} catch (ClassNotFoundException | SQLException ex)
	{
	   Logger.getLogger(houseKeepingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
	} catch (ClassNotFoundException | SQLException ex)
	{
	   Logger.getLogger(houseKeepingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
