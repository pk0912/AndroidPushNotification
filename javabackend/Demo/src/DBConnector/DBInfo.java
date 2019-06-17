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
	 //con=DriverManager.getConnection("jdbc:mysql://1ipaddress/mydatabase","user******","*********");
		 con=DriverManager.getConnection("jdbc:mysql://ipaddress/mydatabase","user******","*********");
	 }
	 catch(Exception e)
	 {
	 e.printStackTrace();
	 }
   return con;
  }
}
