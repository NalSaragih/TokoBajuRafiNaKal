/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import com.mycompany.tokobajurafinakal.model.ProductLogModel;
import com.mycompany.tokobajurafinakal.repository.LogRepository;
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
public class ItemLogFrame extends JFrame {

    private final LogRepository logRepository = new LogRepository();
    private JTable logTable;

    public ItemLogFrame() {
        initUi();
        loadLogsToTable();
    }

    private void initUi() {
        setTitle("Toko Baju RafiNaKal - Log Stok Produk");
        setSize(780, 500);
        setMinimumSize(new Dimension(700, 440));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UiUtil.closeOrGoHome(ItemLogFrame.this);
            }
        });

        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setBorder(BorderFactory.createEmptyBorder(18, 24, 20, 24));

        JPanel headerPanel = new JPanel(new BorderLayout(16, 0));

        JButton backBtn = UiUtil.createBackButton("← Kembali");
        backBtn.addActionListener(e -> UiUtil.closeOrGoHome(this));

        JPanel titleWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 3));
        titleWrap.add(UiUtil.createTitleLabel("Log Aktivitas Stok Produk"));

        JButton refreshBtn = UiUtil.createSecondaryButton("Muat Ulang");
        refreshBtn.setPreferredSize(new Dimension(110, 34));
        refreshBtn.addActionListener(e -> loadLogsToTable());

        headerPanel.add(backBtn, BorderLayout.WEST);
        headerPanel.add(titleWrap, BorderLayout.CENTER);
        headerPanel.add(refreshBtn, BorderLayout.EAST);
        root.add(headerPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Produk", "Nama Produk", "Tanggal", "Perubahan", "Tipe Aktivitas"}
        ) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        logTable = new JTable(tableModel);
        UiUtil.styleTable(logTable);

        var colModel = logTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(85);
        colModel.getColumn(0).setMaxWidth(100);
        colModel.getColumn(1).setPreferredWidth(240);
        colModel.getColumn(2).setPreferredWidth(120);
        colModel.getColumn(3).setPreferredWidth(100);
        colModel.getColumn(3).setMaxWidth(115);
        colModel.getColumn(4).setPreferredWidth(170);

        colModel.getColumn(0).setCellRenderer(UiUtil.centerRenderer());
        colModel.getColumn(2).setCellRenderer(UiUtil.centerRenderer());
        colModel.getColumn(3).setCellRenderer(UiUtil.centerRenderer());

        JScrollPane scrollPane = new JScrollPane(logTable);
        root.add(scrollPane, BorderLayout.CENTER);

        setContentPane(root);
        setLocationRelativeTo(null);
    }

    private void loadLogsToTable() {
        DefaultTableModel model = (DefaultTableModel) logTable.getModel();
        model.setRowCount(0);

        List<ProductLogModel> logs = logRepository.getAllProductLogs();
        for (ProductLogModel log : logs) {
            String idStr = log.getId() > 0 ? "#" + log.getId() : "-";
            String dateStr = (log.getLogDate() != null) ? log.getLogDate().toString() : "-";
            String qtyStr = (log.getQuantityChange() > 0)
                    ? "+" + log.getQuantityChange()
                    : String.valueOf(log.getQuantityChange());

            model.addRow(new Object[]{
                idStr,
                log.getProductName(),
                dateStr,
                qtyStr,
                formatLogType(log.getLogType())
            });
        }
    }

    private String formatLogType(String type) {
        if (type == null) {
            return "-";
        }
        return switch (type) {
            case "SALE" -> "SALE (Penjualan)";
            case "ADMIN" -> "ADMIN (Update Stok)";
            case "INITIAL" -> "INITIAL (Stok Awal)";
            case "DELETED" -> "DELETED (Produk Dihapus)";
            default -> type;
        };
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new ItemLogFrame().setVisible(true));
    }
}
