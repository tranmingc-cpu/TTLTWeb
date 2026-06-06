package DAO;
import java.util.List;

import model.Food;
public interface FoodDAO {
        Food infomation(int id);
        void insertFood(Food food);
        void updateBySeller(Food food, int accId);
        void update(Food food);
        void delete(int id, int accId);
        List<Food> findByName(String name);
        List<Food> findByCategory(int category);
        List<Food> findALL();
        List<Food> findLimit(int limit);
        int countFood();
        List<Food> getFoodsByRestaurant(int resid);
        int countFoodByRestaurant(int resId);
        int getResIdByFoodId(int foodId);
        List<Food> getFoodBySeller(int accId);
        Food getFoodbyFoodId(int id);
        void deleteFood(int id);
        List<Food> searchAdvanced(String keyword, Integer categoryId, Double minPrice, Double maxPrice);
        List<Food> findByPage(int page, int pageSize);
    }

