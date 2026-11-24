/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
        setTitle("Toko Baju RafiNaKal - Beranda");
        setSize(620, 420);
        setMinimumSize(new Dimension(580, 390));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setJMenuBar(new NavBar(this));
        setLayout(new BorderLayout(16, 16));

        JPanel headerPanel = new JPanel(new BorderLayout(0, 6));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(26, 24, 8, 24));

        JLabel titleLabel = new JLabel("TOKO BAJU RAFINAKAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("Pilih menu di bawah untuk mulai mengelola toko", SwingConstants.CENTER);
        subtitleLabel.setFont(UiUtil.FONT_SUBTITLE);
        subtitleLabel.setForeground(UiUtil.MUTED_TEXT);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(1, 2, 24, 20));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(24, 44, 24, 44));

        JButton btnKasir = buatTombolMenu("Kasir (Mulai Transaksi)", true);
        JButton btnAdmin = buatTombolMenu("Admin (Kelola Inventory)", false);

        btnKasir.addActionListener(e -> pindahHalaman(new ItemListFrame()));
        btnAdmin.addActionListener(e -> pindahHalaman(new InventoryFrame()));

        menuPanel.add(btnKasir);
        menuPanel.add(btnAdmin);
        add(menuPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 24, 24, 24));

        JButton btnExit = UiUtil.createSecondaryButton("Keluar");
        btnExit.setPreferredSize(new Dimension(110, 36));
        btnExit.addActionListener(e -> System.exit(0));

        footerPanel.add(btnExit);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton buatTombolMenu(String teks, boolean utama) {
        JButton btn = utama ? UiUtil.createPrimaryButton(teks) : UiUtil.createSecondaryButton(teks);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
