package cn.infanti.bookstore.user.service;



/**
 * 自定义一个异常类
 *   只是给出父类中的构造器即可！方便用来创建对象！
 * @author ys
 *
 */

public class UserException extends Exception {
	public UserException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
