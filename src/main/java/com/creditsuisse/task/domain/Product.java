package com.creditsuisse.task.domain;


/**
 * Created by pankajpardasani on 16/11/2017.
 */
public class Product {

    private String name;
    private double price;
    private int quantity;
    private double discount;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
