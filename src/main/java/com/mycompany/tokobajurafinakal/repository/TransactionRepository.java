/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.repository;
import com.mycompany.tokobajurafinakal.model.CartItem;
import com.mycompany.tokobajurafinakal.model.TransactionHistoryModel;
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

                   detailStmt.setInt(1, transactionId);
                   detailStmt.setInt(2, productId);
                   detailStmt.setInt(3, qty);
                   detailStmt.setDouble(4, item.getSubtotal());
                   detailStmt.addBatch();

                   stockStmt.setInt(1, qty);
                   stockStmt.setInt(2, productId);
                   stockStmt.addBatch();

                   logStmt.setInt(1, productId);
                   logStmt.setInt(2, -qty);
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
    
    public List<TransactionHistoryModel> getTransactionHistory() {
        List<TransactionHistoryModel> history = new ArrayList<>();
        String query = "SELECT t.transaction_id, p.product_name, td.quantity, td.subtotal, t.total, t.paid "
                     + "FROM transactions t "
                     + "JOIN transaction_details td ON t.transaction_id = td.transaction_id "
                     + "LEFT JOIN products p ON td.product_id = p.product_id "
                     + "ORDER BY t.transaction_id DESC";

        try (
            Connection conn = DatabaseUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ) {
            while (rs.next()) {
                String itemName = rs.getString("product_name");
                if (itemName == null) {
                    itemName = "Deleted Product";
                }

                history.add(new TransactionHistoryModel(
                    rs.getInt("transaction_id"),
                    itemName,
                    rs.getInt("quantity"),
                    rs.getDouble("subtotal"),
                    rs.getDouble("total"),
                    rs.getDouble("paid")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }

        return history;
    }
}
