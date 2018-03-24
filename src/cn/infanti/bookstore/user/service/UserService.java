package cn.infanti.bookstore.user.service;

import java.sql.SQLException;

import cn.infanti.bookstore.user.dao.UserDao;
import cn.infanti.bookstore.user.domain.User;

/**
 * user 业务层
 * @author 尹顺
 *
 */
public class UserService {
    private UserDao userDao = new UserDao();


     
	public void regist(User form) throws UserException, SQLException {
	    String name = form.getUsername();
	    User user = new User();
	    user = userDao.findByUsername(name);
		if(user != null){
			throw new UserException(user.getUsername() + "这个用户名已经被注册过了");
		}
		String email = form.getEmail();
		user = userDao.findByEmail(email);
		if(user != null){
			throw new UserException(user.getEmail() + "这个邮箱已经被注册过了");

		}
		userDao.add(form);
				
		
	}



	public void active(String code) throws UserException {
		/**
		 * 调用userdao方法 findByCode 通过code找到user对象
		 * 如果user为null 则说明激活码错误
		 * 如果user对象state为true则为二次激活，抛出异常 
		 * 如果user对象state为false则修改为true ,需要调用updateState方法
		 */
		User user = userDao.findByCode(code);
		if(user==null)
			throw new UserException("激活码无效！！！");
	    if(user.isState())
	    	throw new UserException("您已经激活过了 ，请不要再次激活！！！！");
	     userDao.updateState(user.getUid(),true);
	    
	    
	}



	public User login(User user) throws UserException {
		User form = userDao.findByUsername(user.getUsername());
          if(form==null)
        	  throw new UserException("用户名不存在");
          if(!form.getPassword().equals(user.getPassword()))
        	  throw new UserException("密码错误");
         /* if(form.isState()==false)
        	  throw new UserException("账户未被激活");*/
           
		return form;
	}




    
}
