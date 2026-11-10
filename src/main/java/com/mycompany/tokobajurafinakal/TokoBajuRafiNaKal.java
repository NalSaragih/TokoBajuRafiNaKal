/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package com.mycompany.tokobajurafinakal;

import com.formdev.flatlaf.FlatDarkLaf; 
import com.mycompany.tokobajurafinakal.view.CartFrame;
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
                new CartFrame().setVisible(true); // Replace CartFrame with your main frame name
            }
        });
    }
}
