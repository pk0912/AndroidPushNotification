package Webservices;

import java.util.ArrayList;
import java.util.Iterator;

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
import beans.DeptListVO;
import beans.DeptVO;
import beans.NotificationBean;
import beans.StatusVO;

@Path("/DepartmentNotify")
public class DepartmentNotifyWebService 
{	 
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
	    public String postDept(MultivaluedMap<String, String> personParams) 
	    {
			        ArrayList<DeptVO> deptList=new ArrayList<DeptVO>(); 
			        String message = personParams.getFirst(MESSAGE);
			        String dept = personParams.getFirst(DeptList);
			        
			        Gson gson=new Gson();
			        deptListVO=gson.fromJson(dept,DeptListVO.class);
			        deptList=deptListVO.getDeptlist();
			        
			        String query="select dev_id from device d,reg r where d.emp_id=r.emp_id and r.dept_id in ('";
			        for (int i = 0; i < deptList.size(); i++) {
			        	DeptVO department = deptList.get(i);
			        	if(i<deptList.size()-1)
	                    {
	                     query=query+department.getDept_id()+"','";
	                    }
			        	else
			        	{
			        		query=query+department.getDept_id()+"')";
			        	}
			        }
			        
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
