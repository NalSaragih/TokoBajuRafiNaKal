/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.entity;

/**
 *
 * @author Nala
 */
public class Transaction {
    private int id;
    private LocalDateTime transactionDate;
    private String customerName;
    private double totalAmount;

    public Transaction() {
    }

    public Transaction(int id, LocalDateTime transactionDate, String customerName, double totalAmount) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
    }

    public Transaction(LocalDateTime transactionDate, String customerName, double totalAmount) {
        this.transactionDate = transactionDate;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}