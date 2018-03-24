package cn.infanti.bookstore.cart.domain;

import java.math.BigDecimal;

import cn.infanti.bookstore.book.domain.Book;

public class Cartitem {
 private Book book; //商品类
 private int  count; // 数量
 /**
  * 小计方法
  * 处理了二进制误差问题
  * @return
  */
 public double getSubtotal(){
	 BigDecimal d1 = new BigDecimal(book.getPrice() + "");
	 BigDecimal d2 = new BigDecimal(count + "");
	 return d1.multiply(d2).doubleValue();
 }
public Book getBook() {
	return book;
}
public void setBook(Book book) {
	this.book = book;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
}
