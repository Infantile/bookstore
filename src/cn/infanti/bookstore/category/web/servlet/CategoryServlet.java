package cn.infanti.bookstore.category.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.infanti.bookstore.category.domain.Category;
import cn.infanti.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;




public class CategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	
	/**
	 * 1、通过categoryservice 调用findAll 返回category集合
	 * 2、将集合传到域中
	 * 3、转发到left.jsp中
	 * @throws SQLException 
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		  List<Category> categoryList = categoryService.findAll(); 
		  request.setAttribute("categoryList", categoryList);
		return "f:/jsps/left.jsp";
	}

}
