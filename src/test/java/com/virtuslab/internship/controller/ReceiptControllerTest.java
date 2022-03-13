package com.virtuslab.internship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldCreateReceiptFromBasket() throws Exception {
        Basket basket = new Basket();
        ProductDb productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        var bread = productDb.getProduct("Bread");
        basket.addProduct(cheese);
        basket.addProduct(steak);
        basket.addProduct(bread);
        this.mockMvc.perform(post("/api/receipt").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basket))).andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnBadRequest() throws Exception {
        Basket basket = new Basket();
        this.mockMvc.perform(post("/api/receipt").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(basket))).andExpect(status().isBadRequest());
    }
}
