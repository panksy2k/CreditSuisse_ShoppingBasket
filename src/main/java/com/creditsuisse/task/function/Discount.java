package com.creditsuisse.task.function;

import com.creditsuisse.task.domain.Product;

import java.util.function.Function;

/**
 * Created by pankajpardasani on 18/11/2017.
 */
public class Discount {
    public static final Function<Product, Double> BUY_ONE_GET_ONE_OFFER = p -> p.getQuantity() >= 2? p.getPrice() * (p.getQuantity() / 2) * -1 : 0.0d;
    public static final Function<Product, Double> THREE_FOR_TWO_OFFER = p -> p.getQuantity() >= 3? p.getPrice() * (p.getQuantity() / 3) * -1 : 0.0d;
}
