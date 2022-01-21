package inputfiles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.Category;

public final class Gift {
    private String productName;
    private Double price;
    private Category category;

    // quantity of the gift
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer quantity;

    public Gift() {
        this.productName = null;
        this.price = 0.0;
        this.category = null;
        this.quantity = 0;
    }
    public Gift(final Gift newGift) {
        this.productName = newGift.getProductName();
        this.price = newGift.getPrice();
        this.category = newGift.getCategory();
        this.quantity = newGift.getQuantity();
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

    @JsonIgnore
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }
}
