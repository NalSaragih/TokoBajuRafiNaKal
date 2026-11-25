/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import com.mycompany.tokobajurafinakal.entity.Product;
import com.mycompany.tokobajurafinakal.repository.ProductRepository;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nala
 */
public class InventoryFrame extends javax.swing.JFrame {

    private List<Product> allProducts = new ArrayList<>();
    private List<Product> displayedProducts = new ArrayList<>();
    private final ProductRepository productRepository = new ProductRepository();

    public InventoryFrame() {
        initComponents();
        setupCustomUi();
        loadProductsToTable();
    }

    private void setupCustomUi() {
        setMinimumSize(new Dimension(780, 480));
        setJMenuBar(new NavBar(this));

        UiUtil.styleSecondaryButton(backBtn);
        UiUtil.styleSecondaryButton(refreshBtn);
        UiUtil.styleSecondaryButton(viewLogBtn);
        UiUtil.styleSecondaryButton(viewHistoryBtn);
        UiUtil.stylePrimaryButton(newItemBtn);

        searchField.putClientProperty("JTextField.placeholderText", "Cari produk...");
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

        UiUtil.styleTable(inventoryTable);
        setupTableColumns();
    }

    private void setupTableColumns() {
        var colModel = inventoryTable.getColumnModel();
        colModel.getColumn(0).setCellRenderer(UiUtil.centerRenderer());
        colModel.getColumn(2).setCellRenderer(UiUtil.centerRenderer());
        colModel.getColumn(3).setCellRenderer(UiUtil.rightRenderer());

        colModel.getColumn(4).setCellRenderer(new ButtonRenderer());
        colModel.getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), row -> {
            if (row >= 0 && row < displayedProducts.size()) {
                Product selected = displayedProducts.get(row);
                ItemDetailFrame detailFrame = new ItemDetailFrame(selected);
                detailFrame.setLocationRelativeTo(this);
                detailFrame.setVisible(true);
            }
        }));

        colModel.getColumn(5).setCellRenderer(new ButtonRenderer());
        colModel.getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), row -> {
            if (row >= 0 && row < displayedProducts.size()) {
                Product selected = displayedProducts.get(row);
                EditItemFrame editFrame = new EditItemFrame(selected, this);
                editFrame.setLocationRelativeTo(this);
                editFrame.setVisible(true);
            }
        }));

        colModel.getColumn(6).setCellRenderer(new ButtonRenderer());
        colModel.getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), row -> {
            if (row >= 0 && row < displayedProducts.size()) {
                Product selected = displayedProducts.get(row);
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Yakin ingin menghapus produk \"" + selected.getProductName() + "\"?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    productRepository.delete(selected);
                    SwingUtilities.invokeLater(this::loadProductsToTable);
                }
            }
        }));
    }

    public void loadProductsToTable() {
        allProducts = productRepository.getAllProducts();
        filterProducts();
    }

    private void filterProducts() {
        if (inventoryTable.isEditing()) {
            inventoryTable.getCellEditor().stopCellEditing();
        }
        String keyword = searchField.getText().trim().toLowerCase(Locale.ROOT);
        displayedProducts = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.setRowCount(0);

        for (Product p : allProducts) {
            String name = p.getProductName() != null ? p.getProductName() : "";
            if (keyword.isEmpty() || name.toLowerCase(Locale.ROOT).contains(keyword)) {
                displayedProducts.add(p);
                model.addRow(new Object[]{
                    p.getId(),
                    name,
                    p.getCurrentStock(),
                    UiUtil.formatRupiah(p.getPrice()),
                    "Detail",
                    "Edit",
                    "Hapus"
                });
            }
        }
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
        inventoryTable = new javax.swing.JTable();
        viewLogBtn = new javax.swing.JButton();
        viewHistoryBtn = new javax.swing.JButton();
        newItemBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Toko Baju RafiNaKal - Kelola Inventory");

        mainPanel.setBackground(new java.awt.Color(43, 45, 48));

        backBtn.setBackground(new java.awt.Color(72, 76, 82));
        backBtn.setForeground(new java.awt.Color(235, 238, 242));
        backBtn.setText("← Beranda");
        backBtn.addActionListener(this::backBtnActionPerformed);

        titleLabel.setForeground(new java.awt.Color(235, 238, 242));
        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        titleLabel.setText("Kelola Inventory Produk");

        refreshBtn.setBackground(new java.awt.Color(72, 76, 82));
        refreshBtn.setForeground(new java.awt.Color(235, 238, 242));
        refreshBtn.setText("Muat Ulang");
        refreshBtn.addActionListener(this::refreshBtnActionPerformed);

        inventoryTable.setBackground(new java.awt.Color(52, 55, 60));
        inventoryTable.setForeground(new java.awt.Color(235, 238, 242));
        inventoryTable.setRowHeight(36);
        inventoryTable.setSelectionBackground(new java.awt.Color(38, 117, 191));
        inventoryTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        inventoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama Produk", "Stok", "Harga", "Detail", "Edit", "Hapus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        inventoryTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(inventoryTable);
        if (inventoryTable.getColumnModel().getColumnCount() > 0) {
            inventoryTable.getColumnModel().getColumn(0).setMinWidth(45);
            inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            inventoryTable.getColumnModel().getColumn(0).setMaxWidth(65);
            inventoryTable.getColumnModel().getColumn(1).setMinWidth(180);
            inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(240);
            inventoryTable.getColumnModel().getColumn(2).setMinWidth(60);
            inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(70);
            inventoryTable.getColumnModel().getColumn(2).setMaxWidth(85);
            inventoryTable.getColumnModel().getColumn(3).setMinWidth(105);
            inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(130);
            inventoryTable.getColumnModel().getColumn(4).setMinWidth(75);
            inventoryTable.getColumnModel().getColumn(4).setPreferredWidth(85);
            inventoryTable.getColumnModel().getColumn(4).setMaxWidth(95);
            inventoryTable.getColumnModel().getColumn(5).setMinWidth(70);
            inventoryTable.getColumnModel().getColumn(5).setPreferredWidth(80);
            inventoryTable.getColumnModel().getColumn(5).setMaxWidth(90);
            inventoryTable.getColumnModel().getColumn(6).setMinWidth(75);
            inventoryTable.getColumnModel().getColumn(6).setPreferredWidth(85);
            inventoryTable.getColumnModel().getColumn(6).setMaxWidth(95);
        }

        viewLogBtn.setBackground(new java.awt.Color(72, 76, 82));
        viewLogBtn.setForeground(new java.awt.Color(235, 238, 242));
        viewLogBtn.setText("Log Stok Produk");
        viewLogBtn.addActionListener(this::viewLogBtnActionPerformed);

        viewHistoryBtn.setBackground(new java.awt.Color(72, 76, 82));
        viewHistoryBtn.setForeground(new java.awt.Color(235, 238, 242));
        viewHistoryBtn.setText("Riwayat Transaksi");
        viewHistoryBtn.addActionListener(this::viewHistoryBtnActionPerformed);

        newItemBtn.setBackground(new java.awt.Color(38, 117, 191));
        newItemBtn.setForeground(new java.awt.Color(255, 255, 255));
        newItemBtn.setText("+ Tambah Produk Baru");
        newItemBtn.addActionListener(this::newItemBtnActionPerformed);

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(titleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(viewLogBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(viewHistoryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(newItemBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(viewLogBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewHistoryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newItemBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void viewLogBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewLogBtnActionPerformed
        ItemLogFrame logFrame = new ItemLogFrame();
        logFrame.setLocationRelativeTo(this);
        logFrame.setVisible(true);
    }//GEN-LAST:event_viewLogBtnActionPerformed

    private void viewHistoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewHistoryBtnActionPerformed
        TransactionHistoryFrame historyFrame = new TransactionHistoryFrame();
        historyFrame.setLocationRelativeTo(this);
        historyFrame.setVisible(true);
    }//GEN-LAST:event_viewHistoryBtnActionPerformed

    private void newItemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newItemBtnActionPerformed
        NewItem newItem = new NewItem(this);
        newItem.setLocationRelativeTo(this);
        newItem.setVisible(true);
    }//GEN-LAST:event_newItemBtnActionPerformed

    public static void main(String args[]) {
        com.formdev.flatlaf.FlatDarkLaf.setup();
        java.awt.EventQueue.invokeLater(() -> new InventoryFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JTable inventoryTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton newItemBtn;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton viewHistoryBtn;
    private javax.swing.JButton viewLogBtn;
    // End of variables declaration//GEN-END:variables
}
