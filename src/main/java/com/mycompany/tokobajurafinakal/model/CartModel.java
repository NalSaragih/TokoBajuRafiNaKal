/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.model;
import com.mycompany.tokobajurafinakal.entity.Product;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Nala
 */
public class CartModel {
    private static CartModel instance;
    private final List<CartItem> items;

    private CartModel() {
        this.items = new ArrayList<>();
    }

    public static CartModel getInstance() {
        if (instance == null) {
            instance = new CartModel();
        }
        return instance;
    }

    public boolean addItem(Product product, int qty) {
        if (qty <= 0) return false;

        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                int newQty = item.getQuantity() + qty;
                if (newQty > product.getCurrentStock()) {
                    return false;
                }
                item.setQuantity(newQty);
                return true;
            }
        }

        if (qty > product.getCurrentStock()) {
            return false;
        }

        items.add(new CartItem(product, qty));
        return true;
    }

    public boolean updateQuantity(int productId, int newQty) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                if (newQty <= 0) {
                    items.remove(item);
                    return true;
                }
                if (newQty > item.getProduct().getCurrentStock()) {
                    return false;
                }
                item.setQuantity(newQty);
                return true;
            }
        }
        return false;
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
