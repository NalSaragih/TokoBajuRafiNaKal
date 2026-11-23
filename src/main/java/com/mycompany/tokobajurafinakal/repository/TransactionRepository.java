/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.repository;
import com.mycompany.tokobajurafinakal.model.CartItem;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Nala
 */
public class TransactionRepository {
    public boolean checkout(List<CartItem> items, double total, double paid) {
        String insertTransactionSql = "INSERT INTO transactions (transaction_date, total, paid) VALUES (CURDATE(), ?, ?)";
        String insertDetailSql = "INSERT INTO transaction_details (transaction_id, product_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
        String updateStockSql = "UPDATE products SET current_stock = current_stock - ? WHERE product_id = ?";
        String insertLogSql = "INSERT INTO product_logs (product_id, log_date, quantity_change, log_type) VALUES (?, CURDATE(), ?, 'SALE')";
        
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            int transactionId = -1;
            try (PreparedStatement stmt = conn.prepareStatement(insertTransactionSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDouble(1, total);
                stmt.setDouble(2, paid);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        transactionId = rs.getInt(1);
                    }
                }
            }

            if (transactionId == -1) {
                throw new SQLException("Failed to retrieve generated transaction ID.");
            }

            try (PreparedStatement detailStmt = conn.prepareStatement(insertDetailSql);
                PreparedStatement stockStmt = conn.prepareStatement(updateStockSql);
                PreparedStatement logStmt = conn.prepareStatement(insertLogSql)) {

               for (CartItem item : items) {
                   int productId = item.getProduct().getId();
                   int qty = item.getQuantity();

                   // Transaction details
                   detailStmt.setInt(1, transactionId);
                   detailStmt.setInt(2, productId);
                   detailStmt.setInt(3, qty);
                   detailStmt.setDouble(4, item.getSubtotal());
                   detailStmt.addBatch();

                   // Deduct stock
                   stockStmt.setInt(1, qty);
                   stockStmt.setInt(2, productId);
                   stockStmt.addBatch();

                   // Product log
                   logStmt.setInt(1, productId);
                   logStmt.setInt(2, -qty); // Negative to show stock decreased (remove '-' if you prefer positive)
                   logStmt.addBatch();
               }

               detailStmt.executeBatch();
               stockStmt.executeBatch();
               logStmt.executeBatch();
           }

           conn.commit();
           return true;

       } catch (SQLException e) {
           e.printStackTrace();
           if (conn != null) {
               try {
                   conn.rollback();
               } catch (SQLException ex) {
                   ex.printStackTrace();
               }
           }
           javax.swing.JOptionPane.showMessageDialog(null, "Checkout Error: " + e.getMessage());
           return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
