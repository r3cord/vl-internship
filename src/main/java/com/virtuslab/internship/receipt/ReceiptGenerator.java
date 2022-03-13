package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        var receipt = getReceipt(basket);
        return receipt;
    }

    private Receipt getReceipt(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        Map<Product, Integer> productsInBasket = new LinkedHashMap<>();

        boolean productAdded;
        for (Product product:
             basket.getProducts()) {
            productAdded = false;
            for(int i = 0; i < receiptEntries.size(); i++) {
                if(receiptEntries.get(i).product().equals(product)) {
                    receiptEntries.set(i, new ReceiptEntry(product, receiptEntries.get(i).quantity() + 1));
                    productAdded = true;
                    break;
                }
            }
            if(productAdded)
                continue;
            else
                receiptEntries.add(new ReceiptEntry(product, 1));
        }
        return new Receipt(receiptEntries);
    }
}
