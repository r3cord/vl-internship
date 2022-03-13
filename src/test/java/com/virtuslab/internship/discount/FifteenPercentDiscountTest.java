package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenPercentDiscountTest {

    @Test
    void shouldApplyMultipleDiscounts() throws Exception {
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(steak, 1));
        receiptEntries.add(new ReceiptEntry(bread, 2));

        var receipt = new Receipt(receiptEntries);
        var tenDiscount = new TenPercentDiscount();
        var receiptAfterDiscount = tenDiscount.apply(receipt);
        var fifteenDiscount = new FifteenPercentDiscount();
        receiptAfterDiscount = fifteenDiscount.apply(receiptAfterDiscount);
        var expectedTotalPrice = cheese.price().add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .add(steak.price()).add(bread.price().multiply(BigDecimal.valueOf(2))).multiply(BigDecimal.valueOf(0.85))
                        .multiply(BigDecimal.valueOf(0.9));

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApplyDiscount() throws Exception {
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var apple = productDb.getProduct("Apple");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(apple, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();
        var receiptAfterDiscount = discount.apply(receipt);
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(2)).add(apple.price()
                .multiply(BigDecimal.valueOf(2))).add(cereals.price()).multiply(BigDecimal.valueOf(0.85));

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApplyDiscount() throws Exception {
        var productDb = new ProductDb();
        var apple = productDb.getProduct("Apple");
        var pork = productDb.getProduct("Pork");
        var potato = productDb.getProduct("Potato");
        var bread = productDb.getProduct("Bread");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(apple, 2));
        receiptEntries.add(new ReceiptEntry(pork, 1));
        receiptEntries.add(new ReceiptEntry(potato, 1));
        receiptEntries.add(new ReceiptEntry(bread, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();
        var receiptAfterDiscount = discount.apply(receipt);
        var expectedTotalPrice = apple.price().multiply(BigDecimal.valueOf(2)).add(pork.price())
                .add(potato.price()).add(bread.price().multiply(BigDecimal.valueOf(2)));

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}
