package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Food;

public class FoodDAOimpl implements FoodDAO {

    @Override
    public Food infomation(int id) {
        String sql = "SELECT * FROM FOOD WHERE ID = ?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                f.setPrice(rs.getBigDecimal("PRICE"));
                f.setDescription(rs.getString("DESCRIPTIONS"));
                f.setImage(rs.getString("IMAGES"));
                f.setCATEGORYId(rs.getInt("CATEGORYID"));
                f.setResID(rs.getInt("RESID"));
                f.setQuantity(rs.getBigDecimal("QUANTITY"));
                f.setDiscount(rs.getInt("DISCOUNT"));
                return f;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertFood(Food food) {
        String sql = "INSERT INTO Food(FNAME, PRICE, DESCRIPTIONS, CATEGORYID, IMAGES, RESID, QUANTITY) "
                + "VALUES (?,?,?,?,?,?,?)";

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, food.getName());
            ps.setBigDecimal(2, food.getPrice());
            ps.setString(3, food.getDescription());
            ps.setInt(4, food.getCATEGORYId());
            ps.setString(5, food.getImage());
            ps.setInt(6, food.getResID());
            ps.setBigDecimal(7, food.getQuantity());

            int rows = ps.executeUpdate();
            System.out.println("Rows affected = " + rows);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBySeller(Food food, int accId) {
        String sql = "UPDATE FOOD SET FNAME=?, PRICE=?, DESCRIPTIONS=?, QUANTITY=?, IMAGES=? WHERE ID=?  "
                + "AND RESID = (SELECT RESID FROM RESTAURANT WHERE ACCID=?)";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, food.getName());
            ps.setBigDecimal(2, food.getPrice());
            ps.setString(3, food.getDescription());
            ps.setBigDecimal(4, food.getQuantity());
            ps.setString(5, food.getImage());
            ps.setInt(6, food.getId());
            ps.setInt(7, accId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Food food) {
        String sql = "UPDATE FOOD SET FNAME=?, PRICE=?, DESCRIPTIONS=?, QUANTITY=?, IMAGES=? WHERE ID=? "
                + "AND RESID =?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, food.getName());
            ps.setBigDecimal(2, food.getPrice());
            ps.setString(3, food.getDescription());
            ps.setBigDecimal(4, food.getQuantity());
            ps.setString(5, food.getImage());
            ps.setInt(6, food.getId());
            ps.setInt(7, food.getResID());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id, int accId) {
        String sql = "DELETE FROM Food WHERE ID=? AND RESID = (SELECT RESID FROM RESTAURANT WHERE ACCID=?)";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, accId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Food> findByName(String name) {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM Food WHERE FNAME LIKE ?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                f.setPrice(rs.getBigDecimal("PRICE"));
                f.setDescription(rs.getString("DESCRIPTIONS"));
                f.setImage(rs.getString("IMAGES"));
                f.setQuantity(rs.getBigDecimal("QUANTITY"));
                list.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Food> findByCategory(int category) {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM FOOD WHERE CATEGORYID =?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                f.setPrice(rs.getBigDecimal("PRICE"));
                f.setDescription(rs.getString("DESCRIPTIONS"));
                f.setImage(rs.getString("IMAGES"));
                f.setCATEGORYId(rs.getInt("CATEGORYID"));
                f.setQuantity(rs.getBigDecimal("QUANTITY"));
                list.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Food> findALL() {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT ID, FNAME, IMAGES, PRICE, QUANTITY FROM FOOD";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                f.setImage(rs.getString("IMAGES"));
                f.setPrice(rs.getBigDecimal("PRICE"));
                f.setQuantity(rs.getBigDecimal("QUANTITY"));
                list.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Food> findLimit(int limit) {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT TOP (?) * FROM Food";

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                f.setPrice(rs.getBigDecimal("PRICE"));
                f.setDescription(rs.getString("DESCRIPTIONS"));
                f.setImage(rs.getString("IMAGES"));
                f.setQuantity(rs.getBigDecimal("QUANTITY"));
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countFood() {
        String sql = "SELECT COUNT(*) FROM FOOD";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Food> getFoodsByRestaurant(int resid) {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM FOOD WHERE RESID = ?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, resid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                f.setPrice(rs.getBigDecimal("PRICE"));
                f.setImage(rs.getString("IMAGES"));
                f.setDescription(rs.getString("DESCRIPTIONS"));
                f.setResID(rs.getInt("RESID"));
                f.setCATEGORYId(rs.getInt("CATEGORYID"));
                f.setQuantity(rs.getBigDecimal("QUANTITY"));
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countFoodByRestaurant(int resId) {
        String sql = "SELECT COUNT(*) FROM FOOD WHERE RESID = ?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, resId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getResIdByFoodId(int foodId) {
        String sql = "SELECT RESID FROM FOOD WHERE ID = ?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, foodId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("RESID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Food> getFoodBySeller(int accId) {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT f.* FROM Food f JOIN RESTAURANT r ON f.RESID = r.RESID WHERE r.ACCID = ?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                f.setPrice(rs.getBigDecimal("PRICE"));
                f.setDescription(rs.getString("DESCRIPTIONS"));
                f.setCATEGORYId(rs.getInt("CATEGORYID"));
                f.setImage(rs.getString("IMAGES"));
                f.setResID(rs.getInt("RESID"));
                f.setQuantity(rs.getBigDecimal("QUANTITY"));
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Food getFoodbyFoodId(int id) {
        String sql = "SELECT * FROM food WHERE ID = ?";
        Food food = null;

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                food = new Food();
                food.setId(rs.getInt("ID"));
                food.setName(rs.getString("FNAME"));
                food.setPrice(rs.getBigDecimal("PRICE"));
                food.setImage(rs.getString("IMAGES"));
                food.setResID(rs.getInt("RESID"));
                food.setQuantity(rs.getBigDecimal("QUANTITY"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }

    @Override
    public void deleteFood(int id) {
        String sql = "DELETE FROM Food WHERE ID = ?";

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Food> searchAdvanced(String keyword, Integer categoryId, Double minPrice, Double maxPrice) {
        List<Food> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Food WHERE 1=1");

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (FNAME LIKE ? OR DESCRIPTIONS LIKE ?)");
        }
        if (categoryId != null) {
            sql.append(" AND CATEGORYID = ?");
        }
        if (minPrice != null) {
            sql.append(" AND PRICE >= ?");
        }
        if (maxPrice != null) {
            sql.append(" AND PRICE <= ?");
        }

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int index = 1;

            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(index++, "%" + keyword + "%");
                ps.setString(index++, "%" + keyword + "%");
            }
            if (categoryId != null) {
                ps.setInt(index++, categoryId);
            }
            if (minPrice != null) {
                ps.setDouble(index++, minPrice);
            }
            if (maxPrice != null) {
                ps.setDouble(index++, maxPrice);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                f.setPrice(rs.getBigDecimal("PRICE"));
                f.setImage(rs.getString("IMAGES"));
                list.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Food> findByPage(int page, int pageSize) {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM FOOD ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int offset = (page - 1) * pageSize;

            ps.setInt(1, offset);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                f.setImage(rs.getString("IMAGES"));
                f.setPrice(rs.getBigDecimal("PRICE"));
                f.setQuantity(rs.getBigDecimal("QUANTITY"));

                list.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public List<Food> getDisountFood (){
        String sql = "SELECT ID ,FNAME , PRICE , IMAGES , QUANTITY,  DISCOUNT FROM FOOD WHERE ID =? ";
        List<Food> list = new ArrayList<>();

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql);
                )
      {
            ResultSet rs = ps.executeQuery();
           while( rs.next()){
               Food f = new Food();
               f.setId(rs.getInt("ID"));
               f.setName(rs.getString("FNAME"));
               f.setPrice(rs.getBigDecimal("PRICE"));
               f.setImage(rs.getString("IMAGES"));
               f.setQuantity(rs.getBigDecimal("QUANTITY"));
               f.setDiscount(rs.getInt("DISCOUNT"));
               list.add(f);
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
                    return list ;
    }
    public List<Food> getAllFoodNames() {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT ID, FNAME FROM FOOD";

        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Food f = new Food();
                f.setId(rs.getInt("ID"));
                f.setName(rs.getString("FNAME"));
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean decreaseFoodStock(int foodId, BigDecimal quantityBought) {
        String sql = "UPDATE FOOD SET QUANTITY = QUANTITY - ? WHERE ID = ? AND QUANTITY >= ?";
        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, quantityBought);
            ps.setInt(2, foodId);
            ps.setBigDecimal(3, quantityBought);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}