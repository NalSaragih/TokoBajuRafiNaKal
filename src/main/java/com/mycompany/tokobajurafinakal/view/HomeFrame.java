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
import java.awt.GridBagLayout;
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
        setSize(650, 440);
        setMinimumSize(new Dimension(620, 420));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setJMenuBar(new NavBar(this));
        setLayout(new BorderLayout(0, 12));

        JPanel headerPanel = new JPanel(new BorderLayout(0, 6));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(28, 30, 8, 30));

        JLabel titleLabel = new JLabel("TOKO BAJU RAFINAKAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("Sistem Kasir & Manajemen Stok Pakaian", SwingConstants.CENTER);
        subtitleLabel.setFont(UiUtil.FONT_SUBTITLE);
        subtitleLabel.setForeground(UiUtil.MUTED_TEXT);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        JPanel menuGrid = new JPanel(new GridLayout(2, 2, 16, 14));

        JButton btnTransaksi = buatTombolMenu("Mulai Transaksi", "Katalog & Kasir Penjualan", true);
        JButton btnInventory = buatTombolMenu("Kelola Inventory", "Tambah, Edit & Hapus Stok", false);
        JButton btnHistory = buatTombolMenu("Riwayat Transaksi", "Daftar Transaksi Penjualan", false);
        JButton btnLog = buatTombolMenu("Log Aktivitas Stok", "Catatan Stok Masuk & Keluar", false);

        btnTransaksi.addActionListener(e -> pindahHalaman(new ItemListFrame()));
        btnInventory.addActionListener(e -> pindahHalaman(new InventoryFrame()));
        btnHistory.addActionListener(e -> pindahHalaman(new TransactionHistoryFrame()));
        btnLog.addActionListener(e -> pindahHalaman(new ItemLogFrame()));

        menuGrid.add(btnTransaksi);
        menuGrid.add(btnInventory);
        menuGrid.add(btnHistory);
        menuGrid.add(btnLog);

        centerWrapper.add(menuGrid);
        add(centerWrapper, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(4, 25, 24, 25));

        JButton btnNewItem = UiUtil.createSecondaryButton("+ Tambah Produk Baru");
        btnNewItem.setPreferredSize(new Dimension(180, 36));
        btnNewItem.addActionListener(e -> pindahHalaman(new NewItem()));

        JButton btnExit = UiUtil.createSecondaryButton("Keluar");
        btnExit.setPreferredSize(new Dimension(105, 36));
        btnExit.addActionListener(e -> System.exit(0));

        footerPanel.add(btnNewItem);
        footerPanel.add(btnExit);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton buatTombolMenu(String judul, String subjudul, boolean utama) {
        String warnaSub = utama ? "#dbeafe" : "#a5aab2";
        String html = "<html><div style='text-align:center;'>"
                + "<span style='font-size:12px;font-weight:bold;'>" + judul + "</span><br>"
                + "<span style='font-size:9px;color:" + warnaSub + ";'>" + subjudul + "</span>"
                + "</div></html>";
        JButton btn = utama ? UiUtil.createPrimaryButton(html) : UiUtil.createSecondaryButton(html);
        btn.setPreferredSize(new Dimension(250, 74));
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
