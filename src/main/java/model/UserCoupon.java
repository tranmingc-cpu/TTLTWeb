package model;

import java.sql.Timestamp;

public class UserCoupon {
    private int id;
    private int accId;
    private int couponId;
    private Timestamp usedAt;
    public UserCoupon(){

    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAccountId() { return accId; }
    public void setAccountId(int accountId) { this.accId = accountId; }
    public int getCouponId() { return couponId; }
    public void setCouponId(int couponId) { this.couponId = couponId; }
    public Timestamp getUsedAt() { return usedAt; }
    public void setUsedAt(Timestamp usedAt) { this.usedAt = usedAt; }
}
