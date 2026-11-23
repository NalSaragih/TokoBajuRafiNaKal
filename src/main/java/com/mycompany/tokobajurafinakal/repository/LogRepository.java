/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.repository;
import com.mycompany.tokobajurafinakal.model.ProductLogModel;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Nala
 */
public class LogRepository {
    public List<ProductLogModel> getAllProductLogs() {
        List<ProductLogModel> productLogs = new ArrayList<>();
        String query = "SELECT pl.log_id, pl.product_id, p.product_name, pl.log_date, pl.quantity_change, pl.log_type "
                     + "FROM product_logs pl "
                     + "LEFT JOIN products p ON pl.product_id = p.product_id "
                     + "ORDER BY pl.log_id DESC";

        try (
            Connection conn = DatabaseUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ) {
            while (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("log_date");
                java.time.LocalDate logDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                String productName = rs.getString("product_name");
                if (productName == null) {
                    productName = "Deleted Product";
                }

                ProductLogModel pl = new ProductLogModel(
                    rs.getInt("log_id"),
                    rs.getInt("product_id"),
                    logDate,
                    rs.getInt("quantity_change"),
                    rs.getString("log_type"),
                    productName
                );
                productLogs.add(pl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }

        return productLogs;
    }              
}
