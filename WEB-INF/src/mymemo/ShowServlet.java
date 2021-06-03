package mymemo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ShowServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException{
    	
    	int postId=Integer.parseInt(request.getParameter("id"));
    	//dogetメソッドで受け取ったデータを「postId」変数に代入
    	if(request.getAttribute("message")==null) {
    		request.setAttribute("message", "This is your post" + postId);
    	}
    	
    	String url = "jdbc:mysql://localhost/mymemo";
        String user = "root";
        String password = "chhagane";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String sql="SELECT * FROM posts WHERE id=?";
        
        try (	Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statment = connection.prepareStatement(sql);
                ) {

               statment.setInt(1, postId); //DBから指定のデータを取り出す
               ResultSet results = statment.executeQuery();

               while (results.next()) {

                   String id = results.getString("id");
                   request.setAttribute("id", id);

                   String title = results.getString("title");
                   request.setAttribute("title", title);

                   String content = results.getString("content").replaceAll("\n","<br>" );
                   request.setAttribute("content", content);
               }

           } catch (Exception e) {
               request.setAttribute("message", "Exception:" + e.getMessage());
           }
    	
    	String view = "/WEB-INF/view/post.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }
}