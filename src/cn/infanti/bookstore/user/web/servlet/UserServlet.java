package cn.infanti.bookstore.user.web.servlet;

import cn.infanti.bookstore.cart.domain.Cart;
import cn.infanti.bookstore.user.domain.User;
import cn.infanti.bookstore.user.service.UserException;
import cn.infanti.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
    private UserService userService = new UserService();
    public String quit (HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, SQLException {
    	/**
    	 * 注销session的内容
    	 * 转发到index.jsp页面
    	 */
    	request.getSession().invalidate();
    	return "f:/index.jsp";
    }
    
    
    public String login (HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, SQLException {
    	/**
    	 * 1、封装登录数据
    	 * 2、调用service方法查询用户名是否存在（密码）
    	 * 3、判断用户是否激活
    	 * 4、和激活就转发到main.jsp 并将用户信息保存到seesion中
    	 * 5、如果用户不存在保存错误信息 到登录界面
    	 */
    	User user = CommonUtils.toBean(request.getParameterMap(), User.class);
    	try {
			User form = userService.login(user);
			request.getSession().setAttribute("session_user", form);
			/**
			 * 向session中添加一辆车
			 */
			request.getSession().setAttribute("cart", new Cart());
			return "r:/index.jsp";
		} catch (UserException e) {
			request.setAttribute("msg",e.getMessage());
			request.setAttribute("form", user);
			return "f:/jsps/user/login.jsp";
		}
    	
    }
    
    public String active (HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, SQLException {
    	/*
		 * 1. 获取参数激活码
		 * 2. 调用service方法完成激活
		 *   > 保存异常信息到request域，转发到msg.jsp
		 * 3. 保存成功信息到request域，转发到msg.jsp
		 */
		String code = request.getParameter("code");
		try {
			userService.active(code);
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}
		request.setAttribute("msg", "恭喜，您激活成功了！请马上登录！");
		return "f:/jsps/msg.jsp";
    }
    
    public String regist (HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, SQLException {
        /**
         * 获取表单数据 
         * 封装到 User对象中
         * 保存user 信息到request域中
         * 校验 1 用户名是否存在 2 邮箱是否存在 
         * 插入到数据库中     
         *     
         * 调用userService注册方法
         * 
         * 转发到登录界面
         */
    	User form = CommonUtils.toBean(request.getParameterMap(), User.class);
    	form.setUid(CommonUtils.uuid());
    	form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
    	/*
		 * 输入校验
		 * 1. 创建一个Map，用来封装错误信息，其中key为表单字段名称，值为错误信息
		 */
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 2. 获取form中的username、password、email进行校验
		 */
		String username = form.getUsername();
		if(username == null || username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空！");
		} else if(username.length() < 3 || username.length() > 10) {
			errors.put("username", "用户名长度必须在3~10之间！");
		}
		
		String password = form.getPassword();
		if(password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空！");
		} else if(password.length() < 3 || password.length() > 10) {
			errors.put("password", "密码长度必须在3~10之间！");
		}
		
		String email = form.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("\\w+@\\w+\\.\\w+")) {
			errors.put("email", "Email格式错误！");
		}
		/*
		 * 3. 判断是否存在错误信息
		 */
		if(errors.size() > 0) {
			// 1. 保存错误信息
			// 2. 保存表单数据
			// 3. 转发到regist.jsp
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
    	try{
    		  userService.regist(form);
    		   
    	}catch(UserException e){
    		/*
    		 * 如果校验失败 就将错误信息传到request域中去
    		 * 将form表单也传过去
    		 * 最后转发到msg.jsp
    		 */
    		request.setAttribute("msg", e.getMessage());
    		request.setAttribute("form", form);
    		return "f:jsps/user/regist.jsp";
    	}

		/*
		 * 发邮件
		 * 准备配置文件！
		 */
		// 获取配置文件内容
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader()
				.getResourceAsStream("email_template.properties"));
		String host = props.getProperty("host");//获取服务器主机
		String uname = props.getProperty("uname");//获取用户名
		String pwd = props.getProperty("pwd");//获取密码
		String from = props.getProperty("from");//获取发件人
		String to = form.getEmail();//获取收件人
		String subject = props.getProperty("subject");//获取主题
		String content = props.getProperty("content");//获取邮件内容
		content = MessageFormat.format(content, form.getCode());//替换{0}
		
		Session session = MailUtils.createSession(host, uname, pwd);//得到session
		Mail mail = new Mail(from, to, subject, content);//创建邮件对象
		try {
			MailUtils.send(session, mail);//发邮件！
		} catch (MessagingException e) {
		}
		

		/*
		 * 1. 保存成功信息
		 * 2. 转发到msg.jsp
		 */
		request.setAttribute("msg", "恭喜，注册成功！请马上到邮箱激活");
    	
    	return "f:jsps/msg.jsp";
    }
   
}
