/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Rafi
 */

public class NavBar extends JMenuBar {

    public NavBar(JFrame currentFrame) {
        // Buat menu utama
        JMenu menuNav = new JMenu("Menu");

        // Buat item navigasi
        JMenuItem itemShop = new JMenuItem("Daftar Produk (Shop)");
        JMenuItem itemInventory = new JMenuItem("Inventory");
        JMenuItem itemCart = new JMenuItem("Keranjang (Cart)");
        JMenuItem itemLog = new JMenuItem("Log Produk");

        // Aksi saat diklik
        itemShop.addActionListener(e -> pindahHalaman(currentFrame, new ItemListFrame()));
        itemInventory.addActionListener(e -> pindahHalaman(currentFrame, new InventoryFrame()));
        itemCart.addActionListener(e -> pindahHalaman(currentFrame, new CartFrame()));
        itemLog.addActionListener(e -> pindahHalaman(currentFrame, new ItemLogFrame()));

        // Masukkan item ke dalam menu
        menuNav.add(itemShop);
        menuNav.add(itemInventory);
        menuNav.add(itemCart);
        menuNav.add(itemLog);

        // Tambahkan menu ke NavBar
        this.add(menuNav);
    }

    private void pindahHalaman(JFrame currentFrame, JFrame targetFrame) {
        // Cegah buka ulang halaman yang sedang aktif
        if (currentFrame.getClass().equals(targetFrame.getClass())) {
            return;
        }
        targetFrame.setLocationRelativeTo(null);
        targetFrame.setVisible(true);
        currentFrame.dispose();
    }
}