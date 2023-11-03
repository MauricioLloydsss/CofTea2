package com.example.buyproducts;

import java.util.List;

public class QueueEntry {
    private List<Cart> products;

    private String id;
    private double totalPayment;
    private String customerName;
    private String customerPhone;


    public QueueEntry(double totalPayment, String customerName, String customerPhone) {
        this.products = products;
        this.totalPayment = Double.parseDouble(String.valueOf(totalPayment));
        this.customerName = customerName;
        this.customerPhone = String.valueOf(customerPhone);
        this.id = String.valueOf(id);
    }


    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}