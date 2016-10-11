package cn.missbe.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.missbe.model.User;
import cn.missbe.service.UserServiceI;
import cn.missbe.util.DBUtil;

public class UserServiceImpl implements UserServiceI {

	public User getUserBean(String name, String password) throws SQLException {
		// TODO Auto-generated method stub
//		System.out.println("getUserBean");
		
		Connection conn=DBUtil.getConnection();
		String sql="SELECT * FROM admin_inf admin WHERE admin.username=? and admin.userpass=?";
		PreparedStatement statement=conn.prepareStatement(sql,
				                   ResultSet.TYPE_SCROLL_SENSITIVE,
				                   ResultSet.CONCUR_UPDATABLE);
		statement.setString(1, name);
		statement.setString(2, password);
		
		ResultSet rs=statement.executeQuery();
		User user=new User();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		boolean flag=false;///判断是否有此用户 false即无
		if(rs.next()){			
			user.setUsername(rs.getString("username"));
			user.setUserpass(rs.getString("userpass"));		
			
			user.setUpdate_time(rs.getString("update_time"));
//			System.out.println("TIME:"+rs.getString("update_time"));
//			System.out.println(sdf.format(new Date()));///TEST
			rs.updateString("update_time",sdf.format(new Date()));	
			
			rs.updateRow();
			flag=true;
		}		
		return flag ? user : null;
	}

	public boolean modifyUserPass(String pass) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn=DBUtil.getConnection();
//		String sql="UPDATE admin_inf SET userpass=? WHERE username=? and userpass=?";	
		String sql="UPDATE admin_inf SET userpass=?";	
//		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("admin");
		PreparedStatement statement=null;
		int number=0;
//		if(null != user){
		    statement=conn.prepareStatement(sql);
			statement.setString(1, pass);
//			statement.setString(2, user.getUsername());
//			statement.setString(3, user.getUserpass());	
			number= statement.executeUpdate();
			
//			System.out.println("获取Session内容:"+user.getUsername()+":"+user.getUserpass());
//		}
		
     	return  number>0 ? true : false;	
	
	}

}
