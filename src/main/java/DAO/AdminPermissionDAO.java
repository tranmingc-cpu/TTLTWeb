package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.AdminPermission;

public class AdminPermissionDAO {

    public AdminPermission getByAccountId(int accountId) {

        String sql = """
            SELECT *
            FROM ADMINPERMISSION
            WHERE ACCOUNTID = ?
            """;

        try {
            Connection conn = DBConnect.getConnect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                AdminPermission p = new AdminPermission();

                p.setAccountId(rs.getInt("ACCOUNTID"));

                // USER
                p.setViewUser(rs.getBoolean("VIEWUSER"));
                p.setAddUser(rs.getBoolean("ADDUSER"));
                p.setEditUser(rs.getBoolean("EDITUSER"));
                p.setDeleteUser(rs.getBoolean("DELETEUSER"));

                // ORDER
                p.setViewOrder(rs.getBoolean("VIEWORDER"));
                p.setAddOrder(rs.getBoolean("ADDORDER"));
                p.setEditOrder(rs.getBoolean("EDITORDER"));
                p.setDeleteOrder(rs.getBoolean("DELETEORDER"));

                // PRODUCT
                p.setViewProduct(rs.getBoolean("VIEWPRODUCT"));
                p.setAddProduct(rs.getBoolean("ADDPRODUCT"));
                p.setEditProduct(rs.getBoolean("EDITPRODUCT"));
                p.setDeleteProduct(rs.getBoolean("DELETEPRODUCT"));

                // COUPON
                p.setViewCoupon(rs.getBoolean("VIEWCOUPON"));
                p.setAddCoupon(rs.getBoolean("ADDCOUPON"));
                p.setEditCoupon(rs.getBoolean("EDITCOUPON"));
                p.setDeleteCoupon(rs.getBoolean("DELETECOUPON"));

                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Tạo quyền mặc định cho admin mới
    public boolean insert(int accountId) {

        String sql = """
            INSERT INTO ADMINPERMISSION(
            ACCOUNTID)
            VALUES(?)
            """;

        try {
           Connection conn =  DBConnect.getConnect();
           PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, accountId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cập nhật phân quyền
    public boolean update(AdminPermission p) {

        String sql = """
        UPDATE ADMINPERMISSION
        SET
        VIEWUSER=?,
        ADDUSER=?,
        EDITUSER=?,
        DELETEUSER=?,

        VIEWORDER=?,
        ADDORDER=?,
        EDITORDER=?,
        DELETEORDER=?,

        VIEWPRODUCT=?,
        ADDPRODUCT=?,
        EDITPRODUCT=?,
        DELETEPRODUCT=?,

        VIEWCOUPON=?,
        ADDCOUPON=?,
        EDITCOUPON=?,
        DELETECOUPON=?

        WHERE ACCOUNTID=?
        """;

        try {

           Connection conn =  DBConnect.getConnect();
          PreparedStatement  ps = conn.prepareStatement(sql);

            int i = 1;

            ps.setBoolean(i++, p.isViewUser());
            ps.setBoolean(i++, p.isAddUser());
            ps.setBoolean(i++, p.isEditUser());
            ps.setBoolean(i++, p.isDeleteUser());

            ps.setBoolean(i++, p.isViewOrder());
            ps.setBoolean(i++, p.isAddOrder());
            ps.setBoolean(i++, p.isEditOrder());
            ps.setBoolean(i++, p.isDeleteOrder());

            ps.setBoolean(i++, p.isViewProduct());
            ps.setBoolean(i++, p.isAddProduct());
            ps.setBoolean(i++, p.isEditProduct());
            ps.setBoolean(i++, p.isDeleteProduct());

            ps.setBoolean(i++, p.isViewCoupon());
            ps.setBoolean(i++, p.isAddCoupon());
            ps.setBoolean(i++, p.isEditCoupon());
            ps.setBoolean(i++, p.isDeleteCoupon());

            ps.setInt(i++, p.getAccountId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}