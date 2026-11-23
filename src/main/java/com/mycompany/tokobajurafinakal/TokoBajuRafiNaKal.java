/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.tokobajurafinakal;

import com.formdev.flatlaf.FlatDarkLaf; 
import com.mycompany.tokobajurafinakal.view.InventoryFrame;
import com.mycompany.tokobajurafinakal.view.ItemListFrame;
import com.mycompany.tokobajurafinakal.view.NewItem;
import javax.swing.UIManager;

/**
 *
 * @author Nala
 */
public class TokoBajuRafiNaKal {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf theme.");
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InventoryFrame().setVisible(true); 
            }
        });
    }
}
