package cn.infanti.bookstore.category.service;

import java.sql.SQLException;
import java.util.List;

import cn.infanti.bookstore.category.dao.CategoryDao;
import cn.infanti.bookstore.category.domain.Category;

public class CategoryService {
   private CategoryDao categoryDao = new CategoryDao();

public List<Category> findAll() throws SQLException {
	  
	return categoryDao.findAll();
}
}
