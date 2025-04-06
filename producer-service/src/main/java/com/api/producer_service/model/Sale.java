package com.api.producer_service.model;

import java.time.LocalDate;

public class Sale {
    private int item;
    private String productName;
    private double unitPrice;
    private int quantity;
    private LocalDate saleDate;
    private double total;

    public Sale() {
    }

    public Sale(int item, String productName, double unitPrice, int quantity, LocalDate saleDate, double total) {
        this.item = item;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.saleDate = saleDate;
        this.total = total;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
