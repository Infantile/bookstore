package cn.infanti.bookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.infanti.bookstore.book.domain.Book;
import cn.infanti.bookstore.order.domain.Order;
import cn.infanti.bookstore.order.domain.OrderItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
    
/**
 * 插入订单	
 */
   public void addOrder(Order order){
	   try{
		    String sql = "insert into orders values(?,?,?,?,?,?)";
		    Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
		    Object[] params = {order.getOid(),timestamp,order.getTotal(),
		    		order.getState(),order.getOwner().getUid(),
		    		order.getAddress()};
		    qr.update(sql,params);
	   }catch(SQLException e ){
		   throw new RuntimeException(e);
	   }
   }
   /**
    * 添加订单条目
    * @param orderItemList
    */
 public void addOrderItemList(List<OrderItem> orderItemList){
	 /**
	  * 批处理方法
	  * 其中queryRunner类的batch（String sql,object[][] params)
	  * 其中params 是一个多维数组
	  * 每一个一维数组与sql执行一次batch
	  */
	 try{
	   String sql = "insert into orderitem values(?,?,?,?,?)";
	   /**
	    * 把orderItemList 转化为一个二维数组
	    */
	   Object[][] params = new Object[orderItemList.size()][];
	   for(int i =0;i<orderItemList.size();i++){
		   OrderItem item = orderItemList.get(i);
		   params[i] = new Object[]{item.getIid(),item.getCount(),
				   item.getSubtotal(),item.getOrder().getOid(),item.getBook().getBid()};
	   }
	   qr.batch(sql, params);  //执行批处理
	   
	 }catch(SQLException e ){
		   throw new RuntimeException(e);
	 }
	 
   }
public List<Order> findByUid(String uid) {
     /**
      * 通过uid查询数据库得到 orderList
      * 循环遍历得到order 得到每个的orderItemList
      */
    try {
    	/**
    	 * 当前用户的所有订单
    	 */
    String sql = "select * from orders where uid=?";
	List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class),uid);
	for(Order order:orderList){		

		loadOrderItems(order);
	}
	return orderList;
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
}
	private void loadOrderItems(Order order) {
		/**
		 * 一个Order 来通过查询数据库 来获取所有订单条目集合然后写进去
		 * 通过oid查询订单条目	
		 * sql语句查询的结果是两张表的笛卡尔积，
		 * 通过 mapListHandler 得到的每一个map中是每一个键值对
		 */
		String sql = "select * from orderitem o,book b where o.bid=b.bid AND oid=?";
		try {
			List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(),order.getOid());
	    /**
	     * 通过每一个map生成两个对象， OrderItem 和Book对象 然后将Book、对象设置到OrderItem中
		 * 循环遍历每个map 生成两个对象 然后建立关系 把OrderItem保存到Order中
		 */
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		order.setOrderItemList(orderItemList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 把mapList中每个map转换成两个对象
	 * 并建立关系
	 * 将MapList转换为 orerItemList
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String,Object> map:mapList){
			OrderItem item = null;
		    item = toOrderItem(map);
			orderItemList.add(item);
		}
		
		return orderItemList;
	}
	/**
	 * 将map转换成一个Item对象
	 * @param map
	 * @return
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {
	      OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
	      Book book = CommonUtils.toBean(map, Book.class);
	      orderItem.setBook(book);
	      return orderItem;
	}
}
