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
        JMenu menuNav = new JMenu("Menu");

        JMenuItem itemHome = new JMenuItem("Home");
        JMenuItem itemTransaksi = new JMenuItem("Mulai Transaksi");
        JMenuItem itemInventory = new JMenuItem("Inventory");
        JMenuItem itemHistory = new JMenuItem("Riwayat Transaksi");
        JMenuItem itemLog = new JMenuItem("Log Produk");

        itemHome.addActionListener(e -> pindahHalaman(currentFrame, new HomeFrame()));
        itemTransaksi.addActionListener(e -> pindahHalaman(currentFrame, new ItemListFrame()));
        itemInventory.addActionListener(e -> pindahHalaman(currentFrame, new InventoryFrame()));
        itemHistory.addActionListener(e -> pindahHalaman(currentFrame, new TransactionHistoryFrame()));
        itemLog.addActionListener(e -> pindahHalaman(currentFrame, new ItemLogFrame()));

        menuNav.add(itemHome);
        menuNav.addSeparator();
        menuNav.add(itemTransaksi);
        menuNav.add(itemInventory);
        menuNav.add(itemHistory);
        menuNav.add(itemLog);

        this.add(menuNav);
    }

    private void pindahHalaman(JFrame currentFrame, JFrame targetFrame) {
        if (currentFrame.getClass().equals(targetFrame.getClass())) {
            return;
        }
        targetFrame.setLocationRelativeTo(null);
        targetFrame.setVisible(true);
        currentFrame.dispose();
    }
}