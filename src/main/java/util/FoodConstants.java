package util;

import java.io.InputStream;
import java.util.Properties;

public class FoodConstants {

    public static String GHN_API_TOKEN;
    public static int GHN_SHOP_ID;
    public static int GHN_FROM_DISTRICT_ID;
    public static String GHN_FROM_WARD_CODE;

    static {
        Properties pro = new Properties();
        try (InputStream input = FoodConstants.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                pro.load(input);
                GHN_API_TOKEN = pro.getProperty("ghn.api.token", "").trim();
                GHN_SHOP_ID = Integer.parseInt(pro.getProperty("ghn.shop.id", "0").trim());
                GHN_FROM_DISTRICT_ID = Integer.parseInt(pro.getProperty("ghn.from.district.id", "0").trim());
                GHN_FROM_WARD_CODE = pro.getProperty("ghn.from.ward.code", "").trim();
            } else {
                throw new RuntimeException("Không tìm thấy config.properties");
            }

        } catch (Exception e) {
            throw new RuntimeException("Lỗi load cấu hình GHN", e);
        }
    }
    private FoodConstants() {
    }
}