package model;

public class CartItem {
	private Food food;
	private int quantity;
	private int DetailId ;
	private double total;

	public CartItem() {
	}

	public double getToatl() {
		return total;
	}
	public void setTotal(double d) {
		this.total = d;
	}
	public int getDetailId() {
		return DetailId;
	}
	public void setDetailId(int detailId) {
		DetailId = detailId;
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
	public double getTotalPrice () {
		return food.getPrice() * quantity;

	}
	public int getResid() {
		return food.getResID();
	}
}