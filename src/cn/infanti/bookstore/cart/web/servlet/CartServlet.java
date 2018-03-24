package cn.infanti.bookstore.cart.web.servlet;

import cn.infanti.bookstore.book.domain.Book;
import cn.infanti.bookstore.book.service.BookService;
import cn.infanti.bookstore.cart.domain.Cart;
import cn.infanti.bookstore.cart.domain.Cartitem;
import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {

	/**
	 * 添加购物条目
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/**
		 * 1、得到购物车
		 * 2、得到条目（图书和数量）
		 * 3、把条目添加到车中
		 */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		/**
		 * 得到图书的数量 以及图书的bid，通过bid 查询数据库得到Book
		 * 数量和bid 通过表单传递 
		 */
		String bid = request.getParameter("bid");
		Book book = new BookService().findByBid(bid);
		int count = Integer.parseInt(request.getParameter("count")) ;
		Cartitem cartitem = new Cartitem();
		cartitem.setBook(book);
		cartitem.setCount(count);
		
		cart.add(cartitem);
			return "f:/jsps/cart/list.jsp";
	}
   
	/**
	 * 清空购物条目
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/**
		 * 得到车
		 * 然后clear
		 */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clear();
			return "f:/jsps/cart/list.jsp";
	}
	/**
	 * 删除购物条目
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/**
		 * 得到车
		 * 得到要删除的bid
		 * 然后remove
		 */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.remove(bid);
		
			return "f:/jsps/cart/list.jsp";
	}
   
}
