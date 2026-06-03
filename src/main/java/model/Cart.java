package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

	private int idcart;
	private int accountId;
	private List<CartItem> items = new ArrayList<>();

	public Cart() {
	}
	public int getIdcart() {
		return idcart;
	}
	public void setIdcart(int idcart) {
		this.idcart = idcart;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public List<CartItem> getItems() {
		return items;
	}
	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	public void addItem(Food food, BigDecimal quantity) {
		if (items == null) {
			items = new ArrayList<>();
		}
		for (CartItem item : items) {
			if (item.getFood() != null
					&& item.getFood().getId() == food.getId()) {
				item.setQuantity(
						item.getQuantity().add(quantity)
				);
				return;
			}
		}
		CartItem newItem = new CartItem();
		newItem.setFood(food);
		newItem.setQuantity(quantity);
		items.add(newItem);
	}
	public void removeItem(int foodId) {
		if (items != null) {
			items.removeIf(item ->
					item.getFood() != null
							&& item.getFood().getId() == foodId
			);
		}
	}

	public BigDecimal getTotalPrice() {
		BigDecimal total = BigDecimal.ZERO;
		if (items == null) {
			return total;
		}
		for (CartItem cartItem : items) {

			if (cartItem.getTotalPrice() != null) {

				total = total.add(
						cartItem.getTotalPrice()
				);
			}
		}
		return total;
	}
	public BigDecimal getTotalQuantity() {
		BigDecimal q = BigDecimal.ZERO;
		if (items == null) {
			return q;
		}
		for (CartItem c : items) {

			q = q.add(c.getQuantity());
		}
		return q;
	}
	public void clear() {
		if (items != null) {
			items.clear();
		}
	}

	public void updateItem(int foodId, BigDecimal quantity) {
		if (items == null) {
			return;
		}
		items.removeIf(i ->
				i.getFood() != null
						&& i.getFood().getId() == foodId
						&& quantity.compareTo(BigDecimal.ZERO) <= 0
		);
		for (CartItem cartI : items) {
			if (cartI.getFood() != null
					&& cartI.getFood().getId() == foodId) {
				cartI.setQuantity(quantity);
				return;
			}
		}
	}
}