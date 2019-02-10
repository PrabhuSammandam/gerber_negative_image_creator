/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.GerberNegativeCreator;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import org.jinjuamla.camfilelibrary.FileHelper;
import org.jinjuamla.camfilelibrary.StreamCapturer;
import org.jinjuamla.camfilelibrary.enums.PcbFileTypeEnum;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 *
 * @author psammand
 */
public class GerberNegativeCreatorMainFrame extends javax.swing.JFrame {

    public final String __version = "v1.0.0";
    FormListener formListener;
    JFileChooser fileChooser;

    /**
     * Creates new form GerberNegativeCreatorMainFrame
     */
    public GerberNegativeCreatorMainFrame() {
        initComponents();
        
        setTitle( "Gerber Negative Image Creator : " + __version);

        System.setOut( new PrintStream( new StreamCapturer( "OUT", __capturePane, System.out ) ) );
        System.setErr( new PrintStream( new StreamCapturer( "ERR", __capturePane, System.err ) ) );

        __fileList.setDropTarget( new DropTarget() {
            private static final long serialVersionUID = 1L;

            @Override
            public synchronized void drop( DropTargetDropEvent evt ) {
                try {
                    evt.acceptDrop( DnDConstants.ACTION_COPY );
                    @SuppressWarnings( "unchecked" )
                    List<File> droppedFiles = ( List<File> ) evt.getTransferable().getTransferData( DataFlavor.javaFileListFlavor );

                    if ( droppedFiles.size() > 0 ) {
                        handleDroppedFiles( droppedFiles );
                    }
                } catch ( UnsupportedFlavorException | IOException ex ) {
                }
            }

        } );
        __fileListDeleteButton = new javax.swing.JButton();
        
                __fileListDeleteButton.setFont(__fileListDeleteButton.getFont().deriveFont(__fileListDeleteButton.getFont().getStyle() & ~java.awt.Font.BOLD));
                __fileListDeleteButton.setText("Delete");
                __fileListDeleteButton.addActionListener(formListener);
                
                __fileAddButton = new JButton("Add Files");
                __fileAddButton.getFont().deriveFont(__fileAddButton.getFont().getStyle() & ~java.awt.Font.BOLD, 12);
                __fileAddButton.addActionListener(formListener);
                GridBagConstraints gbc___fileAddButton = new GridBagConstraints();
                gbc___fileAddButton.insets = new Insets(0, 0, 5, 0);
                gbc___fileAddButton.gridx = 1;
                gbc___fileAddButton.gridy = 0;
                jPanel1.add(__fileAddButton, gbc___fileAddButton);
                
                GridBagConstraints gbc___fileListDeleteButton = new GridBagConstraints();
                gbc___fileListDeleteButton.anchor = GridBagConstraints.NORTH;
                gbc___fileListDeleteButton.fill = GridBagConstraints.HORIZONTAL;
                gbc___fileListDeleteButton.insets = new Insets(0, 0, 5, 0);
                gbc___fileListDeleteButton.gridx = 1;
                gbc___fileListDeleteButton.gridy = 1;
                
                                jPanel1.add(__fileListDeleteButton, gbc___fileListDeleteButton);
                                __fileListClearButton = new javax.swing.JButton();
                                
                                        __fileListClearButton.setFont(__fileListClearButton.getFont().deriveFont(__fileListClearButton.getFont().getStyle() & ~java.awt.Font.BOLD, 12));
                                        __fileListClearButton.setText("Clear");
                                        __fileListClearButton.addActionListener(formListener);
                                        GridBagConstraints gbc___fileListClearButton = new GridBagConstraints();
                                        gbc___fileListClearButton.insets = new Insets(0, 0, 5, 0);
                                        gbc___fileListClearButton.anchor = GridBagConstraints.NORTH;
                                        gbc___fileListClearButton.fill = GridBagConstraints.HORIZONTAL;
                                        gbc___fileListClearButton.gridx = 1;
                                        gbc___fileListClearButton.gridy = 2;
                                        
                                                        jPanel1.add(__fileListClearButton, gbc___fileListClearButton);

    }

    private void handleDroppedFiles( List<File> droppedFiles ) {
        DefaultListModel<String> model = ( DefaultListModel<String> ) __fileList.getModel();

        LinkedList<String> fileList = new LinkedList<>();

        for ( File file : droppedFiles ) {
            if ( file.isDirectory() ) {
                File[] dir_files = file.listFiles( (File file1) -> file1.isFile() );

                for ( File dir_file : dir_files ) {

                    PcbFileTypeEnum fileType = FileHelper.FindFileType( dir_file.getAbsolutePath() );
                    if ( fileType == PcbFileTypeEnum.Gerber || fileType == PcbFileTypeEnum.Excellon ) {
                        //model.addElement( dir_file.getAbsolutePath() );
                        fileList.add( dir_file.getAbsolutePath() );
                    }
                }
            } else {
                PcbFileTypeEnum fileType = FileHelper.FindFileType( file.getAbsolutePath() );
                if ( fileType == PcbFileTypeEnum.Gerber || fileType == PcbFileTypeEnum.Excellon ) {
                    //model.addElement( file.getAbsolutePath() );
                    fileList.add( file.getAbsolutePath() );
                }
            }
        }

        boolean duplicateFound;

        for ( int i = 0; i < fileList.size(); i++ ) {
            duplicateFound = false;
            String currentFile = fileList.get( i );
            for ( int j = 0; j < model.size(); j++ ) {
                if ( currentFile.equals( model.get( j ) ) ) {
                    duplicateFound = true;
                }
            }

            if ( !duplicateFound ) {
                model.addElement( currentFile );
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel5 = new javax.swing.JPanel();
        __capturePane = new org.jinjuamla.GerberNegativeCreator.CapturePane();
        __renderButton = new javax.swing.JButton();

        formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerber Negative Creator");
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "[Drop the GERBER files in below box]", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 255)));
        GridBagLayout gbl_jPanel1 = new GridBagLayout();

        gbl_jPanel1.rowHeights = new int[]{40, 40, 40, 40};
        jPanel1.setLayout(gbl_jPanel1);
        jScrollPane2 = new javax.swing.JScrollPane();
        __fileList = new javax.swing.JList<>();
        
                __fileList.setModel(new DefaultListModel<String>()
                );
                __fileList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                jScrollPane2.setViewportView(__fileList);
                GridBagConstraints gbc_jScrollPane2 = new GridBagConstraints();
                gbc_jScrollPane2.gridheight = 4;
                gbc_jScrollPane2.gridwidth = 1;
                gbc_jScrollPane2.fill = GridBagConstraints.BOTH;
                gbc_jScrollPane2.insets = new Insets(0, 0, 0, 5);
                gbc_jScrollPane2.gridx = 0;
                gbc_jScrollPane2.gridy = 0;
                gbc_jScrollPane2.weightx = 1;
                jPanel1.add(jScrollPane2, gbc_jScrollPane2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));
        jPanel2.setPreferredSize(new java.awt.Dimension(120, 200));
        jPanel2.setLayout(new java.awt.GridBagLayout());
        jLabel1 = new javax.swing.JLabel();
        
                jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() & ~java.awt.Font.BOLD));
                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel1.setText("DPI :");
                jLabel1.setPreferredSize(new java.awt.Dimension(100, 15));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 5, 5, 10);
                jPanel2.add(jLabel1, gridBagConstraints);
        __dpiSpinner = new javax.swing.JSpinner();
        
                __dpiSpinner.setFont(__dpiSpinner.getFont().deriveFont(__dpiSpinner.getFont().getStyle() & ~java.awt.Font.BOLD));
                __dpiSpinner.setModel(new javax.swing.SpinnerNumberModel(600, 96, 2000, 10));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 0, 5, 0);
                jPanel2.add(__dpiSpinner, gridBagConstraints);
        jLabel2 = new javax.swing.JLabel();
        
                jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() & ~java.awt.Font.BOLD));
                jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel2.setText("Include BMASK :");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 5, 5, 10);
                jPanel2.add(jLabel2, gridBagConstraints);
        __includeBmaskCheckbox = new javax.swing.JCheckBox();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(1, 0, 5, 0);
        jPanel2.add(__includeBmaskCheckbox, gridBagConstraints);
        jLabel7 = new javax.swing.JLabel();
        
                jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getStyle() & ~java.awt.Font.BOLD));
                jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel7.setText("Paper X Offset [mm] :");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 5, 5, 10);
                jPanel2.add(jLabel7, gridBagConstraints);
        __paperXOffsetSpinner = new javax.swing.JSpinner();
        
                __paperXOffsetSpinner.setFont(__paperXOffsetSpinner.getFont().deriveFont(__paperXOffsetSpinner.getFont().getStyle() & ~java.awt.Font.BOLD));
                __paperXOffsetSpinner.setModel(new javax.swing.SpinnerNumberModel(10.0d, null, null, 1.0d));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 0, 5, 0);
                jPanel2.add(__paperXOffsetSpinner, gridBagConstraints);
        jLabel8 = new javax.swing.JLabel();
        
                jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getStyle() & ~java.awt.Font.BOLD));
                jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel8.setText("Paper Y Offset [mm] :");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 5, 5, 10);
                jPanel2.add(jLabel8, gridBagConstraints);
        __paperYOffsetSpinner = new javax.swing.JSpinner();
        
                __paperYOffsetSpinner.setFont(__paperYOffsetSpinner.getFont().deriveFont(__paperYOffsetSpinner.getFont().getStyle() & ~java.awt.Font.BOLD));
                __paperYOffsetSpinner.setModel(new javax.swing.SpinnerNumberModel(10.0d, null, null, 1.0d));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 0, 5, 0);
                jPanel2.add(__paperYOffsetSpinner, gridBagConstraints);
        jComboBox1 = new javax.swing.JComboBox<>();
        
                jComboBox1.setFont(jComboBox1.getFont().deriveFont(jComboBox1.getFont().getStyle() & ~java.awt.Font.BOLD));
                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "png", "jpg", "bmp" }));
                jComboBox1.addActionListener(formListener);
                jLabel9 = new javax.swing.JLabel();
                
                        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getStyle() & ~java.awt.Font.BOLD));
                        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                        jLabel9.setText("Image Type :");
                        gridBagConstraints = new java.awt.GridBagConstraints();
                        gridBagConstraints.gridx = 0;
                        gridBagConstraints.gridy = 4;
                        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                        gridBagConstraints.insets = new Insets(1, 5, 5, 10);
                        jPanel2.add(jLabel9, gridBagConstraints);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.insets = new Insets(1, 0, 5, 0);
                jPanel2.add(jComboBox1, gridBagConstraints);
        jPanel4.setLayout(new GridLayout(0, 2, 0, 0));
        jPanel4.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Step & Repeat"));
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 140));
        jPanel3.setLayout(new java.awt.GridBagLayout());
        jLabel3 = new javax.swing.JLabel();
        
                jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getStyle() & ~java.awt.Font.BOLD));
                jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel3.setText("X Copies :");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.insets = new Insets(1, 5, 5, 10);
                jPanel3.add(jLabel3, gridBagConstraints);
        __xCopiesSpinner = new javax.swing.JSpinner();
        
                __xCopiesSpinner.setFont(__xCopiesSpinner.getFont().deriveFont(__xCopiesSpinner.getFont().getStyle() & ~java.awt.Font.BOLD));
                __xCopiesSpinner.setModel(new javax.swing.SpinnerNumberModel(2, 1, 100, 1));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 0, 5, 5);
                jPanel3.add(__xCopiesSpinner, gridBagConstraints);
        jLabel5 = new javax.swing.JLabel();
        
                jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getStyle() & ~java.awt.Font.BOLD));
                jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel5.setText("X Gap [mm] :");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.ipady = 1;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.insets = new Insets(1, 5, 5, 10);
                jPanel3.add(jLabel5, gridBagConstraints);
        __xGapSpinner = new javax.swing.JSpinner();
        
                __xGapSpinner.setFont(__xGapSpinner.getFont().deriveFont(__xGapSpinner.getFont().getStyle() & ~java.awt.Font.BOLD));
                __xGapSpinner.setModel(new javax.swing.SpinnerNumberModel(10.0d, 1.0d, 100.0d, 1.0d));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 3;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 0, 5, 0);
                jPanel3.add(__xGapSpinner, gridBagConstraints);
        jLabel4 = new javax.swing.JLabel();
        
                jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() & ~java.awt.Font.BOLD));
                jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel4.setText("Y Copies :");
                jLabel4.setPreferredSize(new java.awt.Dimension(50, 14));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 5, 5, 10);
                jPanel3.add(jLabel4, gridBagConstraints);
        __yCopiesSpinner = new javax.swing.JSpinner();
        
                __yCopiesSpinner.setFont(__yCopiesSpinner.getFont().deriveFont(__yCopiesSpinner.getFont().getStyle() & ~java.awt.Font.BOLD));
                __yCopiesSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 0, 5, 5);
                jPanel3.add(__yCopiesSpinner, gridBagConstraints);
        jLabel6 = new javax.swing.JLabel();
        
                jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getStyle() & ~java.awt.Font.BOLD));
                jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jLabel6.setText("Y Gap [mm] :");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 5, 5, 10);
                jPanel3.add(jLabel6, gridBagConstraints);
        __yGapSpinner = new javax.swing.JSpinner();
        
                __yGapSpinner.setFont(__yGapSpinner.getFont().deriveFont(__yGapSpinner.getFont().getStyle() & ~java.awt.Font.BOLD));
                __yGapSpinner.setModel(new javax.swing.SpinnerNumberModel(10.0d, 1.0d, 100.0d, 1.0d));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 3;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new Insets(1, 0, 5, 0);
                jPanel3.add(__yGapSpinner, gridBagConstraints);
        gridBagConstraints_2 = new java.awt.GridBagConstraints();
        gridBagConstraints_2.insets = new Insets(0, 0, 0, 5);
        gridBagConstraints_2.gridx = 0;
        gridBagConstraints_2.gridy = 3;
        gridBagConstraints_2.weighty = 0.1;
        jPanel3.add(filler1, gridBagConstraints_2);
        jPanel4.add(jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(jPanel4, gridBagConstraints);
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));
        jPanel5.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 0, 5, 5);
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel5.add(__capturePane, gridBagConstraints);

        __renderButton.setFont(__renderButton.getFont().deriveFont(__renderButton.getFont().getStyle() & ~java.awt.Font.BOLD));
        __renderButton.setText("Render");
        __renderButton.addActionListener(formListener);
        gridBagConstraints_1 = new java.awt.GridBagConstraints();
        gridBagConstraints_1.anchor = GridBagConstraints.NORTHWEST;
        jPanel5.add(__renderButton, gridBagConstraints_1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        getContentPane().add(jPanel5, gridBagConstraints);

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == __fileListDeleteButton) {
                GerberNegativeCreatorMainFrame.this.__fileListDeleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == __fileListClearButton) {
                GerberNegativeCreatorMainFrame.this.__fileListClearButtonActionPerformed(evt);
            }
            else if (evt.getSource() == jComboBox1) {
                GerberNegativeCreatorMainFrame.this.jComboBox1ActionPerformed(evt);
            }
            else if (evt.getSource() == __renderButton) {
                GerberNegativeCreatorMainFrame.this.__renderButtonActionPerformed(evt);
            }
            else if (evt.getSource() == __fileAddButton) {
                int returnVal = fileChooser.showOpenDialog(GerberNegativeCreatorMainFrame.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    List<File> openedfiles = Arrays.asList(fileChooser.getSelectedFiles());
                    handleDroppedFiles( openedfiles );
                }
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void __fileListClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event___fileListClearButtonActionPerformed
        (( DefaultListModel<String> ) __fileList.getModel()).clear();
    }//GEN-LAST:event___fileListClearButtonActionPerformed

    private void __fileListDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event___fileListDeleteButtonActionPerformed
        int selectedIndex = __fileList.getSelectedIndex();

        if ( selectedIndex == -1 ) {
            return;
        }
        (( DefaultListModel<String> ) __fileList.getModel()).removeElementAt( selectedIndex );
    }//GEN-LAST:event___fileListDeleteButtonActionPerformed

    private void __renderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event___renderButtonActionPerformed
        __capturePane.clear();

        if ( __fileList.getModel().getSize() <= 0 ) {
            System.out.println( "No files to process" );
            return;
        }

        ArrayList<String> gerberFiles = new ArrayList<>();
        DefaultListModel<String> listModel = ( DefaultListModel<String> ) __fileList.getModel();

        /*
        collect all the dropped files
         */
        for ( int i = 0; i < listModel.size(); i++ ) {
            gerberFiles.add( listModel.get( i ) );
        }

        int dpi = ( int ) __dpiSpinner.getValue();
        boolean isBmaskIncluded = __includeBmaskCheckbox.getModel().isSelected();
        double paperXOffset = ( double ) __paperXOffsetSpinner.getValue();
        double paperYOffset = ( double ) __paperYOffsetSpinner.getValue();
        String imageType = ( String ) jComboBox1.getSelectedItem();
        String outFilePath = "";

        File fileObj = new File( gerberFiles.get( 0 ) );

        if ( fileObj.isFile() ) {
            outFilePath = fileObj.getParent() + File.separator + "negative_image." + imageType;
        }

        int xCopies = ( int ) __xCopiesSpinner.getValue();
        int yCopies = ( int ) __yCopiesSpinner.getValue();
        double xGap = ( double ) __xGapSpinner.getValue();
        double yGap = ( double ) __yGapSpinner.getValue();

        CommandOptions commandOptions = new CommandOptions( dpi, isBmaskIncluded, paperXOffset, paperYOffset, imageType, outFilePath, gerberFiles );
        StepAndRepeatValues stepAndRepeatValues = new StepAndRepeatValues( xCopies, yCopies, xGap, yGap );

        NegativeImageCreator imageCreator = new NegativeImageCreator();

        imageCreator.createImage( commandOptions, stepAndRepeatValues );
    }//GEN-LAST:event___renderButtonActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] ) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        try {
            for ( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() ) {
                if ( "GTK+".equals( info.getName() ) ) {
                    javax.swing.UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex ) {
            java.util.logging.Logger.getLogger( GerberNegativeCreatorMainFrame.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater( () -> {
            new GerberNegativeCreatorMainFrame().setVisible( true );
        } );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jinjuamla.GerberNegativeCreator.CapturePane __capturePane;
    private javax.swing.JSpinner __dpiSpinner;
    private javax.swing.JList<String> __fileList;
    private javax.swing.JButton __fileListClearButton;
    private javax.swing.JButton __fileListDeleteButton;
    private javax.swing.JButton __fileAddButton;
    private javax.swing.JCheckBox __includeBmaskCheckbox;
    private javax.swing.JSpinner __paperXOffsetSpinner;
    private javax.swing.JSpinner __paperYOffsetSpinner;
    private javax.swing.JButton __renderButton;
    private javax.swing.JSpinner __xCopiesSpinner;
    private javax.swing.JSpinner __xGapSpinner;
    private javax.swing.JSpinner __yCopiesSpinner;
    private javax.swing.JSpinner __yGapSpinner;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private GridBagConstraints gridBagConstraints_1;
    private GridBagConstraints gridBagConstraints_2;
    
    // End of variables declaration//GEN-END:variables
}
