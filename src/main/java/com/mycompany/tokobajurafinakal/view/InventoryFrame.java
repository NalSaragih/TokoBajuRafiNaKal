/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import com.mycompany.tokobajurafinakal.entity.Product;
import com.mycompany.tokobajurafinakal.repository.ProductRepository;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nala
 */
public class InventoryFrame extends JFrame {

    private List<Product> allProducts = new ArrayList<>();
    private List<Product> displayedProducts = new ArrayList<>();
    private final ProductRepository productRepository = new ProductRepository();

    private JTable inventoryTable;
    private JTextField searchField;

    public InventoryFrame() {
        initUi();
        loadProductsToTable();
    }

    private void initUi() {
        setTitle("Toko Baju RafiNaKal - Kelola Inventory");
        setSize(860, 540);
        setMinimumSize(new Dimension(780, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new NavBar(this));

        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setBorder(BorderFactory.createEmptyBorder(18, 24, 20, 24));

        JPanel headerPanel = new JPanel(new BorderLayout(16, 0));

        JButton backBtn = UiUtil.createBackButton("← Beranda");
        backBtn.addActionListener(e -> {
            HomeFrame home = new HomeFrame();
            home.setLocationRelativeTo(null);
            home.setVisible(true);
            this.dispose();
        });

        JPanel titleWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 3));
        titleWrap.add(UiUtil.createTitleLabel("Kelola Inventory Produk"));

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        searchField = new JTextField(16);
        searchField.setPreferredSize(new Dimension(190, 32));
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

        JButton refreshBtn = UiUtil.createSecondaryButton("Muat Ulang");
        refreshBtn.setPreferredSize(new Dimension(105, 32));
        refreshBtn.addActionListener(e -> loadProductsToTable());

        rightHeader.add(searchField);
        rightHeader.add(refreshBtn);

        headerPanel.add(backBtn, BorderLayout.WEST);
        headerPanel.add(titleWrap, BorderLayout.CENTER);
        headerPanel.add(rightHeader, BorderLayout.EAST);
        root.add(headerPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nama Produk", "Stok", "Harga", "Detail", "Edit", "Hapus"}
        ) {
            private final boolean[] canEdit = new boolean[]{false, false, false, false, true, true, true};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        inventoryTable = new JTable(tableModel);
        UiUtil.styleTable(inventoryTable);
        setupTableColumns();

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        root.add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());

        JPanel leftFooter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton viewLogBtn = UiUtil.createSecondaryButton("Log Stok Produk");
        viewLogBtn.setPreferredSize(new Dimension(150, 36));
        viewLogBtn.addActionListener(e -> {
            ItemLogFrame logFrame = new ItemLogFrame();
            logFrame.setLocationRelativeTo(this);
            logFrame.setVisible(true);
        });

        JButton viewHistoryBtn = UiUtil.createSecondaryButton("Riwayat Transaksi");
        viewHistoryBtn.setPreferredSize(new Dimension(155, 36));
        viewHistoryBtn.addActionListener(e -> {
            TransactionHistoryFrame historyFrame = new TransactionHistoryFrame();
            historyFrame.setLocationRelativeTo(this);
            historyFrame.setVisible(true);
        });

        leftFooter.add(viewLogBtn);
        leftFooter.add(viewHistoryBtn);

        JPanel rightFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton newItemBtn = UiUtil.createPrimaryButton("+ Tambah Produk Baru");
        newItemBtn.setPreferredSize(new Dimension(190, 36));
        newItemBtn.addActionListener(e -> {
            NewItem newItem = new NewItem(this);
            newItem.setLocationRelativeTo(this);
            newItem.setVisible(true);
        });
        rightFooter.add(newItemBtn);

        footerPanel.add(leftFooter, BorderLayout.WEST);
        footerPanel.add(rightFooter, BorderLayout.EAST);
        root.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(root);
        setLocationRelativeTo(null);
    }

    private void setupTableColumns() {
        var colModel = inventoryTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(50);
        colModel.getColumn(0).setMaxWidth(65);
        colModel.getColumn(1).setPreferredWidth(240);
        colModel.getColumn(2).setPreferredWidth(70);
        colModel.getColumn(2).setMaxWidth(85);
        colModel.getColumn(3).setPreferredWidth(130);
        colModel.getColumn(4).setPreferredWidth(85);
        colModel.getColumn(4).setMaxWidth(95);
        colModel.getColumn(5).setPreferredWidth(80);
        colModel.getColumn(5).setMaxWidth(90);
        colModel.getColumn(6).setPreferredWidth(85);
        colModel.getColumn(6).setMaxWidth(95);

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

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new InventoryFrame().setVisible(true));
    }
}
