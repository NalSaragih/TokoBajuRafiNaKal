/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.entity;


import java.time.LocalDateTime;
/**
 *
 * @author Nala
 */
public class ProductLog {
    private int id;
    private int productId;
    private LocalDateTime logDate;
    private int quantityChange;

    public ProductLog() {
    }

    public ProductLog(int id, int productId, LocalDateTime logDate, int quantityChange) {
        this.id = id;
        this.productId = productId;
        this.logDate = logDate;
        this.quantityChange = quantityChange;
    }

    // Constructor without ID (useful when recording a new log)
    public ProductLog(int productId, LocalDateTime logDate, int quantityChange) {
        this.productId = productId;
        this.logDate = logDate;
        this.quantityChange = quantityChange;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }
}
