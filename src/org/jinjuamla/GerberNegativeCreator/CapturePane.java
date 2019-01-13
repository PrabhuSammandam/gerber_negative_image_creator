/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.GerberNegativeCreator;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.jinjuamla.camfilelibrary.Consumer;

/**
 *
 * @author psammand
 */
public class CapturePane extends JPanel implements Consumer {

    private static final long serialVersionUID = 1L;

        private final JTextArea output;

        public CapturePane() {
            setLayout(new BorderLayout());
            output = new JTextArea();
            output.setFont(new Font("monospaced", Font.PLAIN, 12));
            add(new JScrollPane(output));
        }
        
        public void clear()
        {
            output.setText( "");
        }

        @Override
        public void appendText(final String text) {
            if (EventQueue.isDispatchThread()) {
                output.append(text);
                output.setCaretPosition(output.getText().length());
            } else {

                EventQueue.invokeLater(() -> {
                    appendText(text);
                });

            }
        }        
    }
