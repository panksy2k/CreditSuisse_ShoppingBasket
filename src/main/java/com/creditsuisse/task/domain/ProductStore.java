package com.creditsuisse.task.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by pankajpardasani on 16/11/2017.
 */
public class ProductStore {

    private final String productName;
    private BigDecimal unitPrice;

    public ProductStore(String productName) {
        this.productName = productName;
    }

    public ProductStore(String productName, BigDecimal unitPrice) {
        this.productName = productName;
        this.unitPrice = unitPrice;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductStore)) return false;
        ProductStore that = (ProductStore) o;
        return Objects.equals(getProductName(), that.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductName());
    }
}
