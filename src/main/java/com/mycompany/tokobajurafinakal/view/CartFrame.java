/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import com.mycompany.tokobajurafinakal.model.CartItem;
import com.mycompany.tokobajurafinakal.model.CartModel;
import com.mycompany.tokobajurafinakal.repository.TransactionRepository;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nala
 */
public class CartFrame extends JFrame {

    private JTable cartTable;
    private JTextField totalField;
    private JTextField paidField;
    private JButton payBtn;

    public CartFrame() {
        initUi();
        loadCartToTable();
    }

    private void initUi() {
        setTitle("Toko Baju RafiNaKal - Keranjang Belanja");
        setSize(840, 530);
        setMinimumSize(new Dimension(780, 460));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(new NavBar(this));

        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setBorder(BorderFactory.createEmptyBorder(18, 24, 20, 24));

        JPanel headerPanel = new JPanel(new BorderLayout(16, 0));

        JButton backBtn = UiUtil.createBackButton("← Katalog");
        backBtn.addActionListener(e -> backToCatalog());

        JPanel titleWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 3));
        titleWrap.add(UiUtil.createTitleLabel("Keranjang Belanja"));

        JButton clearCartBtn = UiUtil.createSecondaryButton("Kosongkan Keranjang");
        clearCartBtn.setPreferredSize(new Dimension(170, 34));
        clearCartBtn.addActionListener(e -> {
            if (CartModel.getInstance().isEmpty()) {
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Kosongkan seluruh isi keranjang?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                CartModel.getInstance().clear();
                loadCartToTable();
            }
        });

        headerPanel.add(backBtn, BorderLayout.WEST);
        headerPanel.add(titleWrap, BorderLayout.CENTER);
        headerPanel.add(clearCartBtn, BorderLayout.EAST);
        root.add(headerPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nama Produk", "Harga Satuan", "Jumlah", "Subtotal", "+", "-", "Hapus"}
        ) {
            private final boolean[] canEdit = new boolean[]{false, false, false, false, true, true, true};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        cartTable = new JTable(tableModel);
        UiUtil.styleTable(cartTable);
        setupTableColumns();

        JScrollPane scrollPane = new JScrollPane(cartTable);
        root.add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout(16, 0));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel totalLabel = new JLabel("Total Belanja:");
        totalLabel.setFont(UiUtil.FONT_LABEL);

        totalField = new JTextField(13);
        totalField.setEditable(false);
        totalField.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalField.setPreferredSize(new Dimension(160, 36));

        totalPanel.add(totalLabel);
        totalPanel.add(totalField);

        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JLabel paidLabel = new JLabel("Nominal Bayar (Rp):");
        paidLabel.setFont(UiUtil.FONT_LABEL);

        paidField = new JTextField(12);
        paidField.setPreferredSize(new Dimension(150, 36));
        paidField.putClientProperty("JTextField.placeholderText", "Contoh: 200000");

        payBtn = UiUtil.createSuccessButton("Bayar Sekarang");
        payBtn.setPreferredSize(new Dimension(160, 36));
        payBtn.addActionListener(e -> processCheckout());

        paymentPanel.add(paidLabel);
        paymentPanel.add(paidField);
        paymentPanel.add(payBtn);

        footerPanel.add(totalPanel, BorderLayout.WEST);
        footerPanel.add(paymentPanel, BorderLayout.EAST);
        root.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(root);
        setLocationRelativeTo(null);
    }

    private void setupTableColumns() {
        var colModel = cartTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(230);
        colModel.getColumn(1).setPreferredWidth(120);
        colModel.getColumn(2).setPreferredWidth(70);
        colModel.getColumn(2).setMaxWidth(85);
        colModel.getColumn(3).setPreferredWidth(130);
        colModel.getColumn(4).setPreferredWidth(48);
        colModel.getColumn(4).setMaxWidth(52);
        colModel.getColumn(5).setPreferredWidth(48);
        colModel.getColumn(5).setMaxWidth(52);
        colModel.getColumn(6).setPreferredWidth(80);
        colModel.getColumn(6).setMaxWidth(90);

        colModel.getColumn(1).setCellRenderer(UiUtil.rightRenderer());
        colModel.getColumn(2).setCellRenderer(UiUtil.centerRenderer());
        colModel.getColumn(3).setCellRenderer(UiUtil.rightRenderer());

        colModel.getColumn(4).setCellRenderer(new ButtonRenderer());
        colModel.getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), row -> {
            List<CartItem> items = CartModel.getInstance().getItems();
            if (row >= 0 && row < items.size()) {
                CartItem item = items.get(row);
                boolean updated = CartModel.getInstance().updateQuantity(
                        item.getProduct().getId(), item.getQuantity() + 1);
                if (!updated) {
                    JOptionPane.showMessageDialog(this,
                            "Stok tidak mencukupi! Maksimal tersedia: " + item.getProduct().getCurrentStock(),
                            "Stok Maksimal",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    SwingUtilities.invokeLater(this::loadCartToTable);
                }
            }
        }));

        colModel.getColumn(5).setCellRenderer(new ButtonRenderer());
        colModel.getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), row -> {
            List<CartItem> items = CartModel.getInstance().getItems();
            if (row >= 0 && row < items.size()) {
                CartItem item = items.get(row);
                CartModel.getInstance().updateQuantity(
                        item.getProduct().getId(), item.getQuantity() - 1);
                SwingUtilities.invokeLater(this::loadCartToTable);
            }
        }));

        colModel.getColumn(6).setCellRenderer(new ButtonRenderer());
        colModel.getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), row -> {
            List<CartItem> items = CartModel.getInstance().getItems();
            if (row >= 0 && row < items.size()) {
                CartItem item = items.get(row);
                CartModel.getInstance().removeItem(item.getProduct().getId());
                SwingUtilities.invokeLater(this::loadCartToTable);
            }
        }));
    }

    private void loadCartToTable() {
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
        model.setRowCount(0);

        List<CartItem> items = CartModel.getInstance().getItems();
        for (CartItem item : items) {
            model.addRow(new Object[]{
                item.getProduct().getProductName(),
                UiUtil.formatRupiah(item.getProduct().getPrice()),
                item.getQuantity(),
                UiUtil.formatRupiah(item.getSubtotal()),
                "+",
                "-",
                "Hapus"
            });
        }

        double total = CartModel.getInstance().getTotalAmount();
        totalField.setText(UiUtil.formatRupiah(total));
        payBtn.setEnabled(!items.isEmpty());
    }

    private void processCheckout() {
        CartModel cart = CartModel.getInstance();
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang masih kosong!");
            return;
        }

        double total = cart.getTotalAmount();
        String paidStr = paidField.getText().trim().replace(".", "").replace(",", "");

        if (paidStr.isEmpty()) {
            String dialogInput = JOptionPane.showInputDialog(
                    this,
                    "Total Belanja: " + UiUtil.formatRupiah(total) + "\nMasukkan nominal pembayaran (Rp):"
            );
            if (dialogInput == null) {
                return;
            }
            paidStr = dialogInput.trim().replace(".", "").replace(",", "");
        }

        try {
            double paid = Double.parseDouble(paidStr);
            if (paid < total) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nominal pembayaran kurang!\nMinimal yang harus dibayar: " + UiUtil.formatRupiah(total),
                        "Pembayaran Kurang",
                        JOptionPane.WARNING_MESSAGE
                );
                paidField.requestFocus();
                return;
            }

            TransactionRepository repo = new TransactionRepository();
            boolean success = repo.checkout(cart.getItems(), total, paid);

            if (success) {
                double change = paid - total;
                JOptionPane.showMessageDialog(
                        this,
                        "Pembayaran Berhasil!\n\n"
                        + "Total Belanja : " + UiUtil.formatRupiah(total) + "\n"
                        + "Dibayarkan    : " + UiUtil.formatRupiah(paid) + "\n"
                        + "Kembalian     : " + UiUtil.formatRupiah(change),
                        "Transaksi Selesai",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cart.clear();
                backToCatalog();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Masukkan angka nominal pembayaran yang valid!",
                    "Input Tidak Valid",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void backToCatalog() {
        ItemListFrame itemListFrame = new ItemListFrame();
        itemListFrame.setLocationRelativeTo(null);
        itemListFrame.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new CartFrame().setVisible(true));
    }
}
