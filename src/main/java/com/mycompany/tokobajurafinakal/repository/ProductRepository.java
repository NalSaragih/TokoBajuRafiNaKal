/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.repository;
import com.mycompany.tokobajurafinakal.repository;
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
                    Product p = new Product(rs.getInt("id"), 
                                            rs.getString("name"), 
                                            rs.getInt("price"), 
                                            rs.getInt("stock"));
                    products.add(p);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        
        return products;
    }
    
    public void insert(Product product){
        String query = "INSERT INTO products(name, price, stock) VALUES(?, ?, ?)";
        try (
                Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
            ){
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
