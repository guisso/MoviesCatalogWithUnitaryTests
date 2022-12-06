/*
 * CC BY-NC-SA 4.0
 *
 * Copyright 2022 Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;.
 *
 * Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0)
 *
 * You are free to:
 *   Share - copy and redistribute the material in any medium or format
 *   Adapt - remix, transform, and build upon the material
 *
 * Under the following terms:
 *   Attribution - You must give appropriate credit, provide 
 *   a link to the license, and indicate if changes were made.
 *   You may do so in any reasonable manner, but not in any 
 *   way that suggests the licensor endorses you or your use.
 *   NonCommercial - You may not use the material for commercial purposes.
 *   ShareAlike - If you remix, transform, or build upon the 
 *   material, you must distribute your contributions under 
 *   the same license as the original.
 *   No additional restrictions - You may not apply legal 
 *   terms or technological measures that legally restrict 
 *   others from doing anything the license permits.
 *
 * Notices:
 *   You do not have to comply with the license for elements 
 *   of the material in the public domain or where your use 
 *   is permitted by an applicable exception or limitation.
 *   No warranties are given. The license may not give you 
 *   all of the permissions necessary for your intended use. 
 *   For example, other rights such as publicity, privacy, 
 *   or moral rights may limit how you use the material.
 */
package io.github.guisso.moviecatalog.util;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * Classe MyJTextField
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 * @version 0.1, 2022-12-04
 */
public class MyJTextField extends JTextField {

    private int maxLength = 15;

    private Color standard;
    private Color focused;

    //<editor-fold defaultstate="collapsed" desc="Constructor with DocumentFilter">
    public MyJTextField() {

        // Length limited by DocumentFilter
        ((PlainDocument) getDocument())
                .setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void replace(DocumentFilter.FilterBypass fb,
                            int offset, int length, String text,
                            AttributeSet attrs) throws BadLocationException {
                        if (getText().length() < maxLength) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }

                    @Override
                    public void insertString(DocumentFilter.FilterBypass fb,
                            int offset, String string, AttributeSet attr) throws BadLocationException {
                        if (getText().length() < maxLength) {
                            super.insertString(fb, offset, string, attr);
                        }
                    }
                });

        addFocusListener(new FocusHandler());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public Color getStandard() {
        return standard;
    }

    public void setStandard(Color standard) {
        this.standard = standard;
    }

    public Color getFocused() {
        return focused;
    }

    public void setFocused(Color focused) {
        this.focused = focused;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Focus handler">
    private class FocusHandler
            implements FocusListener {

        //<editor-fold defaultstate="collapsed" desc="Constructors">
        public FocusHandler() {
            // Default colors
            this(new Color(255, 255, 255),
                    new Color(255, 255, 220));
        }

        public FocusHandler(Color standard, Color focused) {
            setStandard(standard);
            setFocused(focused);

            setBackground(standard);
        }
        //</editor-fold>

        @Override
        public void focusGained(FocusEvent e) {
            swapColors();
            selectAll();
        }

        @Override
        public void focusLost(FocusEvent e) {
            swapColors();
        }

        private void swapColors() {
            setBackground(getBackground() == standard ? focused : standard);
        }
    }
    //</editor-fold>
}
