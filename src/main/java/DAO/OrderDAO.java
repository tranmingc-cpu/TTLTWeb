package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import model.CartItem;
import model.Food;
import model.Order;
import model.OrderDetails;

public class OrderDAO {

	public int createOrder(int accId, int resId, BigDecimal total, String address) {

		String sql = """
				    INSERT INTO ORDERS (ACCOUNTID, RESID, TOTAL, ADDRES, ORDERDATE, STATUSS)
				    VALUES (?, ?, ?, ?, GETDATE(), ?)
				""";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, accId);
			ps.setInt(2, resId);
			ps.setBigDecimal(3, total);
			ps.setString(4, address);
			ps.setString(5, "PENDING");

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1); // ORDER ID
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void insertOrderDetail(int orderId, int foodId, BigDecimal quantity, BigDecimal price) {

		String sql = """
				    INSERT INTO ORDERSDETAIL (ORDERID, FOODID, QUANTITY, PRICE)
				    VALUES (?, ?, ?, ?)
				""";

		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, orderId);
			ps.setInt(2, foodId);
			ps.setBigDecimal(3, quantity);
			ps.setBigDecimal(4, price);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// đếm số order theo id cửa hàng
	public int countFoodByRes(int resid) {
		String sql = "SELECT COUNT(*) FROM FOOD WHERE RESID=?";
		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, resid);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// tổng tiền order trong ngày
	public double totalRevenue() {
		String sql = "SELECT SUM(TOTAL) FROM ORDERS";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery();) {
			if (rs.next()) {
				return rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	// lấy tất cả order
	public List<Order> getAllOrders() {

		List<Order> list = new ArrayList<>();

		String sql = """
				    SELECT *
				    FROM ORDERS
				""";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Order o = new Order();
				o.setOrderId(rs.getInt("ID"));
				o.setAccountId(rs.getInt("ACCOUNTID"));
				o.setTotalAmount(rs.getBigDecimal("TOTAL"));
				o.setResId(rs.getInt(rs.getInt("RESID")));
				o.setAddress(rs.getString("ADDRES"));
				o.setOrderDate(rs.getTimestamp("ORDERDATE"));
				o.setStatus(rs.getString("STATUSS"));
				list.add(o);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Order> getOrdersByIds(List<Integer> orderIds) {
		List<Order> orders = new ArrayList<>();

		if (orderIds == null || orderIds.isEmpty())
			return orders;

		String sql = "SELECT * FROM ORDERS WHERE ID = ?";

		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

			for (int id : orderIds) {
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					Order o = new Order();
					o.setOrderId(rs.getInt("ID"));
					o.setAccountId(rs.getInt("ACCOUNTID"));
					o.setResId(rs.getInt("RESID"));
					o.setTotalAmount(rs.getBigDecimal("TOTAL"));
					o.setStatus(rs.getString("STATUSS"));
					o.setOrderDate(rs.getTimestamp("ORDERDATE"));
					o.setAddress(rs.getString("ADDRES"));
					orders.add(o);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}
	// lấy tình trạng order theo accid

	public List<Order> getActiveOrdersByAccount(int accountId) {
		List<Order> list = new ArrayList<>();

		String sql = """
				    SELECT *
				    FROM ORDERS
				    WHERE ACCOUNTID = ?
				      AND STATUSS <> 'Done'
				    ORDER BY ORDERDATE DESC
				""";

		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, accountId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Order o = new Order();
				o.setOrderId(rs.getInt("ID"));
				o.setResId(rs.getInt("RESID"));
				o.setTotalAmount(rs.getBigDecimal("TOTAL"));
				o.setAddress(rs.getString("ADDRES"));
				o.setStatus(rs.getString("STATUSS"));
				o.setOrderDate(rs.getTimestamp("ORDERDATE"));
				list.add(o);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/* ===== GET ORDER DETAIL ===== */
	public List<OrderDetails> getOrderDetails(int orderId) {
		List<OrderDetails> list = new ArrayList<>();

		String sql = """
				    SELECT
				        od.ID AS od_id,
				        od.ORDERID AS od_order_id,
				        od.FOODID AS od_food_id,
				        od.QUANTITY AS od_quantity,
				        od.PRICE AS od_price,
				        f.ID AS f_id,
				        f.FNAME AS f_name,
				        f.IMAGES AS f_image,
				        f.PRICE AS f_price
				    FROM ORDERSDETAIL od
				    JOIN FOOD f ON od.FOODID = f.ID
				    WHERE od.ORDERID = ?
				""";

		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderDetails d = new OrderDetails();
				d.setId(rs.getInt("od_id"));
				d.setOrderId(rs.getInt("od_order_id"));
				d.setQuantity(rs.getInt("od_quantity"));
				d.setPrice(rs.getDouble("od_price")); // giá lúc mua

				Food f = new Food();
				f.setId(rs.getInt("f_id"));
				f.setName(rs.getString("f_name")); // FNAME
				f.setImage(rs.getString("f_image")); // IMAGES
				f.setPrice(rs.getBigDecimal("f_price")); // giá hiện tại

				d.setFood(f);
				list.add(d);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public void updateStatus(int orderId, String status) {
		String sql = "UPDATE ORDERS SET STATUS = ? WHERE ID = ?";
		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, status);
			ps.setInt(2, orderId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// tính tổng số order hôm nay
	public int countTodayOrders(int resId) {

		String sql = """
				    SELECT COUNT(*)
				    FROM ORDERS
				    WHERE RESID = ?
				      AND CAST(CREATED_AT AS DATE) = CAST(GETDATE() AS DATE)
				""";

		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, resId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	// tính giá tiền hôm nay thu đc
	public int sumTodayRevenue(int resId) {
		String sql = """
				    SELECT SUM(o.TOTAL)
				    FROM ORDERS o
				    WHERE o.RESID = ?
				      AND CAST(o.CREATED_AT AS DATE) = CAST(GETDATE() AS DATE)
				""";

		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, resId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// lấy thông tin món ăn của nhà hàng theo id
	public List<Order> getRecentOrdersByRestaurant(int resId) {

		List<Order> list = new ArrayList<>();

		String sql = """
				    SELECT TOP 5
				           o.ID,
				           o.TOTAL,
				           o.STATUSS,
				           o.ORDERDATE,
				           a.USERNAME AS CUSTOMER_NAME
				    FROM ORDERS o
				    JOIN ACCOUNT a ON o.ACCOUNTID = a.ID
				    WHERE o.RESID = ?
				    ORDER BY o.ORDERDATE DESC
				""";

		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, resId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Order o = new Order();
				o.setOrderId(rs.getInt("ID"));
				o.setTotalAmount(rs.getBigDecimal("TOTAL"));
				o.setStatus(rs.getString("STATUSS"));
				o.setOrderDate(rs.getTimestamp("ORDERDATE"));

				list.add(o);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// lấy những order theo accid trong khoảng thời gian nhất định
	public List<Order> getLatestOrdersByAccount(int accountId) {

		List<Order> list = new ArrayList<>();

		String sql = """
				    SELECT *
				    FROM ORDERS
				    WHERE ACCOUNTID = ?
				      AND ORDERDATE >= DATEADD(MINUTE, -5, GETDATE())
				    ORDER BY ORDERDATE DESC
				""";

		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, accountId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Order o = new Order();
				o.setOrderId(rs.getInt("ID"));
				o.setAccountId(rs.getInt("ACCOUNTID"));
				o.setResId(rs.getInt("RESID"));
				o.setTotalAmount(rs.getBigDecimal("TOTAL"));
				o.setAddress(rs.getString("ADDRES"));
				o.setOrderDate(rs.getTimestamp("ORDERDATE"));
				o.setStatus(rs.getString("STATUSS"));

				list.add(o);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// lấy order theo acc
	public List<Order> getOrdersByAccount(int accountId) {
		List<Order> list = new ArrayList<>();

		String sql = """
				    SELECT o.ID, o.TOTAL, o.ORDERDATE, o.STATUSS,
				           r.NAME AS RESNAME
				    FROM ORDERS o
				    JOIN RESTAURENT r ON o.RESID = r.RESID
				    WHERE o.ACCOUNTID = ?
				    ORDER BY o.ORDERDATE DESC
				""";

		try (Connection con = DBConnect.getConnect(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, accountId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Order o = new Order();
				o.setOrderId(rs.getInt("ID"));
				o.setTotalAmount(rs.getBigDecimal("TOTAL"));
				o.setOrderDate(rs.getDate("ORDERDATE"));
				o.setStatus(rs.getString("STATUSS"));
				o.setResId(rs.getInt("RESID"));
				list.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int countOrder() {
		String sql = "SELECT COUNT(*) FROM ORDERS";
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
	public double revenueThisMonth(int resid) {
		String sql = """
	        SELECT ISNULL(SUM(TOTAL),0)
	        FROM ORDERS
	        WHERE RESID=?
	        AND MONTH(CREATED_AT)=MONTH(GETDATE())
	        AND YEAR(CREATED_AT)=YEAR(GETDATE())
	        AND STATUS='COMPLETED'
	    """;
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, resid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return rs.getDouble(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/*public List<Map<String, Object>> getRevenueByMonth() {
		List<Map<String, Object>> list = new ArrayList<>();

		String sql = """
        SELECT 
            MONTH(ORDERDATE) AS month,
            RESID,
            SUM(TOTAL) AS revenue
        FROM ORDERS
        GROUP BY MONTH(ORDERDATE), RESID
        ORDER BY month
    """;

		try (Connection conn = DBConnect.getConnect();   //
		     PreparedStatement ps = conn.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				row.put("month", rs.getInt("month"));
				row.put("resid", rs.getInt("RESID"));
				row.put("revenue", rs.getDouble("revenue"));
				list.add(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}*/
	public List<Map<String, Object>> getRevenueByMonth() {
		List<Map<String, Object>> list = new ArrayList<>();

		String sql = """
        WITH Months AS (
            SELECT 1 AS month
            UNION SELECT 2
            UNION SELECT 3
            UNION SELECT 4
            UNION SELECT 5
            UNION SELECT 6
            UNION SELECT 7
            UNION SELECT 8
            UNION SELECT 9
            UNION SELECT 10
            UNION SELECT 11
            UNION SELECT 12
        )
        SELECT 
            m.month,
            o.RESID,
            ISNULL(SUM(o.TOTAL), 0) AS revenue
        FROM Months m
        LEFT JOIN ORDERS o 
            ON MONTH(o.ORDERDATE) = m.month
        GROUP BY m.month, o.RESID
        ORDER BY m.month
    """;

		try (Connection conn = DBConnect.getConnect();
		     PreparedStatement ps = conn.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				row.put("month", rs.getInt("month"));
				row.put("resid", rs.getInt("RESID")); // có thể null
				row.put("revenue", rs.getDouble("revenue"));
				list.add(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	public Order getOrderById(int id) {
		String sql = "SELECT * FROM ORDERS WHERE ID = ?";

		try (
				Connection conn = DBConnect.getConnect();
				PreparedStatement ps = conn.prepareStatement(sql)
		) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Order o = new Order();

				o.setOrderId(rs.getInt("ID"));
				o.setAccountId(rs.getInt("ACCOUNTID"));
				o.setResId(rs.getInt("RESID"));
				o.setAddress(rs.getString("ADDRES"));
				o.setOrderDate(rs.getDate("ORDERDATE"));
				o.setTotalAmount(rs.getBigDecimal("TOTAL"));
				o.setStatus(rs.getString("STATUSS"));

				return o;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Order getOrderDetailByOrderId(int  id) {
		String sql = "SELECT * FROM ORDERS WHERE ID = ?";

		try (
				Connection conn = DBConnect.getConnect();
				PreparedStatement ps = conn.prepareStatement(sql)
		) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Order o = new Order();
				o.setOrderId(rs.getInt("ID"));
				o.setAccountId(rs.getInt("ACCOUNTID"));
				o.setResId(rs.getInt("RESID"));
				o.setTotalAmount(rs.getBigDecimal("TOTAL"));
				o.setAddress(rs.getString("ADDRES"));
				o.setOrderDate(rs.getTimestamp("ORDERDATE"));
				o.setStatus(rs.getString("STATUSS"));

				return o;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	}
