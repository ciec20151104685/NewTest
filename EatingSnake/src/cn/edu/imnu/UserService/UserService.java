package cn.edu.imnu.UserService;

import cn.edu.imnu.User.User;

public interface UserService {
	User selectUser(String usernam,String password);
	void insertUser(User user);
	void deleteUser(String id);
	void updataUser(User user);

}
