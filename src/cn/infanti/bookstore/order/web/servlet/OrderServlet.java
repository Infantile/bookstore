package cn.infanti.bookstore.order.web.servlet;

import cn.infanti.bookstore.cart.domain.Cart;
import cn.infanti.bookstore.cart.domain.Cartitem;
import cn.infanti.bookstore.order.domain.Order;
import cn.infanti.bookstore.order.domain.OrderItem;
import cn.infanti.bookstore.order.service.OrderService;
import cn.infanti.bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	public String myOrders(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	/**
	 * 从session中获取User对象，再获取uid
	 * 通过uid来获取调用service方法获得 list<order> orderList保存到request中
	 * 转发到jsps/order/list.jsp
	 */
		User user = (User) request.getSession().getAttribute("session_user");
		String uid = user.getUid();//获取uid
		List<Order> orderList = orderService.myOrders(uid);		
		request.setAttribute("orderList", orderList);
	return "f:/jsps/order/list.jsp";
	}
	/**
	 * 添加订单
	 */
public String add(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
      /**
       * 1、从session中获得cart 购物车中的数据
       * 2、将数据生成order对象传给service 中的add方法
       * 3、保存order到request域中去 转发/jsps/order/desc.jsp
       */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//创建order对象并设置属性
		Order order = new Order();
		order.setOid(CommonUtils.uuid()); //设置oid
		order.setOrdertime(new Date());//设置当前系统时间
		User user = (User) request.getSession().getAttribute("session_user");
		order.setOwner(user);  //设置订单的所有者
		order.setTotal(cart.getTotal());//设置合计
		order.setState(1); //设置订单状态
		/**
		 * 创建订单条目集合
		 */
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();		
		for(Cartitem cartItem:cart.getCartItems()){
			OrderItem oi = new OrderItem();
			 oi.setIid(CommonUtils.uuid());//设置订单条目id
			 oi.setCount(cartItem.getCount());//设置条目数量
			 oi.setBook(cartItem.getBook());//设置书籍信息
			 oi.setOrder(order);//设置订单信息
			 oi.setSubtotal(cartItem.getSubtotal());//设置小计
			orderItemList.add(oi);//添加一个订单条目到订单条盲目集合中
		}
		order.setOrderItemList(orderItemList); //设置订单条目集合
		//清空购物车
	     cart.clear();
		/**
		 *  调用service添加订单
		 */
		orderService.add(order);
	    request.setAttribute("order", order);
			
	return "f:/jsps/order/desc.jsp";
}
}
