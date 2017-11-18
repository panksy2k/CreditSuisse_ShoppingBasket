package com.creditsuisse.task.service;

import com.creditsuisse.task.domain.Product;
import com.creditsuisse.task.domain.ProductStore;
import com.creditsuisse.task.function.Discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Created by pankajpardasani on 16/11/2017.
 */
public class ShoppingBasketManager {

    private static final Logger LOG = Logger.getLogger(ShoppingBasketManager.class.getName());

    private final Map<String, Product> productsBoughtByName;
    private final Set<ProductStore> catalogue;

    private BigDecimal totalCost = BigDecimal.ZERO;
    private int totalItems = 0;

    public ShoppingBasketManager(Set<ProductStore> catalogue) {
        this.productsBoughtByName = new HashMap<>();
        this.catalogue = catalogue;
    }

    public ShoppingBasketManager addProduct(String newProduct) {
        if(!catalogue.contains(new ProductStore(newProduct))) {
            throw new RuntimeException("Product is not registered in the store!");
        }

        totalItems++;

        if (!productsBoughtByName.containsKey(newProduct)) {
            productsBoughtByName.computeIfAbsent(newProduct,
                    productName ->
                            new Product(productName, getProductUnitPrice(allProducts -> allProducts.getProductName().equalsIgnoreCase(newProduct))))
                    .setQuantity(1);
        }
        else {
            productsBoughtByName.computeIfPresent(newProduct, (String productName, Product productExisting) -> {
                productExisting.setQuantity(productExisting.getQuantity() + 1);
                return productExisting;
            });
        }

        return this;
    }

    public ShoppingBasketManager addProduct(String... multipleProduct) {
        for(String productName : multipleProduct) {
            addProduct(productName);
        }

        return this;
    }


    public BigDecimal getTotalBill() {
        for(String productName : productsBoughtByName.keySet()) {
            Product product = productsBoughtByName.get(productName);
            product.setDiscount(getDiscount(product));

            BigDecimal totalCostBeforeDiscount = BigDecimal.valueOf(product.getQuantity() * product.getPrice());
            totalCostBeforeDiscount = totalCostBeforeDiscount.setScale(2, RoundingMode.HALF_DOWN);

            LOG.info(() -> "Product name ="+productName + ", Qty="+product.getQuantity() +", Unit Price="+product.getPrice());

            totalCost = totalCost.add(totalCostBeforeDiscount).add(BigDecimal.valueOf(product.getDiscount()));
        }

        return totalCost;
    }

    public int getTotalItems() {
        return totalItems;
    }

    private double getProductUnitPrice(Predicate<ProductStore> filterByName) {
        return catalogue.stream().filter(filterByName).findFirst().get().getUnitPrice().doubleValue();
    }

    private double getDiscount(Product shoppingProduct) {
        switch (shoppingProduct.getName()) {
            case "Melon":
                return Discount.BUY_ONE_GET_ONE_OFFER.apply(shoppingProduct);
            case "Lime":
                return Discount.THREE_FOR_TWO_OFFER.apply(shoppingProduct);
            default:
                return 0.0;
        }
    }
}
