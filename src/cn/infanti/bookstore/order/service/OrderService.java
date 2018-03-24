package cn.infanti.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.infanti.bookstore.order.dao.OrderDao;
import cn.infanti.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
  private OrderDao orderDao = new OrderDao();
  /**
   * 添加订单
   * 	需要处理事务
   */
  public void add(Order order){
	  try{
		  /**
		   * 开启事务
		   */
		  JdbcUtils.beginTransaction();
		  orderDao.addOrder(order);
		  orderDao.addOrderItemList(order.getOrderItemList());
		  /**
		   * 提交事务
		   */
		  JdbcUtils.commitTransaction();
		  
	  }catch(Exception e ){
		  try {
			JdbcUtils.rollbackTransaction();
		} catch (SQLException e1) {
			 	throw new RuntimeException(e1);
		}
	  }
  }
public List<Order> myOrders(String uid) {
	
	return orderDao.findByUid(uid);
}
}
