package com.shop.online.simple.entity.dto;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class ProductCreationDTO {

    @NotEmpty(message = "{error.message.product.name.empty}")
    @Size(min = 5, max = 20, message = "{error.message.product.name.size}")
    private String name;

    @NotEmpty(message = "{error.message.product.description.empty}")
    @Size(min = 5, max = 300, message = "{error.message.product.description.size}")
    private String description;

    @DecimalMin(value = "0.0", message = "{error.message.product.price.range}")
    @DecimalMax(value = "999999.9", message = "{error.message.product.price.range}")
    @Digits(integer = 6, fraction = 2, message = "{error.message.product.price.fraction}")
    private BigDecimal price = new BigDecimal("0.0");
    private long sellerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCreationDTO that = (ProductCreationDTO) o;
        return sellerId == that.sellerId && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, sellerId);
    }
}
