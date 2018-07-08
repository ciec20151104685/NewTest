package cn.edu.imnu.UserServiceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.edu.imnu.DBUtil.DBUtil;
import cn.edu.imnu.User.User;
import cn.edu.imnu.UserService.UserService;

public class UserServiceImpl implements UserService{
	@Override
	public User selectUser(String username,String password) {
		// TODO Auto-generated method stub
		Connection con = DBUtil.getConnection();//获取数据库连接
		String sql = "select * from user where user_name=? and password=?";
		ResultSet rs=null;//结果集
		PreparedStatement pstmt = null;//参数设置
		User user = new User();
		try {
			pstmt = con.prepareStatement(sql);//预加载SQL
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();//执行查询
			while(rs.next()) {
				user.setId(rs.getInt("id"));
				user.setImage(rs.getString("image"));
				user.setPassword(rs.getString("password"));
				user.setUser_name(rs.getString("user_name"));
				//给User设置参数
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeJDBC(rs, pstmt, con);//关闭资源
		}
		return user;
	}

	@Override
	public void insertUser(User user) {
		// TODO Auto-generated method stub

		Connection con = DBUtil.getConnection();//获取数据库连接
		String sql = "INSERT INTO user (id,image,password,user_name) VALUES (default,0,?,?)";
		ResultSet rs=null;//结果集
		PreparedStatement pstmt = null;//参数设置
		try {
			pstmt = con.prepareStatement(sql);//预加载SQL
//			pstmt.setString(1, user.getImage());
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getUser_name());
			
			pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeJDBC(rs, pstmt, con);//关闭资源
		}
	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		Connection con = DBUtil.getConnection();//获取数据库连接
		String sql = "DELETE FROM user WHERE id = ?";
		ResultSet rs=null;//结果集
		PreparedStatement pstmt = null;//参数设置
		try {
			pstmt = con.prepareStatement(sql);//预加载SQL
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();//执行查询
			pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeJDBC(rs, pstmt, con);//关闭资源
		}
		
		
	}

	@Override
	public void updataUser(User user) {
		// TODO Auto-generated method stub
		Connection con = DBUtil.getConnection();//获取数据库连接
		String sql = "UPDATE User SET score = ?";
		ResultSet rs=null;//结果集
		PreparedStatement pstmt = null;//参数设置
		try {
			pstmt = con.prepareStatement(sql);//预加载SQL
			String score = null;
			pstmt.setString(1, score);
			rs = pstmt.executeQuery();//执行查询
			pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeJDBC(rs, pstmt, con);//关闭资源
		}
		
		
		
	}
}
