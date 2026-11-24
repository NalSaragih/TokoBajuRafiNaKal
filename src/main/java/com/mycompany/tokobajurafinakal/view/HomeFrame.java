/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Rafi
 */
public class HomeFrame extends JFrame {

    public HomeFrame() {
        setTitle("Toko Baju RafiNaKal - Home");
        setSize(600, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 20, 10, 20));

        JLabel titleLabel = new JLabel("TOKO BAJU RAFINAKAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("Pilih menu di bawah untuk mulai mengelola toko", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JButton btnShop = buatTombolMenu("Daftar Produk (Shop)");
        JButton btnInventory = buatTombolMenu("Kelola Inventory");
        JButton btnCart = buatTombolMenu("Keranjang (Cart)");
        JButton btnLog = buatTombolMenu("Log Produk");

        btnShop.addActionListener(e -> pindahHalaman(new ItemListFrame()));
        btnInventory.addActionListener(e -> pindahHalaman(new InventoryFrame()));
        btnCart.addActionListener(e -> pindahHalaman(new CartFrame()));
        btnLog.addActionListener(e -> pindahHalaman(new ItemLogFrame()));

        menuPanel.add(btnShop);
        menuPanel.add(btnInventory);
        menuPanel.add(btnCart);
        menuPanel.add(btnLog);
        add(menuPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JButton btnNewItem = new JButton("+ Tambah Produk Baru");
        btnNewItem.setPreferredSize(new Dimension(180, 35));
        btnNewItem.addActionListener(e -> pindahHalaman(new NewItem()));

        JButton btnExit = new JButton("Keluar");
        btnExit.setPreferredSize(new Dimension(100, 35));
        btnExit.addActionListener(e -> System.exit(0));

        footerPanel.add(btnNewItem);
        footerPanel.add(btnExit);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton buatTombolMenu(String teks) {
        JButton btn = new JButton(teks);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setFocusPainted(false);
        return btn;
    }

    private void pindahHalaman(JFrame targetFrame) {
        targetFrame.setLocationRelativeTo(null);
        targetFrame.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new HomeFrame().setVisible(true));
    }
}
