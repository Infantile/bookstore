package cn.infanti.bookstore.book.service;

import java.util.List;

import cn.infanti.bookstore.book.dao.BookDao;
import cn.infanti.bookstore.book.domain.Book;

public class BookService {
 private BookDao bookDao = new BookDao();

public List<Book> findAll() {
	
	return bookDao.findAll();
}

public List<Book> findByCategory(String cid) {

	
	return bookDao.findByCategory(cid);
}

public Book findByBid(String bid) {
	// TODO Auto-generated method stub
	return bookDao.findByBid(bid);
}
}
