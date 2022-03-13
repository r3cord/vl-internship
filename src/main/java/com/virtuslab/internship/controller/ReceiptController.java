package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {
    @PostMapping
    public ResponseEntity<Receipt> getReceiptFromBasket(@Validated @RequestBody Basket basket) {
        try {
            ReceiptGenerator receiptGenerator = new ReceiptGenerator();
            Receipt receipt = receiptGenerator.generate(basket);
            FifteenPercentDiscount fifteenPercentDiscount = new FifteenPercentDiscount();
            TenPercentDiscount tenPercentDiscount = new TenPercentDiscount();
            receipt = fifteenPercentDiscount.apply(receipt);
            receipt = tenPercentDiscount.apply(receipt);
            return new ResponseEntity<>(receipt, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
