/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import com.mycompany.tokobajurafinakal.entity.Product;
import com.mycompany.tokobajurafinakal.model.CartModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author Nala
 */
public class ItemDetailFrame extends JFrame {

    private static final int IMG_WIDTH = 240;
    private static final int IMG_HEIGHT = 290;

    private Product currentProduct;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel stockLabel;
    private JLabel imgLabel;
    private JTextArea descTextArea;
    private JButton addCartBtn;

    public ItemDetailFrame() {
        this(null);
    }

    public ItemDetailFrame(Product product) {
        this.currentProduct = product;
        initUi();
        if (product != null) {
            loadProductDetails(product);
        }
    }

    private void initUi() {
        setTitle("Toko Baju RafiNaKal - Detail Produk");
        setSize(680, 470);
        setMinimumSize(new Dimension(640, 440));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UiUtil.closeOrGoHome(ItemDetailFrame.this);
            }
        });

        JPanel root = new JPanel(new BorderLayout(18, 16));
        root.setBorder(BorderFactory.createEmptyBorder(20, 26, 20, 26));

        JPanel headerPanel = new JPanel(new BorderLayout(12, 0));
        JButton backBtn = UiUtil.createBackButton("← Kembali");
        backBtn.addActionListener(e -> UiUtil.closeOrGoHome(this));

        JLabel titleLabel = UiUtil.createTitleLabel("Detail Produk");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(backBtn.getPreferredSize());

        headerPanel.add(backBtn, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(spacer, BorderLayout.EAST);
        root.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(24, 0));

        imgLabel = new JLabel("Tidak ada gambar", SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(IMG_WIDTH, IMG_HEIGHT));
        imgLabel.setForeground(UiUtil.MUTED_TEXT);
        imgLabel.setBorder(UiUtil.createPreviewBorder());

        JPanel infoPanel = new JPanel(new BorderLayout(0, 12));

        JPanel metaPanel = new JPanel();
        metaPanel.setLayout(new BoxLayout(metaPanel, BoxLayout.Y_AXIS));

        idLabel = new JLabel("ID Produk: -");
        idLabel.setFont(UiUtil.FONT_SUBTITLE);
        idLabel.setForeground(UiUtil.MUTED_TEXT);

        nameLabel = new JLabel("Nama Produk");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        priceLabel = new JLabel("Rp 0");
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        priceLabel.setForeground(UiUtil.ACCENT_GREEN);

        stockLabel = new JLabel("Stok Tersedia: 0");
        stockLabel.setFont(UiUtil.FONT_BODY);

        metaPanel.add(idLabel);
        metaPanel.add(Box.createVerticalStrut(4));
        metaPanel.add(nameLabel);
        metaPanel.add(Box.createVerticalStrut(6));
        metaPanel.add(priceLabel);
        metaPanel.add(Box.createVerticalStrut(6));
        metaPanel.add(stockLabel);

        JPanel descPanel = new JPanel(new BorderLayout(0, 6));
        JLabel descHeader = new JLabel("Deskripsi Produk");
        descHeader.setFont(UiUtil.FONT_LABEL);

        descTextArea = new JTextArea();
        descTextArea.setEditable(false);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setFont(UiUtil.FONT_BODY);
        JScrollPane scrollPane = new JScrollPane(descTextArea);

        descPanel.add(descHeader, BorderLayout.NORTH);
        descPanel.add(scrollPane, BorderLayout.CENTER);

        infoPanel.add(metaPanel, BorderLayout.NORTH);
        infoPanel.add(descPanel, BorderLayout.CENTER);

        contentPanel.add(imgLabel, BorderLayout.WEST);
        contentPanel.add(infoPanel, BorderLayout.CENTER);
        root.add(contentPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton closeBtn = UiUtil.createSecondaryButton("Tutup");
        closeBtn.setPreferredSize(new Dimension(100, 36));
        closeBtn.addActionListener(e -> UiUtil.closeOrGoHome(this));

        addCartBtn = UiUtil.createPrimaryButton("+ Masuk Keranjang");
        addCartBtn.setPreferredSize(new Dimension(165, 36));
        addCartBtn.addActionListener(e -> addCurrentProductToCart());

        footerPanel.add(closeBtn);
        footerPanel.add(addCartBtn);
        root.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(root);
        setLocationRelativeTo(null);
    }

    private void loadProductDetails(Product product) {
        idLabel.setText("ID Produk: #" + product.getId());
        nameLabel.setText(product.getProductName());
        priceLabel.setText(UiUtil.formatRupiah(product.getPrice()));

        if (product.getCurrentStock() > 0) {
            stockLabel.setText("Stok Tersedia: " + product.getCurrentStock() + " pcs");
        } else {
            stockLabel.setText("Stok: Habis (0)");
            stockLabel.setForeground(UiUtil.ACCENT_RED);
            addCartBtn.setEnabled(false);
        }

        String desc = product.getProductDesc();
        descTextArea.setText((desc != null && !desc.isBlank()) ? desc : "Tidak ada deskripsi produk.");

        if (product.getProductImage() != null && product.getProductImage().length > 0) {
            ImageIcon icon = new ImageIcon(product.getProductImage());
            Image scaled = icon.getImage().getScaledInstance(IMG_WIDTH - 14, IMG_HEIGHT - 14, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
            imgLabel.setText("");
        } else {
            imgLabel.setIcon(null);
            imgLabel.setText("Tidak ada gambar");
        }
    }

    private void addCurrentProductToCart() {
        if (currentProduct == null) {
            return;
        }
        boolean added = CartModel.getInstance().addItem(currentProduct, 1);
        if (added) {
            JOptionPane.showMessageDialog(this,
                    "\"" + currentProduct.getProductName() + "\" ditambahkan ke keranjang!");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Stok tidak mencukupi untuk menambah lagi!",
                    "Stok Habis",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new ItemDetailFrame().setVisible(true));
    }
}
