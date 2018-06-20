package Webservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import beans.NotificationBean;
import beans.StatusVO;

@Path("GroupNotifyWebService")
public class GroupListWebService {
	@GET
	@Path("/getGroupList")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String getDeptList()
	{
		DeptVO deptVO; 
		DeptListVO deptListVO=new DeptListVO(); 
		Connection con=DBInfo.getConn();
		String query="select * from customgroup";
		ArrayList<DeptVO> deptlist= new ArrayList<DeptVO>();
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(deptListVO);
		return responseJsonString;
	}
	private final static String MESSAGE = "message";
    private final static String DeptList = "dept";
    
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
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSampleDept() 
    {
        
    	deptVO=new DeptVO();
    	deptVO.setDept_id("1");
    	deptVO.setDept_name("Sample");
        System.out.println("Returning sample person: " + deptVO.getDept_id() + " " + deptVO.getDept_name());
        Gson gson = new Gson();
		String responseJsonString = gson.toJson(deptVO);
		return responseJsonString;
    }
         
    // Use data from the client source to create a new Person object, returned in JSON format.  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String postGroup(MultivaluedMap<String, String> personParams) 
    {
		        ArrayList<DeptVO> deptList=new ArrayList<DeptVO>(); 
		        String message = personParams.getFirst(MESSAGE);
		        String dept = personParams.getFirst(DeptList);
		        
		        Gson gson=new Gson();
		        deptListVO=gson.fromJson(dept,DeptListVO.class);
		        deptList=deptListVO.getDeptlist();
		        ArrayList<String> emp=new ArrayList<>();
		        
		        String query="select dev_id from device where emp_id in ('";
		        
		        String query1="select distinct(r.emp_id) from reg r,groupdept g where (r.emp_id=g.empid or r.dept_id=g.deptid) and gname in ('";
		        
		        for (int i = 0; i < deptList.size(); i++) {
		        	DeptVO department = deptList.get(i);
		        	if(i<deptList.size()-1)
                    {
                     query1=query1+department.getDept_name()+"','";
                    }
		        	else
		        	{
		        		query1=query1+department.getDept_name()+"')";
		        	}
		        }
		        
		        System.out.println(query1);
		        Connection con=DBInfo.getConn();
		        try
		        {
		        	PreparedStatement ps=con.prepareStatement(query1);
					
					ResultSet res=ps.executeQuery();
					while(res.next())
					{
						emp.add(res.getString(1));
					}
		        }
		        catch(Exception e)
		        {
		        	e.printStackTrace();
		        }
		        
		        try
		        {
		        	query1="select m.emp_id from macstatus m where status='true' and m.emp_id in('";
		        	for (int i = 0; i < emp.size(); i++) {
			       
			        	if(i<emp.size()-1)
	                    {
	                     query1=query1+emp.get(i)+"','";
	                    }
			        	else
			        	{
			        		query1=query1+emp.get(i)+"')";
			        	}
			        }
			        System.out.println(query1);
		        	
		        	PreparedStatement ps=con.prepareStatement(query1);
					
					ResultSet res=ps.executeQuery();
					while(res.next())
					{
						query=query+res.getString(1)+"','";
					}
		        }
		        catch(Exception e)
		        {
		        	e.printStackTrace();
		        }
		        query=query+"-1')";
		        System.out.println(query);
		        
		        
				int i=NotificationBean.sendNotification(message,query);
				if(i==1)
				{
					statusVO.setStatus(true);
					statusVO.setTitle("Thank you!!");
					statusVO.setMessage("Congratulates message delivered succesfully.");
					statusVO.setUsertype("admin");
				}
				
				else
				{
					statusVO.setStatus(false);
					statusVO.setTitle("message Failed");
					statusVO.setMessage("No Device Registered.");
				}
				
				Gson gson1 = new Gson();
				String responseJsonString = gson1.toJson(statusVO);
				System.out.println(responseJsonString);
				return responseJsonString;
		        
	    }

}