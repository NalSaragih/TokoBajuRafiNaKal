/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Rafi
 */
public final class UiUtil {

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 20);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 13);

    public static final Color ACCENT_BLUE = new Color(38, 117, 191);
    public static final Color ACCENT_GREEN = new Color(39, 138, 82);
    public static final Color ACCENT_RED = new Color(210, 60, 60);
    public static final Color BORDER_COLOR = new Color(88, 92, 98);
    public static final Color MUTED_TEXT = new Color(165, 170, 178);

    private static final NumberFormat RUPIAH_FORMAT;

    static {
        RUPIAH_FORMAT = NumberFormat.getNumberInstance(Locale.of("id", "ID"));
        RUPIAH_FORMAT.setMaximumFractionDigits(0);
    }

    private UiUtil() {
    }

    public static String formatRupiah(double amount) {
        return "Rp " + RUPIAH_FORMAT.format(Math.round(amount));
    }

    public static String formatPlainNumber(double amount) {
        if (amount == Math.rint(amount)) {
            return String.format("%.0f", amount);
        }
        return String.valueOf(amount);
    }

    public static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(ACCENT_BLUE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.putClientProperty("FlatLaf.style", "arc: 8");
        return btn;
    }

    public static JButton createSuccessButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(ACCENT_GREEN);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.putClientProperty("FlatLaf.style", "arc: 8");
        return btn;
    }

    public static JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BODY);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.putClientProperty("FlatLaf.style", "arc: 8");
        return btn;
    }

    public static JButton createBackButton(String text) {
        JButton btn = createSecondaryButton(text);
        btn.setPreferredSize(new Dimension(115, 34));
        return btn;
    }

    public static Border createPreviewBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
        );
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_TITLE);
        return label;
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(36);
        table.setFont(FONT_BODY);
        table.getTableHeader().setFont(FONT_LABEL);
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowHorizontalLines(true);
        table.setFillsViewportHeight(true);
    }

    public static DefaultTableCellRenderer centerRenderer() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        return renderer;
    }

    public static DefaultTableCellRenderer rightRenderer() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.RIGHT);
        return renderer;
    }

    public static void closeOrGoHome(JFrame currentFrame) {
        boolean hasOtherVisibleFrame = false;
        for (Frame f : Frame.getFrames()) {
            if (f != currentFrame && f.isDisplayable() && f.isVisible()) {
                hasOtherVisibleFrame = true;
                f.toFront();
                break;
            }
        }
        if (!hasOtherVisibleFrame) {
            HomeFrame home = new HomeFrame();
            home.setLocationRelativeTo(null);
            home.setVisible(true);
        }
        currentFrame.dispose();
    }
}
