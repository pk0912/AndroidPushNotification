package FileData;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.InsertInfoBean;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Servlet implementation class FileRead
 */
public class FileRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileRead() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int flag=0;
		List<String> l2;
		List<List<String>> l1 = new ArrayList<List<String>>();
		PrintWriter pw=response.getWriter();
		try 
		{
			String file = request.getParameter("filename");

			FileInputStream fstream = new FileInputStream("D:/javaws/Demo/src/"+file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			while ((strLine = br.readLine()) != null) {
			StringTokenizer st1 = new StringTokenizer(strLine, "\n");
			
			
			l2=new ArrayList<String>();
			
			while (st1.hasMoreTokens()) 
			{
				String tok=st1.nextToken(",");
				l2.add(tok);
			}
			l1.add(l2);
			
			
			
	            
		}
			br.close();
			//pw.print(l1);
			Iterator it=l1.iterator();
			 while (it.hasNext())
			 {
		            List<String> l3;
		            l3=(ArrayList)it.next();
		            String[] str=new String[l3.size()];
		            str=l3.toArray(str);
		            //System.out.println(str[str.length-1]);
		            InsertInfoBean iib= new InsertInfoBean();
		            
		           flag= iib.insertInfo(str);
		           
		     }
			
			 if(flag==1)
	           {
				  RequestDispatcher dispatcher = request.getRequestDispatcher("/bootstrap/lalert2.js");
					dispatcher.include(request, response); 
					
				  RequestDispatcher dispatcher1 = request.getRequestDispatcher("/adminpanel.jsp");

					dispatcher1.include(request, response); 
	        	   
	           }
	           else
	           {
	        	   RequestDispatcher dispatcher = request.getRequestDispatcher("/bootstrap/lalert1.js");
				    dispatcher.include(request, response); 
				
			       RequestDispatcher dispatcher1 = request.getRequestDispatcher("/adminpanel.jsp");

				    dispatcher1.include(request, response); 
	           }
			}
		catch(Exception e)
		{
			RequestDispatcher dispatcher2 = request.getRequestDispatcher("/bootstrap/lalert1.js");
		    dispatcher2.include(request, response); 
		
	       RequestDispatcher dispatcher3 = request.getRequestDispatcher("/adminpanel.jsp");

		    dispatcher3.include(request, response); 

			e.printStackTrace();
		}
	}

}
