/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.model;

/**
 *
 * @author Nala
 */
public class TransactionHistoryModel {
    private int transactionId;
    private String itemName;
    private int quantity;
    private double subtotal;
    private double total;
    private double paid;

    public TransactionHistoryModel(int transactionId, String itemName, int quantity, double subtotal, double total, double paid) {
        this.transactionId = transactionId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.total = total;
        this.paid = paid;
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPaid() {
        return this.paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }
}
