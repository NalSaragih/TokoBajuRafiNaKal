/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import com.mycompany.tokobajurafinakal.entity.Product;
import com.mycompany.tokobajurafinakal.model.CartItem;
import com.mycompany.tokobajurafinakal.model.CartModel;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nala
 */
public class ItemListFrame extends JFrame {

    private List<Product> allProducts = new ArrayList<>();
    private List<Product> displayedProducts = new ArrayList<>();
    private final ProductRepository productRepository = new ProductRepository();

    private JTable productTable;
    private JTextField searchField;
    private JButton cartBtn;

    public ItemListFrame() {
        initUi();
        loadProductsToTable();
        updateCartBadge();
    }

    private void initUi() {
        setTitle("Toko Baju RafiNaKal - Katalog Produk");
        setSize(820, 540);
        setMinimumSize(new Dimension(760, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new NavBar(this));

        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setBorder(BorderFactory.createEmptyBorder(18, 24, 20, 24));

        JPanel headerPanel = new JPanel(new BorderLayout(16, 0));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton backBtn = UiUtil.createBackButton("← Beranda");
        backBtn.addActionListener(e -> {
            HomeFrame home = new HomeFrame();
            home.setLocationRelativeTo(null);
            home.setVisible(true);
            this.dispose();
        });
        leftHeader.add(backBtn);

        JLabel titleLabel = UiUtil.createTitleLabel("Katalog Produk");

        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        searchField = new JTextField(16);
        searchField.setPreferredSize(new Dimension(190, 32));
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

        JButton refreshBtn = UiUtil.createSecondaryButton("Muat Ulang");
        refreshBtn.setPreferredSize(new Dimension(105, 32));
        refreshBtn.addActionListener(e -> loadProductsToTable());

        rightHeader.add(searchField);
        rightHeader.add(refreshBtn);

        JPanel centerTitleWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 3));
        centerTitleWrap.add(titleLabel);

        headerPanel.add(leftHeader, BorderLayout.WEST);
        headerPanel.add(centerTitleWrap, BorderLayout.CENTER);
        headerPanel.add(rightHeader, BorderLayout.EAST);
        root.add(headerPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nama Produk", "Stok", "Harga", "Detail", "Aksi"}
        ) {
            private final boolean[] canEdit = new boolean[]{false, false, false, true, true};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        productTable = new JTable(tableModel);
        UiUtil.styleTable(productTable);
        setupTableColumns();

        JScrollPane scrollPane = new JScrollPane(productTable);
        root.add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());

        JPanel leftFooter = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton historyBtn = UiUtil.createSecondaryButton("Riwayat Transaksi");
        historyBtn.setPreferredSize(new Dimension(155, 36));
        historyBtn.addActionListener(e -> {
            TransactionHistoryFrame historyFrame = new TransactionHistoryFrame();
            historyFrame.setLocationRelativeTo(this);
            historyFrame.setVisible(true);
        });
        leftFooter.add(historyBtn);

        JPanel rightFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        cartBtn = UiUtil.createPrimaryButton("Lihat Keranjang (0)");
        cartBtn.setPreferredSize(new Dimension(190, 36));
        cartBtn.addActionListener(e -> {
            CartFrame cartFrame = new CartFrame();
            cartFrame.setLocationRelativeTo(null);
            cartFrame.setVisible(true);
            this.dispose();
        });
        rightFooter.add(cartBtn);

        footerPanel.add(leftFooter, BorderLayout.WEST);
        footerPanel.add(rightFooter, BorderLayout.EAST);
        root.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(root);
        setLocationRelativeTo(null);
    }

    private void setupTableColumns() {
        var colModel = productTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(260);
        colModel.getColumn(1).setPreferredWidth(75);
        colModel.getColumn(1).setMaxWidth(95);
        colModel.getColumn(2).setPreferredWidth(135);
        colModel.getColumn(3).setPreferredWidth(95);
        colModel.getColumn(3).setMaxWidth(110);
        colModel.getColumn(4).setPreferredWidth(120);
        colModel.getColumn(4).setMaxWidth(135);

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

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new ItemListFrame().setVisible(true));
    }
}
