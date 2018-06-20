package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBConnector.DBInfo;

public class LoginBean 
{
private String emp_id,password;


public String getEmp_id() {
	return emp_id;
}

public void setEmp_id(String emp_id) {
	this.emp_id = emp_id;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}
public int checkLoginDetails()
{
	int flag=0;
	String usertype="";
	Connection con=DBInfo.getConn();
	String query="select * from signin where emp_id=? and pass=?";
	try
	{
	PreparedStatement ps=con.prepareStatement(query);
	ps.setString(1, emp_id);
	
	
	
	ps.setString(2, password);
	ResultSet res=ps.executeQuery();
	while(res.next())
	{
		flag=1;
		usertype=res.getString(3);
	}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	if(flag==1 && usertype.equalsIgnoreCase("admin"))
		return 1;
	else if(flag==1 && usertype.equalsIgnoreCase("employee"))
		return 2;
	
	else
		return 0;
		
	
	
}
}
