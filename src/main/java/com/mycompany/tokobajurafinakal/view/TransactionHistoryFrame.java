/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import com.mycompany.tokobajurafinakal.model.TransactionHistoryModel;
import com.mycompany.tokobajurafinakal.repository.TransactionRepository;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nala
 */
public class TransactionHistoryFrame extends JFrame {

    private final TransactionRepository transactionRepository = new TransactionRepository();
    private JTable historyTable;

    public TransactionHistoryFrame() {
        initUi();
        loadHistoryToTable();
    }

    private void initUi() {
        setTitle("Toko Baju RafiNaKal - Riwayat Transaksi");
        setSize(840, 500);
        setMinimumSize(new Dimension(760, 440));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UiUtil.closeOrGoHome(TransactionHistoryFrame.this);
            }
        });

        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setBorder(BorderFactory.createEmptyBorder(18, 24, 20, 24));

        JPanel headerPanel = new JPanel(new BorderLayout(16, 0));

        JButton backBtn = UiUtil.createBackButton("← Kembali");
        backBtn.addActionListener(e -> UiUtil.closeOrGoHome(this));

        JPanel titleWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 3));
        titleWrap.add(UiUtil.createTitleLabel("Riwayat Transaksi Penjualan"));

        JButton refreshBtn = UiUtil.createSecondaryButton("Muat Ulang");
        refreshBtn.setPreferredSize(new Dimension(110, 34));
        refreshBtn.addActionListener(e -> loadHistoryToTable());

        headerPanel.add(backBtn, BorderLayout.WEST);
        headerPanel.add(titleWrap, BorderLayout.CENTER);
        headerPanel.add(refreshBtn, BorderLayout.EAST);
        root.add(headerPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Transaksi", "Nama Produk", "Jumlah", "Subtotal", "Total", "Dibayar", "Kembalian"}
        ) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        historyTable = new JTable(tableModel);
        UiUtil.styleTable(historyTable);

        var colModel = historyTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(95);
        colModel.getColumn(0).setMaxWidth(110);
        colModel.getColumn(1).setPreferredWidth(210);
        colModel.getColumn(2).setPreferredWidth(65);
        colModel.getColumn(2).setMaxWidth(80);
        colModel.getColumn(3).setPreferredWidth(115);
        colModel.getColumn(4).setPreferredWidth(115);
        colModel.getColumn(5).setPreferredWidth(115);
        colModel.getColumn(6).setPreferredWidth(115);

        colModel.getColumn(0).setCellRenderer(UiUtil.centerRenderer());
        colModel.getColumn(2).setCellRenderer(UiUtil.centerRenderer());
        colModel.getColumn(3).setCellRenderer(UiUtil.rightRenderer());
        colModel.getColumn(4).setCellRenderer(UiUtil.rightRenderer());
        colModel.getColumn(5).setCellRenderer(UiUtil.rightRenderer());
        colModel.getColumn(6).setCellRenderer(UiUtil.rightRenderer());

        JScrollPane scrollPane = new JScrollPane(historyTable);
        root.add(scrollPane, BorderLayout.CENTER);

        setContentPane(root);
        setLocationRelativeTo(null);
    }

    private void loadHistoryToTable() {
        DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
        model.setRowCount(0);

        List<TransactionHistoryModel> list = transactionRepository.getTransactionHistory();
        int lastId = -1;

        for (TransactionHistoryModel row : list) {
            boolean isNewTransaction = (row.getTransactionId() != lastId);
            double change = row.getPaid() - row.getTotal();

            model.addRow(new Object[]{
                isNewTransaction ? "#" + row.getTransactionId() : "",
                row.getItemName(),
                row.getQuantity(),
                UiUtil.formatRupiah(row.getSubtotal()),
                isNewTransaction ? UiUtil.formatRupiah(row.getTotal()) : "",
                isNewTransaction ? UiUtil.formatRupiah(row.getPaid()) : "",
                isNewTransaction ? UiUtil.formatRupiah(change) : ""
            });

            lastId = row.getTransactionId();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TransactionHistoryFrame().setVisible(true));
    }
}
