package dataprocessing;

import enums.Category;

public final class Gift {
    private String productName;
    private Double price;
    private Category category;

    public Gift() {
        this.productName = null;
        this.price = 0.0;
        this.category = null;
    }
    public Gift(final Gift newGift) {
        this.productName = newGift.getProductName();
        this.price = newGift.getPrice();
        this.category = newGift.getCategory();
    }

    public Gift(final String productName, final Double price, final Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }
}
