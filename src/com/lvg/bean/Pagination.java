 package com.lvg.bean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import com.lvg.database.GetConnection;
import com.lvg.entity.User;
public class Pagination extends GetConnection {
    private final int PAGEROW = 10;//每页显示的行数 
	private int countRow;//总行数
	private int countPage;//总页数
	private int currentlyPage;//当前第几页
	/**
	 * 得到总页数
	 * @return
	 */
	public int getCountPage() {
		return countPage;
	}
	
	/**
	 * 设置总页数
	 * @param countPage
	 */
	public void setCountPage() {
		//通过总行数设置总页数
		if (this.countRow % this.PAGEROW == 0) {//如果总行数除以每页显示的行数余数为零时，总页数等于它们的商
			this.countPage = this.countRow / this.PAGEROW;
		} else {//否则，总页数等于它们的商加1 
			this.countPage = this.countRow / this.PAGEROW + 1;
		}
	}
	
	/**
	 * 得到总行数
	 * @return
	 */
	public int getCountRow() {
		return countRow;
	}
	/**
	 * 设置总行数
	 * @param countRow
	 */
	public void setCountRow() {
		//通过聚合函数查询 TBL_BOOK 表中一共有多少条数据，并把值存储到 countRow 中
		String sql = "SELECT COUNT(*) FROM TBL_BOOK";
		try {
			PreparedStatement ps = super.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.countRow = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到当前页数
	 * @return
	 */
	public int getCurrentlyPage() {
		return currentlyPage;
	}
	/**
	 * 设置当前页数
	 * @param currentlyPage
	 */
	public void setCurrentlyPage(int currentlyPage) {
		this.currentlyPage = currentlyPage;
	}
	
	/**
	* 日期转换成字符串
	* @param date
	* @return str
	*/
	public static String DateToStr(Date date) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String str = format.format(date);
	   return str;
	} 

	/**
	 * 分页查询
	 * @param page 当前页数
	 * @return
	 */
	public List<User> userQuery(int page){
		List<User> userList =new ArrayList<User>();  //存储用户的列表
		int count=(page-1)*this.PAGEROW;  //查询记录的开始计数
		String sql = "SELECT top("+this.PAGEROW+") * FROM TBL_BOOK WHERE book_id NOT IN"
				     + " (SELECT TOP("+count+") book_id FROM TBL_BOOK)";  //从剩计数点开始查询10条用户记录
		
		try {
			PreparedStatement ps = super.getConn().prepareStatement(sql);
			//预编译 SQL 指令并把预编译好的 SQL 存储在 PreparedStatement 对象中
			ResultSet rs = ps.executeQuery();
			//执行预编译好的 SQL 指令，并把获得的查询结果集存储在 ResultSet 对象中
			while(rs.next()){
				User user=new User();
				user.setUserName(rs.getString("UerName"));
				user.setRealName(rs.getString("RealName"));
				user.setUserType(rs.getByte("Type"));
				user.setTel(rs.getString("Tel"));
				user.setEmail(rs.getString("Email"));
				user.setPermission(rs.getByte("Permission"));
				String strDate=Pagination.DateToStr(rs.getDate("Date"));//将从数据库中获取的日期从Date类型转换为String类型
				user.setLastTime(strDate);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	} 
		
	
}
