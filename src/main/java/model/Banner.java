package model;

public class Banner {
    private int id;
    private String title;
    private String images;
    private int foodId;
    private boolean status;

    public Banner() {
        this.status = true;
    }

    public Banner(int id, String title, String images, int foodId, boolean status) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.foodId = foodId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", images='" + images + '\'' +
                ", foodId=" + foodId +
                ", status=" + status +
                '}';
    }
}