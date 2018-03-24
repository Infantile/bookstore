package cn.infanti.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	  private Map<String,Cartitem> map = new LinkedHashMap<String,Cartitem>();
	 
	  
	  /**
	   * 计算合计
	   * 总价
	   */
	  public double getTotal(){
		  BigDecimal total = new BigDecimal("0");
		  for(Cartitem cartitem : map.values()){
			  BigDecimal subtotal = new BigDecimal(cartitem.getSubtotal() + "");
		      total = total.add(subtotal) ; 
		  }
		  return total.doubleValue();
	  }
	  
	 /**
	  * 添加条目
	  * @param cartitem
	  */
	 public void add(Cartitem cartitem){
		 if(map.containsKey(cartitem.getBook().getBid())){ //判断原条目中是否存在
			 Cartitem _cartitem = map.get(cartitem.getBook().getBid());//返回原来的条目
			 _cartitem.setCount(_cartitem.getCount() + cartitem.getCount());//设置原条数量
			 map.put(cartitem.getBook().getBid(), _cartitem);
		 }else{
			 map.put(cartitem.getBook().getBid(),cartitem);
		 }
	 }
	 /**
	  * 清空条目
	  */
	 public void clear(){
		 map.clear();
	 }
	 /**
	  * 删除指定条目
	  */
	 public void remove(String bid){
		 map.remove(bid);
	 }
	 /**
	  * 遍历购物车
	  */
	 public Collection<Cartitem> getCartItems(){
		 return map.values();
	 }
 
}
