package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;

public class FifteenPercentDiscount {

    public static String NAME = "FifteenPercentDiscount";

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            if(receipt.discounts().contains("TenPercentDiscount")) {
                receipt = new Receipt(receipt.entries());
                var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
                var discounts = receipt.discounts();
                discounts.add(NAME);
                receipt = new Receipt(receipt.entries(), discounts, totalPrice);

                var discount = new TenPercentDiscount();
                receipt = discount.apply(receipt);
            }
            else {
                var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
                var discounts = receipt.discounts();
                discounts.add(NAME);
                receipt = new Receipt(receipt.entries(), discounts, totalPrice);
            }
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        int count = 0;
        for (ReceiptEntry receiptEntry:
             receipt.entries()) {
            if(receiptEntry.product().type() == Product.Type.GRAINS) {
                count += receiptEntry.quantity();
                if(count >= 3)
                    return true;
            }
        }
        return false;
    }
}
