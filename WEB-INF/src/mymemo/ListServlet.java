package mymemo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ListServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException{
    	
    	if(request.getAttribute("message")==null) {
    		request.setAttribute("message", "hello paiza memo");
    	}
    	
    	String url="jdbc:mysql://localhost/mymemo";
    	String user="root";
    	String password="chhagane";
    	
    	try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	String sql="SELECT * FROM posts";
    	
    	try (	Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statment = connection.prepareStatement(sql);
                ResultSet results = statment.executeQuery()) {

               ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();

               while (results.next()) {
                   HashMap<String, String> columns = new HashMap<String, String>();

                   String id = results.getString("id");
                   columns.put("id", id);

                   String title = results.getString("title");
                   columns.put("title", title);

                   String content = results.getString("content");
                   columns.put("content", content);

                   rows.add(columns);
               }

               request.setAttribute("rows", rows);

           } catch (Exception e) {
               request.setAttribute("message", "Exception:" + e.getMessage());
           }
    			
    	
    	String view = "/WEB-INF/view/list.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }
}