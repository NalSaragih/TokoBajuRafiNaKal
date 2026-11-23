/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.repository;
import com.mycompany.tokobajurafinakal.entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

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
        try (
                Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
            ){
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setBytes(3, product.getProductImage());
            stmt.setInt(4, product.getCurrentStock());
            stmt.setString(5, product.getProductDesc());
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void update(Product product){
        String query = "UPDATE products SET product_name = ?, price = ?, product_image = ?, current_stock = ?, product_desc = ?"
                + " WHERE product_id = ?";
        try (
                Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
            ){
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setBytes(3, product.getProductImage());
            stmt.setInt(4, product.getCurrentStock());
            stmt.setString(5, product.getProductDesc());
            stmt.setInt(6, product.getId());
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }
    }
}
