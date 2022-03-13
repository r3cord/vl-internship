package com.virtuslab.internship.basket;

import com.virtuslab.internship.product.Product;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    @NotEmpty
    private List<Product> products;

    public Basket() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}
