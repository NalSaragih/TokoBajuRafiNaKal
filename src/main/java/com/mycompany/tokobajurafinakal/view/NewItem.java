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
public class NewItem extends JFrame {

    private static final int PREVIEW_SIZE = 180;

    private byte[] selectedImageBytes = null;
    private final ProductRepository productRepository = new ProductRepository();
    private InventoryFrame parentFrame;

    private JTextField productNameField;
    private JTextField productPriceField;
    private JTextField productStockField;
    private JTextArea descTextArea;
    private JLabel lblImagePreview;
    private JTextField txtImagePath;

    public NewItem() {
        this(null);
    }

    public NewItem(InventoryFrame parentFrame) {
        this.parentFrame = parentFrame;
        initUi();
    }

    private void initUi() {
        setTitle("Toko Baju RafiNaKal - Tambah Produk Baru");
        setSize(700, 490);
        setMinimumSize(new Dimension(660, 460));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleBack();
            }
        });
        if (parentFrame == null) {
            setJMenuBar(new NavBar(this));
        }

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(BorderFactory.createEmptyBorder(20, 26, 20, 26));

        JPanel headerPanel = new JPanel(new BorderLayout(12, 0));
        JButton backBtn = UiUtil.createBackButton("← Kembali");
        backBtn.addActionListener(e -> handleBack());

        JLabel titleLabel = UiUtil.createTitleLabel("Tambah Produk Baru");
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
        gbc.insets = new Insets(6, 0, 6, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        productNameField = new JTextField();
        productNameField.putClientProperty("JTextField.placeholderText", "Contoh: Kemeja Flannel Pria");

        productPriceField = new JTextField();
        productPriceField.putClientProperty("JTextField.placeholderText", "Contoh: 150000");

        productStockField = new JTextField();
        productStockField.putClientProperty("JTextField.placeholderText", "Contoh: 25");

        descTextArea = new JTextArea(5, 20);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setFont(UiUtil.FONT_BODY);
        JScrollPane descScroll = new JScrollPane(descTextArea);

        addFormRow(formPanel, gbc, 0, "Nama Produk", productNameField);
        addFormRow(formPanel, gbc, 1, "Harga (Rp)", productPriceField);
        addFormRow(formPanel, gbc, 2, "Stok Awal", productStockField);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 0, 6, 12);
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

        lblImagePreview = new JLabel("Belum ada gambar", SwingConstants.CENTER);
        lblImagePreview.setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
        lblImagePreview.setForeground(UiUtil.MUTED_TEXT);
        lblImagePreview.setBorder(UiUtil.createPreviewBorder());

        txtImagePath = new JTextField("Belum ada file dipilih");
        txtImagePath.setEditable(false);
        txtImagePath.setHorizontalAlignment(SwingConstants.CENTER);
        txtImagePath.setFont(UiUtil.FONT_SUBTITLE);

        JPanel imgBtnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        JButton chooseImageBtn = UiUtil.createSecondaryButton("Pilih Gambar...");
        chooseImageBtn.addActionListener(e -> chooseImage());

        JButton clearImageBtn = UiUtil.createSecondaryButton("Reset");
        clearImageBtn.addActionListener(e -> clearImage());

        imgBtnRow.add(chooseImageBtn);
        imgBtnRow.add(clearImageBtn);

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

        JButton addButton = UiUtil.createPrimaryButton("Simpan Produk");
        addButton.setPreferredSize(new Dimension(150, 36));
        addButton.addActionListener(e -> saveNewProduct());

        footerPanel.add(cancelBtn);
        footerPanel.add(addButton);
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
        lblImagePreview.setText("Belum ada gambar");
        txtImagePath.setText("Belum ada file dipilih");
    }

    private void saveNewProduct() {
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

            Product product = new Product(name, price, selectedImageBytes, stock, productDesc);
            productRepository.insert(product);

            JOptionPane.showMessageDialog(this, "Produk \"" + name + "\" berhasil ditambahkan!");

            if (parentFrame != null) {
                parentFrame.loadProductsToTable();
                this.dispose();
            } else {
                InventoryFrame inventory = new InventoryFrame();
                inventory.setLocationRelativeTo(null);
                inventory.setVisible(true);
                this.dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus berupa angka yang valid!", "Validasi", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleBack() {
        UiUtil.closeOrGoHome(this);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new NewItem().setVisible(true));
    }
}
