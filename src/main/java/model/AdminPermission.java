package model;

public class AdminPermission {

    private int accountId;

    private boolean viewUser;
    private boolean addUser;
    private boolean editUser;
    private boolean deleteUser;

    private boolean viewOrder;
    private boolean addOrder;
    private boolean editOrder;
    private boolean deleteOrder;

    private boolean viewProduct;
    private boolean addProduct;
    private boolean editProduct;
    private boolean deleteProduct;

    private boolean viewCoupon;
    private boolean addCoupon;
    private boolean editCoupon;
    private boolean deleteCoupon;

    public AdminPermission() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public boolean isViewUser() {
        return viewUser;
    }

    public void setViewUser(boolean viewUser) {
        this.viewUser = viewUser;
    }

    public boolean isAddUser() {
        return addUser;
    }

    public void setAddUser(boolean addUser) {
        this.addUser = addUser;
    }

    public boolean isEditUser() {
        return editUser;
    }

    public void setEditUser(boolean editUser) {
        this.editUser = editUser;
    }

    public boolean isDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(boolean deleteUser) {
        this.deleteUser = deleteUser;
    }

    public boolean isViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(boolean viewOrder) {
        this.viewOrder = viewOrder;
    }

    public boolean isAddOrder() {
        return addOrder;
    }

    public void setAddOrder(boolean addOrder) {
        this.addOrder = addOrder;
    }

    public boolean isEditOrder() {
        return editOrder;
    }

    public void setEditOrder(boolean editOrder) {
        this.editOrder = editOrder;
    }

    public boolean isDeleteOrder() {
        return deleteOrder;
    }

    public void setDeleteOrder(boolean deleteOrder) {
        this.deleteOrder = deleteOrder;
    }

    public boolean isViewProduct() {
        return viewProduct;
    }

    public void setViewProduct(boolean viewProduct) {
        this.viewProduct = viewProduct;
    }

    public boolean isAddProduct() {
        return addProduct;
    }

    public void setAddProduct(boolean addProduct) {
        this.addProduct = addProduct;
    }

    public boolean isEditProduct() {
        return editProduct;
    }

    public void setEditProduct(boolean editProduct) {
        this.editProduct = editProduct;
    }

    public boolean isDeleteProduct() {
        return deleteProduct;
    }

    public void setDeleteProduct(boolean deleteProduct) {
        this.deleteProduct = deleteProduct;
    }

    public boolean isViewCoupon() {
        return viewCoupon;
    }

    public void setViewCoupon(boolean viewCoupon) {
        this.viewCoupon = viewCoupon;
    }

    public boolean isAddCoupon() {
        return addCoupon;
    }

    public void setAddCoupon(boolean addCoupon) {
        this.addCoupon = addCoupon;
    }

    public boolean isEditCoupon() {
        return editCoupon;
    }

    public void setEditCoupon(boolean editCoupon) {
        this.editCoupon = editCoupon;
    }

    public boolean isDeleteCoupon() {
        return deleteCoupon;
    }

    public void setDeleteCoupon(boolean deleteCoupon) {
        this.deleteCoupon = deleteCoupon;
    }
}