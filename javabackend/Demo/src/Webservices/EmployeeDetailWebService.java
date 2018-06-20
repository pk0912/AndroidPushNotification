package Webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.google.gson.Gson;

import DBConnector.DBInfo;
import beans.DeptListVO;
import beans.DeptVO;
import beans.EmployeeDetailVO;

@Path("/EmployeeDetailWS")
public class EmployeeDetailWebService {
	
	int flag=0;
	String name;
	@GET
	@Path("/getid/{data}")

	@Produces(MediaType.APPLICATION_JSON)
	public String getHTMLUniversity(@PathParam("data") String data){
		EmployeeDetailVO ob=new EmployeeDetailVO();
		ArrayList<EmployeeDetailVO> custarr=new ArrayList<EmployeeDetailVO>();
		
		Connection con=DBInfo.getConn();
		String query="select r.emp_id,r.emp_name,r.phone,r.email,d.dept_name,g.gname from reg r, department d,groupdept g where r.emp_id=? and r.dept_id=d.dept_id and (g.deptid=r.dept_id or g.empid=?)";
		try
		{
		ArrayList<String> gname=new ArrayList<>();
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, data);
		ps.setString(2, data);
		ResultSet res=ps.executeQuery();
		while(res.next())
		{
			ob.setId(res.getString(1));
			ob.setEmpname(res.getString(2));
			ob.setPhone(res.getString(3));
			ob.setEmail(res.getString(4));
			ob.setDeptname(res.getString(5));
			gname.add(res.getString(6));
			//custarr.add(ob);
			
		}
		ob.setGrpname(gname);
			//cuarr.setArrcu(custarr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(ob);
		return responseJsonString;
	}
	
	
	@GET
	@Path("/updatedata/{email}/{phone}/{emp_id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String updatedata(@PathParam("email") String email,@PathParam("phone") String phone,@PathParam("emp_id") String emp_id)
	{
		EmployeeDetailVO ob=new EmployeeDetailVO();
		ArrayList<EmployeeDetailVO> custarr=new ArrayList<EmployeeDetailVO>();
		
		Connection con=DBInfo.getConn();
		String query1="UPDATE reg SET email=?,phone=? WHERE emp_id=?";
		ArrayList<DeptVO> deptlist= new ArrayList<DeptVO>();
		try
		{
			PreparedStatement ps=con.prepareStatement(query1);
			ps.setString(1, email);
			ps.setString(2, phone);
			ps.setString(3, emp_id);
			
			int res=ps.executeUpdate();
			if(res==1)
			{
					ob.setMessage("Update Successfull ");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(ob);
		return responseJsonString;
	}
	
}
