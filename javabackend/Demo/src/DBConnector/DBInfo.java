package DBConnector;

import java.sql.*;
public class DBInfo
{
   
   static
   {
	    try
	    {
	    Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
		e.printStackTrace();
		}
   }
  public static Connection getConn()
  {
     Connection con=null;
	 try
	 {
	 //con=DriverManager.getConnection("jdbc:mysql://118.67.248.202/mydatabase","shiveshsky","8877303051");
		 con=DriverManager.getConnection("jdbc:mysql://118.67.248.202/mydatabase","shiveshsky","8877303051");
	 }
	 catch(Exception e)
	 {
	 e.printStackTrace();
	 }
   return con;
  }
}
