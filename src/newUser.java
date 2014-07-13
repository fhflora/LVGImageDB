
import com.lvg.database.UserDB;
import com.lvg.entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class newUser
 */
@WebServlet("/newUser")
public class newUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public newUser() {
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
		String userName=request.getParameter("userName");
		  String realName=request.getParameter("realName");
		  String password=request.getParameter("password");
		  String repassword=request.getParameter("rePassword");//两次密码是否一致在jsp页面通过js判断。
		  String userType=request.getParameter("userType");
		  String userRight[]=request.getParameterValues("userRight");
		  String userTel=request.getParameter("userTel");
		  String userEmail=request.getParameter("userEmail");
		  String remark=request.getParameter("remark");
		  UserDB userDB=new UserDB();
		  boolean isAdded=userDB.addUser(userName, realName, password, userType, userRight, userTel, userEmail, remark);
			 if (isAdded) {//插入成功，提示？跳转？
				 
			 }
		
		  
		 

	}

}
