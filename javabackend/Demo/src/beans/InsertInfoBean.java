package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;


import DBConnector.DBInfo;

public class InsertInfoBean
{
	public int insertInfo(String[] str)
	{
		int i=0,j=0,k=0,l=0,m=0,a=0;
		Connection con=DBInfo.getConn();
		String query1="insert into signin values(?,?,?,?)";
		String query2="insert into reg values(?,?,?,?,?)";
		String query3="delete from signin where emp_id=?";
		String query4="delete from reg where emp_id=?";
		String query5="insert into macstatus values(?,?)";
		String query6="delete from macstatus where emp_id=?";
		try
		{
		PreparedStatement ps1=con.prepareStatement(query1);
		ps1.setString(1, str[0]);
		ps1.setString(2, str[5]);
		ps1.setString(3, str[6]);
		ps1.setInt(4, 0);
		i=ps1.executeUpdate();
		PreparedStatement ps2=con.prepareStatement(query2);
		ps2.setString(1, str[0]);
		ps2.setString(2, str[1]);
		ps2.setString(3, str[3]);
		ps2.setString(4, str[2]);
		ps2.setString(5, str[4]);
		j=ps2.executeUpdate();
		PreparedStatement ps5=con.prepareStatement(query5);
		ps5.setString(1, str[0]);
		ps5.setString(2, "false");
		a=ps5.executeUpdate();
		PreparedStatement ps4=con.prepareStatement(query4);
		ps4.setString(1,"emp_id");
		l=ps4.executeUpdate();
		PreparedStatement ps3=con.prepareStatement(query3);
		ps3.setString(1,"emp_id");
		l=ps3.executeUpdate();
		PreparedStatement ps6=con.prepareStatement(query6);
		ps6.setString(1, "emp_id");
		l=ps6.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(i==0 || j==0 || a==0)
		return k;
		else
			return 1;
	}
}
