package com.example.buyproducts;

public class ModelManageProduct {
    private String price;
    private String name;
    private String id;
    private String imageUrl;

    public ModelManageProduct() {
        // Default constructor required for DataSnapshot.getValue(Product.class)
    }

    public ModelManageProduct(String price, String name, String id, String imageUrl) {
        this.price = price;
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
