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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Nala
 */
public class EditItemFrame extends JFrame {

    private static final int PREVIEW_SIZE = 180;

    private Product currentProduct;
    private InventoryFrame parentFrame;
    private byte[] selectedImageBytes = null;
    private final ProductRepository productRepository = new ProductRepository();

    private JTextField productNameField;
    private JTextField productPriceField;
    private JTextField productStockField;
    private JTextArea descTextArea;
    private JLabel lblImagePreview;
    private JTextField txtImagePath;
    private JLabel titleLabel;

    public EditItemFrame() {
        this(null, null);
    }

    public EditItemFrame(Product product, InventoryFrame parentFrame) {
        this.currentProduct = product;
        this.parentFrame = parentFrame;
        initUi();
        if (product != null) {
            loadProduct(product);
        }
    }

    private void initUi() {
        setTitle("Toko Baju RafiNaKal - Edit Produk");
        setSize(700, 490);
        setMinimumSize(new Dimension(660, 460));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleBack();
            }
        });

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(BorderFactory.createEmptyBorder(20, 26, 20, 26));

        JPanel headerPanel = new JPanel(new BorderLayout(12, 0));
        JButton backBtn = UiUtil.createBackButton("← Kembali");
        backBtn.addActionListener(e -> handleBack());

        titleLabel = UiUtil.createTitleLabel("Edit Produk");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(backBtn.getPreferredSize());

        headerPanel.add(backBtn, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(spacer, BorderLayout.EAST);
        root.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(28, 0));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        productNameField = new JTextField();
        productPriceField = new JTextField();
        productStockField = new JTextField();

        descTextArea = new JTextArea(5, 20);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setFont(UiUtil.FONT_BODY);
        JScrollPane descScroll = new JScrollPane(descTextArea);

        addFormRow(formPanel, gbc, 0, "Nama Produk", productNameField);
        addFormRow(formPanel, gbc, 1, "Harga (Rp)", productPriceField);
        addFormRow(formPanel, gbc, 2, "Stok Saat Ini", productStockField);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 0, 6, 14);
        JLabel descLabel = new JLabel("Deskripsi");
        descLabel.setFont(UiUtil.FONT_LABEL);
        formPanel.add(descLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(6, 0, 6, 0);
        formPanel.add(descScroll, gbc);

        JPanel imagePanel = new JPanel(new BorderLayout(0, 8));
        imagePanel.setPreferredSize(new Dimension(210, 280));

        JLabel imgTitle = new JLabel("Foto Produk", SwingConstants.CENTER);
        imgTitle.setFont(UiUtil.FONT_LABEL);

        lblImagePreview = new JLabel("Tidak ada gambar", SwingConstants.CENTER);
        lblImagePreview.setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
        lblImagePreview.setForeground(UiUtil.MUTED_TEXT);
        lblImagePreview.setBorder(UiUtil.createPreviewBorder());

        txtImagePath = new JTextField("Tidak ada gambar");
        txtImagePath.setEditable(false);
        txtImagePath.setHorizontalAlignment(SwingConstants.CENTER);
        txtImagePath.setFont(UiUtil.FONT_SUBTITLE);

        JPanel imgBtnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        JButton chooseImageBtn = UiUtil.createSecondaryButton("Ganti Gambar...");
        chooseImageBtn.addActionListener(e -> chooseImage());

        JButton removeImageBtn = UiUtil.createSecondaryButton("Hapus");
        removeImageBtn.addActionListener(e -> clearImage());

        imgBtnRow.add(chooseImageBtn);
        imgBtnRow.add(removeImageBtn);

        JPanel imgBottom = new JPanel(new BorderLayout(0, 6));
        imgBottom.add(txtImagePath, BorderLayout.NORTH);
        imgBottom.add(imgBtnRow, BorderLayout.SOUTH);

        imagePanel.add(imgTitle, BorderLayout.NORTH);
        imagePanel.add(lblImagePreview, BorderLayout.CENTER);
        imagePanel.add(imgBottom, BorderLayout.SOUTH);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(imagePanel, BorderLayout.EAST);
        root.add(contentPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton cancelBtn = UiUtil.createSecondaryButton("Batal");
        cancelBtn.setPreferredSize(new Dimension(100, 36));
        cancelBtn.addActionListener(e -> handleBack());

        JButton saveButton = UiUtil.createPrimaryButton("Simpan Perubahan");
        saveButton.setPreferredSize(new Dimension(165, 36));
        saveButton.addActionListener(e -> saveChanges());

        footerPanel.add(cancelBtn);
        footerPanel.add(saveButton);
        root.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(root);
        setLocationRelativeTo(parentFrame);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 0, 6, 14);
        JLabel label = new JLabel(labelText);
        label.setFont(UiUtil.FONT_LABEL);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        field.setPreferredSize(new Dimension(220, 32));
        panel.add(field, gbc);
    }

    private void loadProduct(Product product) {
        titleLabel.setText("Edit Produk #" + product.getId());
        productNameField.setText(product.getProductName() != null ? product.getProductName() : "");
        productStockField.setText(String.valueOf(product.getCurrentStock()));
        productPriceField.setText(UiUtil.formatPlainNumber(product.getPrice()));
        descTextArea.setText(product.getProductDesc() != null ? product.getProductDesc() : "");

        this.selectedImageBytes = product.getProductImage();
        if (product.getProductImage() != null && product.getProductImage().length > 0) {
            ImageIcon icon = new ImageIcon(product.getProductImage());
            Image scaled = icon.getImage().getScaledInstance(PREVIEW_SIZE - 14, PREVIEW_SIZE - 14, Image.SCALE_SMOOTH);
            lblImagePreview.setIcon(new ImageIcon(scaled));
            lblImagePreview.setText("");
            txtImagePath.setText("Gambar tersimpan");
        } else {
            clearImage();
        }
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Gambar (JPG, PNG)", "jpg", "jpeg", "png");
        chooser.setFileFilter(filter);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                selectedImageBytes = Files.readAllBytes(file.toPath());
                txtImagePath.setText(file.getName());

                ImageIcon icon = new ImageIcon(selectedImageBytes);
                Image scaled = icon.getImage().getScaledInstance(PREVIEW_SIZE - 14, PREVIEW_SIZE - 14, Image.SCALE_SMOOTH);
                lblImagePreview.setIcon(new ImageIcon(scaled));
                lblImagePreview.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal membaca gambar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearImage() {
        selectedImageBytes = null;
        lblImagePreview.setIcon(null);
        lblImagePreview.setText("Tidak ada gambar");
        txtImagePath.setText("Tidak ada gambar");
    }

    private void saveChanges() {
        if (currentProduct == null) {
            return;
        }
        String name = productNameField.getText().trim();
        String priceStr = productPriceField.getText().trim();
        String stockStr = productStockField.getText().trim();
        String productDesc = descTextArea.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama produk tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            productNameField.requestFocus();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);

            if (price < 0 || stock < 0) {
                JOptionPane.showMessageDialog(this, "Harga dan stok tidak boleh bernilai negatif!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            currentProduct.setProductName(name);
            currentProduct.setPrice(price);
            currentProduct.setCurrentStock(stock);
            currentProduct.setProductDesc(productDesc);
            currentProduct.setProductImage(selectedImageBytes);

            productRepository.update(currentProduct);

            JOptionPane.showMessageDialog(this, "Produk berhasil diperbarui!");
            if (parentFrame != null) {
                parentFrame.loadProductsToTable();
            }
            handleBack();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus berupa angka yang valid!", "Validasi", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleBack() {
        UiUtil.closeOrGoHome(this);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new EditItemFrame().setVisible(true));
    }
}
