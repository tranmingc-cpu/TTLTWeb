package model;

import java.math.BigDecimal;

public class Food {
	private  int id;
	private String name;
	private BigDecimal price;
	private String description;
	private String image;
	private int ResID;
	private BigDecimal quantity;
	private int categoryId;
	public int getCATEGORYId() {
		return categoryId;
	}
	public void setCATEGORYId(int CategoryId) {
		categoryId = CategoryId;
	}

	public int getResID() {
		return ResID;
	}

	public void setResID(int resID) {
		ResID = resID;
	}

	public Food() {
	}
     public BigDecimal getQuantity(){ return quantity;}
	public void setQuantity(BigDecimal quantity){this.quantity = quantity; }
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Food [id=" + id + ", " + name + ", " + price +  ", "
				+ description + "]";
	}

}