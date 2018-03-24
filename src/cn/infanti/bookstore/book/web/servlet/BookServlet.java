package cn.infanti.bookstore.book.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.infanti.bookstore.book.domain.Book;
import cn.infanti.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class BookServlet
 */
public class BookServlet extends BaseServlet {
	private BookService bookService = new BookService();
  
public String findAll(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	/**
	 *  1、调用bookService的findAll方法得到所有图书集合
	 *  2、将图书集合保存在域中
	 *  3、转发到list.jsp
	 */
	List<Book> bookList = bookService.findAll();
	request.setAttribute("bookList", bookList);
	return "f:/jsps/book/list.jsp";
} 
	public String findByCategory(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
     /**
      * 通过cid来查找图书
      */
      List<Book> bookList = bookService.findByCategory(request.getParameter("cid"));
  		request.setAttribute("bookList", bookList);
		
  		return "f:/jsps/book/list.jsp";

	}
	public String load(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
     /**
      * 通过bid来查找图书
      */
      Book book = bookService.findByBid(request.getParameter("bid"));
  		request.setAttribute("book", book);
		
  		return "f:/jsps/book/desc.jsp";

	}
	
}

