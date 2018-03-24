package cn.infanti.bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.infanti.bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
   private QueryRunner qr = new TxQueryRunner();

public List<Category> findAll() throws SQLException {
	 String sql = "select * from category";
	
	return  qr.query(sql, new BeanListHandler<Category>(Category.class));
}
}
