package model;
import model.Food;
import java.math.BigDecimal;

public class CartItem {
	private Food food;
	private BigDecimal quantity;
	private int DetailId ;
	private double total;

	public CartItem() {
	}

	public double getTotal() {
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
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getTotalPrice () {
		if (food == null || food.getPrice() == null) {
			return BigDecimal.ZERO;
		}
			return food.getPrice().multiply(quantity);
	}
	public int getResid() {
		return food.getResID();
	}
}