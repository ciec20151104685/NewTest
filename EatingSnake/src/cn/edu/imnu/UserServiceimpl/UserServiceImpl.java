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
		Connection con = DBUtil.getConnection();//��ȡ���ݿ�����
		String sql = "select * from user where user_name=? and password=?";
		ResultSet rs=null;//�����
		PreparedStatement pstmt = null;//��������
		User user = new User();
		try {
			pstmt = con.prepareStatement(sql);//Ԥ����SQL
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();//ִ�в�ѯ
			while(rs.next()) {
				user.setId(rs.getInt("id"));
				user.setImage(rs.getString("image"));
				user.setPassword(rs.getString("password"));
				user.setUser_name(rs.getString("user_name"));
				//��User���ò���
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeJDBC(rs, pstmt, con);//�ر���Դ
		}
		return user;
	}

	@Override
	public void insertUser(User user) {
		// TODO Auto-generated method stub

		Connection con = DBUtil.getConnection();//��ȡ���ݿ�����
		String sql = "INSERT INTO user (id,image,password,user_name) VALUES (default,?,?,?)";
		ResultSet rs=null;//�����
		PreparedStatement pstmt = null;//��������
		try {
			pstmt = con.prepareStatement(sql);//Ԥ����SQL
			pstmt.setString(1, user.getImage());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getUser_name());
			
			pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeJDBC(rs, pstmt, con);//�ر���Դ
		}
	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		Connection con = DBUtil.getConnection();//��ȡ���ݿ�����
		String sql = "DELETE FROM user WHERE id = ?";
		ResultSet rs=null;//�����
		PreparedStatement pstmt = null;//��������
		try {
			pstmt = con.prepareStatement(sql);//Ԥ����SQL
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();//ִ�в�ѯ
			pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeJDBC(rs, pstmt, con);//�ر���Դ
		}
		
		
	}

	@Override
	public void updataUser(User user) {
		// TODO Auto-generated method stub
		Connection con = DBUtil.getConnection();//��ȡ���ݿ�����
		String sql = "UPDATE User SET score = ?";
		ResultSet rs=null;//�����
		PreparedStatement pstmt = null;//��������
		try {
			pstmt = con.prepareStatement(sql);//Ԥ����SQL
			pstmt.setString(1, score);
			rs = pstmt.executeQuery();//ִ�в�ѯ
			pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.closeJDBC(rs, pstmt, con);//�ر���Դ
		}
		
		
		
	}
}
