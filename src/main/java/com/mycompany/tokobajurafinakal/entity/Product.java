/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.entity;

/**
 *
 * @author Nala
 */

public class Product {
    private int id;
    private String productName;
    private double price;
    private byte[] productImage; // BLOB
    private int currentStock;

    // Constructor, Getters, and Setters only

    public Product() {
    }

    public Product(int id, String productName, double price, byte[] productImage, int currentStock) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.currentStock = currentStock;
    }

    public Product( String productName, double price, byte[] productImage, int currentStock) {
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.currentStock = currentStock;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getProductImage() {
        return this.productImage;
    }
    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public int getCurrentStock() {
        return this.currentStock;
    }
    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

}