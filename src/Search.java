
import com.lvg.database.UserDB;
import com.lvg.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * 获取从jsp表单传来的搜索参数
		 */
		String userName=request.getParameter("userName");
	    String userType=request.getParameter("userType");
		String[] userRight=request.getParameterValues("userRight");
		String userState=request.getParameter("userState");
		
	    try {
			List<User> userList=UserDB.searchUser(userName, userType,userRight,userState);//获得搜索到的用户结果集
			RequestDispatcher rd = request.getRequestDispatcher("userList.jsp"); //重定向到UserList页面 
		    request.setAttribute("userList",userList);//将用户列表存入request中，发送给UserLIst页面
		    rd.forward(request,response);//开始跳转  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//获得搜索结果列表
	    
	    
	}
	

}
