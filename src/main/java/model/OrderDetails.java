package model;

public class OrderDetails {
private int id;
private int orderId;
private Food food;
private int quantity;
private double price;
public OrderDetails() {
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getOrderId() {
	return orderId;
}
public void setOrderId(int orderId) {
	this.orderId = orderId;
}

public Food getFood() {
	return food;
}
public void setFood(Food food) {
	this.food = food;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}

}
