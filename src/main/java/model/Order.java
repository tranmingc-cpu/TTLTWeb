package model;


import java.util.*;

public class Order {
private int orderId;
private int accountId;
private Date orderDate;
private double totalAmount;
private String status;
private String address;
private int resId;
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public Order() {

}
public int getOrderId() {
	return orderId;
}
public void setOrderId(int orderId) {
	this.orderId = orderId;
}

public int getResId() {
	return resId;
}
public void setResId(int resId) {
	this.resId = resId;
}
public int getAccountId() {
	return accountId;
}
public void setAccountId(int accountId) {
	this.accountId = accountId;
}
public Date getOrderDate() {
	return orderDate;
}
public void setOrderDate(Date orderDate) {
	this.orderDate = orderDate;
}
public double getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(double totalAmount) {
	this.totalAmount = totalAmount;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

	
}
