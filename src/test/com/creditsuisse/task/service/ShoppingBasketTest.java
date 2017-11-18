package com.creditsuisse.task.service;

import com.creditsuisse.task.domain.ProductStore;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by pankajpardasani on 18/11/2017.
 */
public class ShoppingBasketTest {

    private ShoppingBasketManager unitUnderTest = null;
    private Set<ProductStore> catalogue = null;

    @Before
    public void init() {
        catalogue = createImmutableSet(
                new ProductStore("Apple", BigDecimal.valueOf(0.35)),
                new ProductStore("Banana", BigDecimal.valueOf(0.20)),
                new ProductStore("Melon", BigDecimal.valueOf(0.50)),
                new ProductStore("Lime", BigDecimal.valueOf(0.15))
        );

        unitUnderTest = new ShoppingBasketManager(catalogue);
    }

    @Test
    public void testAddProductForShopping() {
        unitUnderTest.addProduct("Apple").addProduct("Banana").addProduct("Apple").addProduct("Banana").addProduct("Apple", "Lime", "Melon", "Apple", "Lime");
        unitUnderTest.addProduct("Melon");

        assertEquals(10, unitUnderTest.getTotalItems());
    }

    @Test(expected = Exception.class)
    public void testAddProductWhenNotRegistered() {
        unitUnderTest.addProduct("Apple");
        unitUnderTest.addProduct("Apple", "Mango", "Brocolli");
    }

    @Test
    public void testShoppingBasketTotal() {
        unitUnderTest.addProduct("Apple"); //0.35
        unitUnderTest.addProduct("Lime"); //0.15
        unitUnderTest.addProduct("Lime"); //0.15
        unitUnderTest.addProduct("Apple", "Lime", "Banana"); //0.35, 0.15, 0.20
        unitUnderTest.addProduct("Apple"); //0/35
        unitUnderTest.addProduct("Banana"); //0.20
        unitUnderTest.addProduct("Apple", "Lime", "Melon", "Apple", "Lime"); //0.35, 0.15, 0.50, 0.35, 0.15
        unitUnderTest.addProduct("Melon"); //0.50
        unitUnderTest.addProduct("Melon", "Melon", "Melon", "Lime"); //0.50, 0.50, 0.50, 0.15

        //5 melons (cost of 3 after discount) = £1.50, 5 apples (no discount) = £1.75, 6 limes (cost of 4 after discount) = £0.60, 2 bananas (no discount) = £0.40
        assertEquals(BigDecimal.valueOf(4.25), unitUnderTest.getTotalBill());
    }

    @SafeVarargs
    private final <T> Set<T> createImmutableSet(T... elements) {
        return Arrays.stream(elements).collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }
}
