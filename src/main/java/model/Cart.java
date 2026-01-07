package model;

import java.util.List;

public class Cart {
	private int idcart;
	private String usersid;
private List<CartItem> items;

public Cart() {
}

public int getIdcart() {
	return idcart;
}

public void setIdcart(int idcart) {
	this.idcart = idcart;
}

public String getUsersid() {
	return usersid;
}

public void setUsersid(String usersid) {
	this.usersid = usersid;
}

public List<CartItem> getItems() {
	return items;
}

public void setItems(List<CartItem> items) {
	this.items = items;
}
// add món vào giỏ hàng
public void addItem(Food food, int quantity) {
	for (CartItem Item : items) {
		if(Item.getFood().getId()==food.getId()) {
			Item.setQuantity(Item.getQuantity()+ quantity);
			return;
			
		}
	}
	items.add(new CartItem());
}
public void removeItem (int FoodID) {
	items.removeIf(item -> item.getFood().getId()==FoodID);
		
	}
public double getTotalPrice () {
	double total =0;
	for (CartItem cartItem : items) {
		total+= cartItem.getTotalPrice();
		
	}
	return total;
}
public int getTotailQuantity() {
	int q =0;
	for (CartItem c : items) {
		q += c.getQuantity();	
	}
	return q;
}
public void Clear() {
	items.clear();
}
public void updateItem (int FoodID,int quantity) {
	 items.removeIf(i -> i.getFood().getId() == FoodID && quantity <= 0);
	for (CartItem cartI : items) {
		if(cartI.getFood().getId()==FoodID) {
			cartI.setQuantity(quantity);
			return;
		}
	}
}
}
