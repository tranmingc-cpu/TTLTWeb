package DAO;
import java.util.List;

import model.Food;
public interface FoodDAO {
public void insert (Food food);
public void update(Food food);
public void delete(int id,int restId);
public List<Food> findByName(String name);
public  List<Food> findByCategory(int category);
public List<Food> findALL();
public List<Food>findLimit (int limit);
}
