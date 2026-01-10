package model;

public class Account {
private int idAccount;
private String userName;
private String password;
private String email;

private Role role;
public Account() {
	
}public enum Role {
    ADMIN, SELLER, USER
}

public int getIdAccount() {
	return idAccount;
}
public void setIdAccount(int idAccount) {
	this.idAccount = idAccount;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public Role getRole() {
	return role;
}
public void setRole(Role role) {
	this.role = role;
}


}
