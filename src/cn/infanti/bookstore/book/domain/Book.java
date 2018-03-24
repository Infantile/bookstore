package cn.infanti.bookstore.book.domain;

public class Book {
 private String bid; //书编号
 private String bname; //书名
 private double price; //书价格
 private String author; //书作者
 private String image; //书图片
 private String cid; //书分类
public String getBid() {
	return bid;
}
public void setBid(String bid) {
	this.bid = bid;
}
public String getBname() {
	return bname;
}
public void setBname(String bname) {
	this.bname = bname;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public String getAuthor() {
	return author;
}
public void setAuthor(String author) {
	this.author = author;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public String getCid() {
	return cid;
}
public void setCid(String cid) {
	this.cid = cid;
}
 
}
