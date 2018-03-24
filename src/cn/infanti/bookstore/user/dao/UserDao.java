package cn.infanti.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.infanti.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
    private QueryRunner qr =  new TxQueryRunner();

	public User findByUsername(String name) {
	    String sql = "select * from tb_user where username=?";
	    try {
			User user = qr.query(sql, new BeanHandler<User>(User.class),name);
			  if(user != null){
			    	return user;
			    }
		} catch (SQLException e) {
			throw new RuntimeException(e);
			}
	  
		return null;
	}

	public User findByEmail(String email) {
		 String sql = "select * from tb_user where email=?";
		    try {
				User user = qr.query(sql, new BeanHandler<User>(User.class),email);
				  if(user != null){
				    	return user;
				    }
			} catch (SQLException e) {
				throw new RuntimeException(e);
				}
		  
		return null;
	}
	public User findByCode(String code) {
		 String sql = "select * from tb_user where code=?";
		    try {
				User user = qr.query(sql, new BeanHandler<User>(User.class),code);
				  if(user != null){
				    	return user;
				    }
			} catch (SQLException e) {
				throw new RuntimeException(e);
				}
		  
		return null;
	}

	public void add(User form) throws SQLException {
  		String sql = "insert into tb_user values(?,?,?,?,?,?)";
  		Object[] params = {form.getUid(),form.getUsername(),form.getPassword(),
  							form.getEmail(),form.getCode(),form.isState()};
  		qr.update(sql, params);
	}

	public void updateState(String uid, boolean b) {
		 String sql = "update tb_user set state=? where uid=?";
		    try {
				qr.update(sql,b,uid);
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
				}
	}
    
}
