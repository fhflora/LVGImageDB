

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String createdCode=(String)request.getSession().getAttribute("certCode");
		  
		  String account =request.getParameter("account");
		  String password=request.getParameter("password");
		  String type = request.getParameter("type");
		  String input=request.getParameter("certCode");
		  System.out.print(account);
		  System.out.print(password);
		  System.out.print(type);
		  System.out.print(input);

		  String rand = (String)request.getSession().getAttribute("rand"); 
		   
		  if(rand.equals(input)){ //验证码通过开始验证用户名密码
			  
		  
		  } else{ 
		  System.out.print("<script>alert('请输入正确的验证码！');location.href='index.jsp';</script>"); 
		  } 

	}

}
