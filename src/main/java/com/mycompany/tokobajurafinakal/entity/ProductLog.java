/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.entity;


import java.time.LocalDate;
/**
 *
 * @author Nala
 */
public class ProductLog {
    private int id;
    private int productId;
    private LocalDate logDate;
    private int quantityChange;
    private String logType;

    public ProductLog() {
    }

    public ProductLog(int id, int productId, LocalDate logDate, int quantityChange, String logType) {
        this.id = id;
        this.productId = productId;
        this.logDate = logDate;
        this.quantityChange = quantityChange;
        this.logType = logType;
    }

    public ProductLog(int productId, LocalDate logDate, int quantityChange, String logType) {
        this.productId = productId;
        this.logDate = logDate;
        this.quantityChange = quantityChange;
        this.logType = logType;
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

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public String getLogType() {
        return this.logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
