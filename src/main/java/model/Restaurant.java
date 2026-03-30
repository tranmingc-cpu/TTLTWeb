package model;

public class Restaurant {
	private int Resid;
	private String name;
	private String address;
	private int accid;
	private String phone;
	private String email;
	private String description;
	private double rating;
	public Restaurant () {
	}

	public int getAccid() {
		return accid;
	}

	public void setAccid(int accid) {
		this.accid = accid;
	}

	public int getResId() {
		return Resid;
	}
	public void setId(int Resid) {
		this.Resid = Resid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Restaurant [Resid=" + Resid + ", name=" + name + ", address=" + address + ", accid=" + accid + ", phone="
				+ phone + ", email=" + email + ", description=" + description + ", rating=" + rating + "]";
	}


}