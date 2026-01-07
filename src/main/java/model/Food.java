package model;

public class Food {
private  int id;
private String name;
private double price;
private String description;
private String image;
private int ResID;
public int getResID() {
	return ResID;
}

public void setResID(int resID) {
	ResID = resID;
}

public Food() { 
}

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
public double getPrice() {
	return price;
}
public void setPrice(double price) {
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
