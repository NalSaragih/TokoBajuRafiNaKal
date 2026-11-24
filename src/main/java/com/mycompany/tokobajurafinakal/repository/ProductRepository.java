/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.repository;
import com.mycompany.tokobajurafinakal.entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Nala
 */
public class ProductRepository {
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        
        try (
                Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
            ){
                while(rs.next()){
                    Product p = new Product(rs.getInt("product_id"), 
                                            rs.getString("product_name"), 
                                            rs.getDouble("price"), 
                                            rs.getBytes("product_image"),
                                            rs.getInt("current_stock"),
                                            rs.getString("product_desc"));
                    products.add(p);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
            }
        
        return products;
    }
    
    public void insert(Product product){
        String query = "INSERT INTO products(product_name, price, product_image, current_stock, product_desc) VALUES(?, ?, ?, ?, ?)";
        String logSql = "INSERT INTO product_logs (product_id, log_date, quantity_change, log_type) VALUES (?, CURDATE(), ?, 'INITIAL')";
        int newProductId = -1;
        try (
                Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement logStmt = conn.prepareStatement(logSql)
            ){
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setBytes(3, product.getProductImage());
            stmt.setInt(4, product.getCurrentStock());
            stmt.setString(5, product.getProductDesc());
            stmt.executeUpdate();
            
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        newProductId = keys.getInt(1);
                    }
                }
        

            if (newProductId != -1 && product.getCurrentStock() > 0) {
                logStmt.setInt(1, newProductId);
                logStmt.setInt(2, product.getCurrentStock());
                logStmt.executeUpdate();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Insert Error: " + e.getMessage());
        }
    }
    
    public void update(Product product){
        String getOldStockSql = "SELECT current_stock FROM products WHERE product_id = ?";
        String query = "UPDATE products SET product_name = ?, price = ?, product_image = ?, current_stock = ?, product_desc = ?"
                + " WHERE product_id = ?";
        String logSql = "INSERT INTO product_logs (product_id, log_date, quantity_change, log_type) VALUES (?, CURDATE(), ?, 'ADMIN')";
        
        try (
                Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                PreparedStatement checkStmt = conn.prepareStatement(getOldStockSql);
            ){
            int oldStock = 0;
            checkStmt.setInt(1, product.getId());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    oldStock = rs.getInt("current_stock");
                }
            } 
            
            
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setBytes(3, product.getProductImage());
            stmt.setInt(4, product.getCurrentStock());
            stmt.setString(5, product.getProductDesc());
            stmt.setInt(6, product.getId());
            stmt.executeUpdate();
            
            int diff = product.getCurrentStock() - oldStock;
            if (diff != 0) {
                try (PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                    logStmt.setInt(1, product.getId());
                    logStmt.setInt(2, diff);
                    logStmt.executeUpdate();
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }
    }
    
    public void delete(Product product) {
        String logSql = "INSERT INTO product_logs (product_id, log_date, quantity_change, log_type) VALUES (?, CURDATE(), ?, 'DELETED')";
        String unlinkDetailsSql = "UPDATE transaction_details SET product_id = NULL WHERE product_id = ?";
        String deleteSql = "DELETE FROM products WHERE product_id = ?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement logStmt = conn.prepareStatement(logSql);
                 PreparedStatement unlinkStmt = conn.prepareStatement(unlinkDetailsSql);
                 PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

                logStmt.setInt(1, product.getId());
                logStmt.setInt(2, -product.getCurrentStock());
                logStmt.executeUpdate();

                unlinkStmt.setInt(1, product.getId());
                unlinkStmt.executeUpdate();

                deleteStmt.setInt(1, product.getId());
                deleteStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Delete Error: " + e.getMessage());
        }
    }
}
