package cn.edu.imnu.StartBody;
import java.awt.Font;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.edu.imnu.User.User;
import cn.edu.imnu.UserService.UserService;
import cn.edu.imnu.UserServiceimpl.UserServiceImpl;
import cn.edu.imnu.Yard.Yard;

/**
 * Session Bean implementation class test
 */

public class StartBody extends JFrame {

	private static int count=0;

	private static JButton bt1;//登陆按钮

	private static JButton bt2;//忘记密码按钮

	private static JLabel jl_1;//登录的版面

	private static JFrame jf_1;//登陆的框架

    private static JTextField jtext1;//用户名

    private static JPasswordField jtext2;//密码

    private static JLabel jl_admin;

    private static JLabel jl_password;

    public StartBody (){//初始化登陆界面

    	Font font =new Font("黑体", Font.PLAIN, 20);//设置字体

	    jf_1=new JFrame("登陆界面");

		jf_1.setSize(450, 400);

		//给登陆界面添加背景图片

		ImageIcon bgim = new ImageIcon("E:\\docker.png") ;//背景图案

		bgim.setImage(bgim.getImage().

		 getScaledInstance(bgim.getIconWidth(),

		 bgim.getIconHeight(), 

		Image.SCALE_DEFAULT));  

		jl_1=new JLabel();
		
		jl_1.setIcon(bgim);
		
		jl_admin=new JLabel("用户名");
		
		
		jl_admin.setBounds(20, 50, 60, 50);
		
		jl_admin.setFont(font);
		
		jl_password=new JLabel("密码");
		
		jl_password.setBounds(20, 120, 60, 50);
		
		jl_password.setFont(font);

		bt1=new JButton("登陆");         //更改成loginButton

		bt1.setBounds(90, 250, 100, 50);

		bt1.setFont(font);

 

		bt2=new JButton("注册");

		bt2.setBounds(250, 250, 100, 50);

		bt2.setFont(font);

 

		//加入文本框

		jtext1=new JTextField("");

		jtext1.setBounds(150, 50, 250, 50);

		jtext1.setFont(font);

		

		jtext2=new JPasswordField("");//密码输入框

		jtext2.setBounds(150, 120, 250, 50);

		jtext2.setFont(font);

		

		jl_1.add(jtext1);

		jl_1.add(jtext2);

		

		jl_1.add(jl_admin);

		jl_1.add(jl_password);

		jl_1.add(bt1);

		jl_1.add(bt2);

		

		jf_1.add(jl_1);

		jf_1.setVisible(true);

		jf_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jf_1.setLocation(300,400);
	
	}

	public static void main(String[] args) {

		//初始化登陆界面

		 

		final StartBody hl =new StartBody();


		//登陆点击事件

		ActionListener bt1_ls=new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				String admin=jtext1.getText();
				char[] password1=jtext2.getPassword();
				
				String password= String.valueOf(password1);
				
				UserService userservice = new UserServiceImpl();
				userservice.selectUser(admin, password);
				User user = userservice.selectUser(admin,password);
				if(user!=null && user.getId()!=null)
				{
					Yard ml=new Yard();//为跳转的界面

					hl.jf_1.dispose();//销毁当前界面

				}//成功跳转
				else
				{
					System.out.println("账户名或密码错误");
					count++;
					if(count==3){

					hl.jf_1.dispose();
				                 }									
				}
			}
		};

		bt1.addActionListener(bt1_ls);
		
		//退出事件的处理

		ActionListener bt2_ls=new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				// TODO Auto-generated method stub

//				System.exit(0);//终止当前程序
				
				User user = new User();
				
				String admin=jtext1.getText();
				char[] password1=jtext2.getPassword();
				
				String password= String.valueOf(password1);
				user.setPassword(password);
				
				user.setUser_name(admin);
				UserService userservice = new UserServiceImpl();
				userservice.insertUser(user);
			}

		};

        bt2.addActionListener(bt2_ls);		

     
	}
}