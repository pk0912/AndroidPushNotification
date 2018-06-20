package Webservices;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import DBConnector.DBInfo;
import beans.DeptListVO;
import beans.DeptVO;
import beans.GroupListVO;
import beans.GroupVO;
import beans.LoginBean;
import beans.NotificationBean;
import beans.StatusVO;

@Path("/AdminConfigWebService")
public class AdminConfigWebService {
	   
   	DeptListVO deptListVO=new DeptListVO();
   	DeptVO deptVO;
   	StatusVO statusVO=new StatusVO();
     
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;
     
    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() 
    {
        return "Demo service is ready!";
    }
 
    @GET
	@Path("/getDeptList")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String getDeptList()
	{
		DeptVO deptVO; 
		DeptListVO deptListVO=new DeptListVO(); 
		Connection con=DBInfo.getConn();
		String query="select * from department";
		String q="Select * from reg";
		ArrayList<DeptVO> deptlist= new ArrayList<DeptVO>();
		ArrayList<DeptVO> emplist= new ArrayList<DeptVO>();
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				deptVO=new DeptVO();
				deptVO.setDept_id(res.getString(1));
				deptVO.setDept_name(res.getString(2));
				deptVO.setSelected(false);
				deptlist.add(deptVO);
			}
			deptListVO.setDeptlist(deptlist);
			
			PreparedStatement ps1=con.prepareStatement(q);
			
			ResultSet res1=ps1.executeQuery();
			while(res1.next())
			{
				deptVO=new DeptVO();
				deptVO.setDept_id(res1.getString(1));
				deptVO.setDept_name(res1.getString(3));
				deptVO.setSelected(false);
				emplist.add(deptVO);
			}
			deptListVO.setEmplist(emplist);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(deptListVO);
		return responseJsonString;
	}
    
    @GET
    @Path("getGroup")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSampleDept() 
	{	
    	GroupVO groupVO; 
		GroupListVO groupListVO=new GroupListVO(); 
		Connection con=DBInfo.getConn();
		String query="select * from customgroup";
		ArrayList<GroupVO> grouplist= new ArrayList<GroupVO>();
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				groupVO=new GroupVO();
				groupVO.setGid(res.getString(1));
				groupVO.setGname(res.getString(2));
				grouplist.add(groupVO);
			}
		}
		catch(Exception e)
		{
			groupVO=new GroupVO();
			groupVO.setGid("No Group");
			groupVO.setGname("No Group");
			grouplist.add(groupVO);
			e.printStackTrace();
		}
		groupListVO.setGrouplist(grouplist);
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(groupListVO);
		return responseJsonString;
    }
    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }
    
    @POST
    @Path("savegroup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String postGroup(MultivaluedMap<String, String> groupParams) 
    {
    	ArrayList<DeptVO> deptList=new ArrayList<DeptVO>();
    	ArrayList<DeptVO> empList=new ArrayList<DeptVO>();
        String groupname=groupParams.getFirst("groupname");
    	String dept = groupParams.getFirst("deptList");
        int id=gen();
        ArrayList<String> list=new ArrayList<String>();
        Gson gson=new Gson();
        deptListVO=gson.fromJson(dept,DeptListVO.class);
        empList=deptListVO.getEmplist();
        deptList=deptListVO.getDeptlist();
        Connection con=DBInfo.getConn();
        String q1="select * from customgroup";
        try
		{
			PreparedStatement ps=con.prepareStatement(q1);
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				String gid=res.getString(1);
				list.add(gid);
				for (int i=0; i<list.size(); i++){
					   if(list.get(i).equalsIgnoreCase(""+id))
					   {
						   id=gen();
						   i=0;
					   }
					}
			}
		}
        catch(Exception e)
        {
        	e.printStackTrace();
        }
       String query=null;
        String q="Insert into customgroup values(?,?)";
        try
		{
			PreparedStatement ps=con.prepareStatement(q);
			ps.setString(1, ""+id);
			ps.setString(2, groupname);
			int i=ps.executeUpdate();
			if(i==1)
			{
				String query1="delete from groupdept where gname=?";
				PreparedStatement ps1=con.prepareStatement(query1);
				ps1.setString(1, groupname);
				int j=ps1.executeUpdate();
				
				query="Insert into groupdept values('";
				for (j = 0; j < deptList.size(); j++) {
		        	DeptVO department = deptList.get(j);
		        	
		             query=query+groupname+"','"+department.getDept_id()+"','0'),('";
		            
		        }
				
				for (j = 0; j < empList.size(); j++) {
		        	DeptVO department = empList.get(j);
		        	if(j<empList.size()-1)
		            {
		             query=query+groupname+"','0','"+department.getDept_id()+"'),('";
		            }
		        	else
		        	{
		        		query=query+groupname+"','0','"+department.getDept_id()+"')";
		        	}
		        }
				
				PreparedStatement ps2=con.prepareStatement(query);
				
				j=ps2.executeUpdate();
				
				if(j==1)
				{
				statusVO.setStatus(true);
				statusVO.setTitle("Thank you!!");
				statusVO.setMessage("Congratulates group saved succesfully.");
				statusVO.setUsertype("admin");
				}
			}
			else
			{
				statusVO.setStatus(false);
				statusVO.setTitle("message Failed");
				statusVO.setMessage("Group Already Present.");
			}
			
		}
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        System.out.println(query);

		Gson gson1 = new Gson();
		String responseJsonString = gson1.toJson(statusVO);
		System.out.println(responseJsonString);
		return responseJsonString;        
	}

    @POST
    @Path("updategroup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateGroup(MultivaluedMap<String, String> groupParams) 
    {
    	ArrayList<DeptVO> deptList=new ArrayList<DeptVO>();
    	ArrayList<DeptVO> empList=new ArrayList<DeptVO>();
        String oldgroup=groupParams.getFirst("oldgroup");
        String newgroup=groupParams.getFirst("newgroup");
    	String dept = groupParams.getFirst("deptList");
   
        Gson gson=new Gson();
        deptListVO=gson.fromJson(dept,DeptListVO.class);
        deptList=deptListVO.getDeptlist();
        empList=deptListVO.getEmplist();
        Connection con=DBInfo.getConn();
        String gid="";
        String q1="select * from customgroup where gname=?";
        try
		{
			PreparedStatement ps=con.prepareStatement(q1);
			ps.setString(1, oldgroup);
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				gid=res.getString(1);
			}
		}
        catch(Exception e)
        {
        	e.printStackTrace();
        }
       String query=null;
        String q="update customgroup set gname=? where gid=?";
        try
		{
			PreparedStatement ps=con.prepareStatement(q);
			ps.setString(1, newgroup);
			ps.setString(2, gid);
			int i=ps.executeUpdate();
			if(i==1)
			{
				String query1="delete from groupdept where gname in (?,?)";
				PreparedStatement ps1=con.prepareStatement(query1);
				ps1.setString(1, newgroup);
				ps1.setString(2, oldgroup);
				int j=ps1.executeUpdate();
				
				query="Insert into groupdept values('";
				for (j = 0; j < deptList.size(); j++) {
		        	DeptVO department = deptList.get(j);
		        	
		             query=query+newgroup+"','"+department.getDept_id()+"','0'),('";
		            
		        }
				
				for (j = 0; j < empList.size(); j++) {
		        	DeptVO department = empList.get(j);
		        	if(j<empList.size()-1)
		            {
		             query=query+newgroup+"','0','"+department.getDept_id()+"'),('";
		            }
		        	else
		        	{
		        		query=query+newgroup+"','0','"+department.getDept_id()+"')";
		        	}
		        }
				
				PreparedStatement ps2=con.prepareStatement(query);
				
				j=ps2.executeUpdate();
				
				if(j==1)
				{
				statusVO.setStatus(true);
				statusVO.setTitle("Thank you!!");
				statusVO.setMessage("Congratulates group saved succesfully.");
				statusVO.setUsertype("admin");
				}
			}
			else
			{
				statusVO.setStatus(false);
				statusVO.setTitle("message Failed");
				statusVO.setMessage("Group Already Present.");
			}
			
		}
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        System.out.println(query);

		Gson gson1 = new Gson();
		String responseJsonString = gson1.toJson(statusVO);
		System.out.println(responseJsonString);
		return responseJsonString;        
	}
    
    @GET
	@Path("/deleteGroup/{groupname}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deletegroup(@PathParam("groupname") String groupname)
	{
		StatusVO statusVO=new StatusVO();
		System.out.println("Group:"+groupname);
		Connection con=DBInfo.getConn();
		String query="delete from customgroup where gname=?";
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, groupname);
			int i=ps.executeUpdate();
			if(i==1)
			{
				String query1="delete from groupdept where gname=?";
				PreparedStatement ps1=con.prepareStatement(query1);
				ps1.setString(1, groupname);
				ps1.executeUpdate();
				
				statusVO.setStatus(true);
				statusVO.setTitle("Thank you!!");
				statusVO.setMessage("Congratulates "+ groupname+  " deleted succesfully.");
				statusVO.setUsertype("admin");
			}
			else
			{
				statusVO.setStatus(false);
				statusVO.setTitle("Group Creation Failed");
				statusVO.setMessage("Group not present.");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(statusVO);
		return responseJsonString;
	}
     
    @GET
	@Path("/saveGroupType/{groupname}")
	@Produces(MediaType.APPLICATION_JSON)
	public String savegrouptype(@PathParam("groupname") String groupname)
	{
		StatusVO statusVO=new StatusVO();
		System.out.println("Group:"+groupname);
		Connection con=DBInfo.getConn();
		int count=0;
		String query="select * from department";
		try
		{
			PreparedStatement ps=con.prepareStatement(query);	
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				count++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		count++;
		String query1="insert into department values(?,?)";
		try
		{
			PreparedStatement ps=con.prepareStatement(query1);
			ps.setString(1,""+ count);
			ps.setString(2, groupname);
			int i=ps.executeUpdate();
			if(i==1)
			{
				statusVO.setStatus(true);
				statusVO.setTitle("Thank you!!");
				statusVO.setMessage("Congratulates "+ groupname+  " saved succesfully.");
				statusVO.setUsertype("admin");
			}
			else
			{
				statusVO.setStatus(false);
				statusVO.setTitle("Group Creation Failed");
				statusVO.setMessage("Group present.");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(statusVO);
		return responseJsonString;
	}
       
    @GET
	@Path("/getDeptList/{groupname}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getdeptList(@PathParam("groupname") String groupname)
	{
    	DeptVO deptVO; 
		DeptListVO deptListVO=new DeptListVO(); 
		Connection con=DBInfo.getConn();
		ArrayList<String> dept=new ArrayList<String>();
		ArrayList<String> emp=new ArrayList<String>();
		String query="select * from groupdept where gname=?";
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, groupname);
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				if(!res.getString(2).equalsIgnoreCase("0"))
				{
					dept.add(res.getString(2));
				}
				if(!res.getString(3).equalsIgnoreCase("0"))
				{
					emp.add(res.getString(3));
				}
			}
			System.out.println(dept);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String query1="select * from department";
		String q="select * from reg";
		ArrayList<DeptVO> deptlist= new ArrayList<DeptVO>();
		ArrayList<DeptVO> emplist= new ArrayList<DeptVO>();
		try
		{
			PreparedStatement ps=con.prepareStatement(query1);
			
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				deptVO=new DeptVO();
				deptVO.setDept_id(res.getString(1));
				deptVO.setDept_name(res.getString(2));
				for(int i=0;i<dept.size();i++)
				{
					if(dept.get(i).equalsIgnoreCase(res.getString(1)))
					{
						deptVO.setSelected(true);
						break;
					}
					else
					{
						deptVO.setSelected(false);
					}
				}
				deptlist.add(deptVO);
			}
			
			PreparedStatement ps1=con.prepareStatement(q);
			
			ResultSet res1=ps1.executeQuery();
			while(res1.next())
			{
				deptVO=new DeptVO();
				deptVO.setDept_id(res1.getString(1));
				deptVO.setDept_name(res1.getString(3));
				for(int i=0;i<emp.size();i++)
				{
					if(emp.get(i).equalsIgnoreCase(res1.getString(1)))
					{
						deptVO.setSelected(true);
						break;
					}
					else
					{
						deptVO.setSelected(false);
					}
				}
				emplist.add(deptVO);
			}
			deptListVO.setEmplist(emplist);
			deptListVO.setDeptlist(deptlist);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(deptListVO);
		return responseJsonString;
	}
}
