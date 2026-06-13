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

                p.setViewUser(rs.getBoolean("VIEWUSER"));
                p.setAddUser(rs.getBoolean("ADDUSER"));
                p.setEditUser(rs.getBoolean("EDITUSER"));
                p.setDeleteUser(rs.getBoolean("DELETEUSER"));

                p.setViewOrder(rs.getBoolean("VIEWORDER"));
                p.setAddOrder(rs.getBoolean("ADDORDER"));
                p.setEditOrder(rs.getBoolean("EDITORDER"));
                p.setDeleteOrder(rs.getBoolean("DELETEORDER"));

                p.setViewProduct(rs.getBoolean("VIEWPRODUCT"));
                p.setAddProduct(rs.getBoolean("ADDPRODUCT"));
                p.setEditProduct(rs.getBoolean("EDITPRODUCT"));
                p.setDeleteProduct(rs.getBoolean("DELETEPRODUCT"));

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
    public AdminPermission getPermissionByAccountId(int accountId) {

        String sql = "SELECT * FROM ADMINPERMISSION WHERE ACCOUNTID = ?";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                AdminPermission p = new AdminPermission();

                p.setAccountId(rs.getInt("ACCOUNTID"));

                p.setAddUser(rs.getBoolean("ADDUSER"));
                p.setViewUser(rs.getBoolean("VIEWUSER"));
                p.setEditUser(rs.getBoolean("EDITUSER"));
                p.setDeleteUser(rs.getBoolean("DELETEUSER"));

                p.setAddOrder(rs.getBoolean("ADDORDER"));
                p.setViewOrder(rs.getBoolean("VIEWORDER"));
                p.setEditOrder(rs.getBoolean("EDITORDER"));
                p.setDeleteOrder(rs.getBoolean("DELETEORDER"));

                p.setAddProduct(rs.getBoolean("ADDPRODUCT"));
                p.setViewProduct(rs.getBoolean("VIEWPRODUCT"));
                p.setEditProduct(rs.getBoolean("EDITPRODUCT"));
                p.setDeleteProduct(rs.getBoolean("DELETEPRODUCT"));

                p.setAddCoupon(rs.getBoolean("ADDCOUPON"));
                p.setViewCoupon(rs.getBoolean("VIEWCOUPON"));
                p.setEditCoupon(rs.getBoolean("EDITCOUPON"));
                p.setDeleteCoupon(rs.getBoolean("DELETECOUPON"));

                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean insertPermission(AdminPermission p) {

        String sql =
                "INSERT INTO ADMINPERMISSION VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, p.getAccountId());

            ps.setBoolean(2, p.isAddUser());
            ps.setBoolean(3, p.isViewUser());
            ps.setBoolean(4, p.isEditUser());
            ps.setBoolean(5, p.isDeleteUser());

            ps.setBoolean(6, p.isAddOrder());
            ps.setBoolean(7, p.isViewOrder());
            ps.setBoolean(8, p.isEditOrder());
            ps.setBoolean(9, p.isDeleteOrder());

            ps.setBoolean(10, p.isAddProduct());
            ps.setBoolean(11, p.isViewProduct());
            ps.setBoolean(12, p.isEditProduct());
            ps.setBoolean(13, p.isDeleteProduct());

            ps.setBoolean(14, p.isAddCoupon());
            ps.setBoolean(15, p.isViewCoupon());
            ps.setBoolean(16, p.isEditCoupon());
            ps.setBoolean(17, p.isDeleteCoupon());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updatePermission(AdminPermission p) {

        String sql =
                "UPDATE ADMINPERMISSION SET " +
                        "ADDUSER=?, VIEWUSER=?, EDITUSER=?, DELETEUSER=?, " +
                        "ADDORDER=?, VIEWORDER=?, EDITORDER=?, DELETEORDER=?, " +
                        "ADDPRODUCT=?, VIEWPRODUCT=?, EDITPRODUCT=?, DELETEPRODUCT=?, " +
                        "ADDCOUPON=?, VIEWCOUPON=?, EDITCOUPON=?, DELETECOUPON=? " +
                        "WHERE ACCOUNTID=?";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setBoolean(1, p.isAddUser());
            ps.setBoolean(2, p.isViewUser());
            ps.setBoolean(3, p.isEditUser());
            ps.setBoolean(4, p.isDeleteUser());

            ps.setBoolean(5, p.isAddOrder());
            ps.setBoolean(6, p.isViewOrder());
            ps.setBoolean(7, p.isEditOrder());
            ps.setBoolean(8, p.isDeleteOrder());

            ps.setBoolean(9, p.isAddProduct());
            ps.setBoolean(10, p.isViewProduct());
            ps.setBoolean(11, p.isEditProduct());
            ps.setBoolean(12, p.isDeleteProduct());

            ps.setBoolean(13, p.isAddCoupon());
            ps.setBoolean(14, p.isViewCoupon());
            ps.setBoolean(15, p.isEditCoupon());
            ps.setBoolean(16, p.isDeleteCoupon());

            ps.setInt(17, p.getAccountId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean saveOrUpdate(AdminPermission p) {

        AdminPermission old =
                getPermissionByAccountId(p.getAccountId());

        if (old == null) {
            return insertPermission(p);
        }

        return updatePermission(p);
    }

    public AdminPermission getByAdminId(int adminId) {

        String sql = "SELECT * FROM ADMINPERMISSON WHERE ACCOUNTID = ?";

        try (
                Connection conn = DBConnect.getConnect();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, adminId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                AdminPermission p = new AdminPermission();

                p.setAccountId(rs.getInt("ACCOUNTID"));

                p.setAddUser(rs.getBoolean("ADDUSER"));
                p.setViewUser(rs.getBoolean("VIEWUSER"));
                p.setEditUser(rs.getBoolean("EDITUSER"));
                p.setDeleteUser(rs.getBoolean("DELETEUSER"));

                p.setAddOrder(rs.getBoolean("ADDORDER"));
                p.setViewOrder(rs.getBoolean("VIEWORDER"));
                p.setEditOrder(rs.getBoolean("EDITORDER"));
                p.setDeleteOrder(rs.getBoolean("DELETEORDER"));

                p.setAddProduct(rs.getBoolean("ADDPRODUCT"));
                p.setViewProduct(rs.getBoolean("VIEWPRODUCT"));
                p.setEditProduct(rs.getBoolean("EDITPRODUCT"));
                p.setDeleteProduct(rs.getBoolean("DELETEPRODUCT"));

                p.setAddCoupon(rs.getBoolean("ADDCOUPON"));
                p.setViewCoupon(rs.getBoolean("VIEWCOUPON"));
                p.setEditCoupon(rs.getBoolean("EDITCOUPON"));
                p.setDeleteCoupon(rs.getBoolean("DELETECOUPON"));


                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}