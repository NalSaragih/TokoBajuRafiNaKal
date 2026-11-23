/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokobajurafinakal.view;
import java.awt.Component;
import java.util.function.Consumer;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
/**
 *
 * @author Nala
 */
public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private int currentRow;
    private Consumer<Integer> onClickAction;

    public ButtonEditor(JCheckBox checkBox, Consumer<Integer> onClickAction) {
        super(checkBox);
        this.onClickAction = onClickAction;
        
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> {
            fireEditingStopped(); // Stop editing before running the action
            onClickAction.accept(currentRow); // Trigger your custom code with the row index
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.currentRow = row;
        this.label = (value == null) ? "" : value.toString();
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }
}
