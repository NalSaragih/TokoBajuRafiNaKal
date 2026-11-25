/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import com.mycompany.tokobajurafinakal.entity.Product;
import com.mycompany.tokobajurafinakal.model.CartItem;
import com.mycompany.tokobajurafinakal.model.CartModel;
import com.mycompany.tokobajurafinakal.repository.ProductRepository;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nala
 */
public class ItemListFrame extends javax.swing.JFrame {

    private List<Product> allProducts = new ArrayList<>();
    private List<Product> displayedProducts = new ArrayList<>();
    private final ProductRepository productRepository = new ProductRepository();

    public ItemListFrame() {
        initComponents();
        setupCustomUi();
        loadProductsToTable();
        updateCartBadge();
    }

    private void setupCustomUi() {
        setMinimumSize(new Dimension(760, 480));
        setJMenuBar(new NavBar(this));

        UiUtil.styleSecondaryButton(backBtn);
        UiUtil.styleSecondaryButton(refreshBtn);
        UiUtil.styleSecondaryButton(historyBtn);
        UiUtil.stylePrimaryButton(cartBtn);

        searchField.putClientProperty("JTextField.placeholderText", "Cari nama produk...");
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterProducts();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterProducts();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterProducts();
            }
        });

        UiUtil.styleTable(productTable);
        setupTableColumns();
    }

    private void setupTableColumns() {
        var colModel = productTable.getColumnModel();
        colModel.getColumn(1).setCellRenderer(UiUtil.centerRenderer());
        colModel.getColumn(2).setCellRenderer(UiUtil.rightRenderer());

        colModel.getColumn(3).setCellRenderer(new ButtonRenderer());
        colModel.getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), row -> {
            if (row >= 0 && row < displayedProducts.size()) {
                Product selected = displayedProducts.get(row);
                ItemDetailFrame detailFrame = new ItemDetailFrame(selected);
                detailFrame.setLocationRelativeTo(this);
                detailFrame.setVisible(true);
            }
        }));

        colModel.getColumn(4).setCellRenderer(new ButtonRenderer());
        colModel.getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), row -> {
            if (row >= 0 && row < displayedProducts.size()) {
                Product selected = displayedProducts.get(row);
                boolean added = CartModel.getInstance().addItem(selected, 1);
                if (added) {
                    updateCartBadge();
                    JOptionPane.showMessageDialog(this,
                            "\"" + selected.getProductName() + "\" masuk ke keranjang!");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Stok tidak mencukupi (tersisa " + selected.getCurrentStock() + ")!",
                            "Stok Tidak Cukup",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }));
    }

    private void loadProductsToTable() {
        allProducts = productRepository.getAllProducts();
        filterProducts();
    }

    private void filterProducts() {
        if (productTable.isEditing()) {
            productTable.getCellEditor().stopCellEditing();
        }
        String keyword = searchField.getText().trim().toLowerCase(Locale.ROOT);
        displayedProducts = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);

        for (Product p : allProducts) {
            String name = p.getProductName() != null ? p.getProductName() : "";
            if (keyword.isEmpty() || name.toLowerCase(Locale.ROOT).contains(keyword)) {
                displayedProducts.add(p);
                model.addRow(new Object[]{
                    name,
                    p.getCurrentStock(),
                    UiUtil.formatRupiah(p.getPrice()),
                    "Detail",
                    "+ Keranjang"
                });
            }
        }
    }

    private void updateCartBadge() {
        int totalItems = 0;
        for (CartItem item : CartModel.getInstance().getItems()) {
            totalItems += item.getQuantity();
        }
        cartBtn.setText("Lihat Keranjang (" + totalItems + ")");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        refreshBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        historyBtn = new javax.swing.JButton();
        cartBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Toko Baju RafiNaKal - Katalog Produk");

        mainPanel.setBackground(new java.awt.Color(43, 45, 48));

        backBtn.setBackground(new java.awt.Color(72, 76, 82));
        backBtn.setForeground(new java.awt.Color(235, 238, 242));
        backBtn.setText("← Beranda");
        backBtn.addActionListener(this::backBtnActionPerformed);

        titleLabel.setForeground(new java.awt.Color(235, 238, 242));
        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        titleLabel.setText("Katalog Produk");

        refreshBtn.setBackground(new java.awt.Color(72, 76, 82));
        refreshBtn.setForeground(new java.awt.Color(235, 238, 242));
        refreshBtn.setText("Muat Ulang");
        refreshBtn.addActionListener(this::refreshBtnActionPerformed);

        productTable.setBackground(new java.awt.Color(52, 55, 60));
        productTable.setForeground(new java.awt.Color(235, 238, 242));
        productTable.setRowHeight(36);
        productTable.setSelectionBackground(new java.awt.Color(38, 117, 191));
        productTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Produk", "Stok", "Harga", "Detail", "Aksi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(productTable);
        if (productTable.getColumnModel().getColumnCount() > 0) {
            productTable.getColumnModel().getColumn(0).setMinWidth(180);
            productTable.getColumnModel().getColumn(0).setPreferredWidth(260);
            productTable.getColumnModel().getColumn(1).setMinWidth(65);
            productTable.getColumnModel().getColumn(1).setPreferredWidth(75);
            productTable.getColumnModel().getColumn(1).setMaxWidth(95);
            productTable.getColumnModel().getColumn(2).setMinWidth(100);
            productTable.getColumnModel().getColumn(2).setPreferredWidth(135);
            productTable.getColumnModel().getColumn(3).setMinWidth(85);
            productTable.getColumnModel().getColumn(3).setPreferredWidth(95);
            productTable.getColumnModel().getColumn(3).setMaxWidth(110);
            productTable.getColumnModel().getColumn(4).setMinWidth(105);
            productTable.getColumnModel().getColumn(4).setPreferredWidth(120);
            productTable.getColumnModel().getColumn(4).setMaxWidth(135);
        }

        historyBtn.setBackground(new java.awt.Color(72, 76, 82));
        historyBtn.setForeground(new java.awt.Color(235, 238, 242));
        historyBtn.setText("Riwayat Transaksi");
        historyBtn.addActionListener(this::historyBtnActionPerformed);

        cartBtn.setBackground(new java.awt.Color(38, 117, 191));
        cartBtn.setForeground(new java.awt.Color(255, 255, 255));
        cartBtn.setText("Lihat Keranjang (0)");
        cartBtn.addActionListener(this::cartBtnActionPerformed);

        searchField.setBackground(new java.awt.Color(58, 61, 66));
        searchField.setForeground(new java.awt.Color(235, 238, 242));
        searchField.setCaretColor(new java.awt.Color(235, 238, 242));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(titleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(historyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleLabel)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addGap(14, 14, 14)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(historyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        HomeFrame home = new HomeFrame();
        home.setLocationRelativeTo(null);
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        loadProductsToTable();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void historyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyBtnActionPerformed
        TransactionHistoryFrame historyFrame = new TransactionHistoryFrame();
        historyFrame.setLocationRelativeTo(this);
        historyFrame.setVisible(true);
    }//GEN-LAST:event_historyBtnActionPerformed

    private void cartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartBtnActionPerformed
        CartFrame cartFrame = new CartFrame();
        cartFrame.setLocationRelativeTo(null);
        cartFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cartBtnActionPerformed

    public static void main(String args[]) {
        com.formdev.flatlaf.FlatDarkLaf.setup();
        java.awt.EventQueue.invokeLater(() -> new ItemListFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JButton cartBtn;
    private javax.swing.JButton historyBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTable productTable;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
