/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osprojectthree;

import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.*;
import javax.swing.*;
import java.math.*;

/**
 *
 * @author deadoggy
 */
public class Fronter extends javax.swing.JFrame {

    /**
     * Creates new form Fronter
     */
    public Fronter() {
        initComponents();
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        NewNameContainer = new StringBuilder();
        jTable1.setDefaultRenderer(Object.class, tcr);
        FileController = new FileManagement();
        FileController.load();
        FileController.setCurrentFolder(FileManagement.getFCB(0));
        Path = new Vector<Integer>();
        Path.add(FileController.getCurrentFolder().getFileID());
        TempFileID = -1;
        refreshTextField();
        refreshTable(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RenameDialog = new javax.swing.JDialog();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        TextEditor = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        RenameDialog.setTitle("Rename");
        RenameDialog.setMinimumSize(new java.awt.Dimension(237, 120));
        RenameDialog.setModal(true);
        RenameDialog.setType(java.awt.Window.Type.POPUP);

        jTextField2.setMinimumSize(new java.awt.Dimension(200, 21));

        jButton2.setText("确定");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("取消");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout RenameDialogLayout = new javax.swing.GroupLayout(RenameDialog.getContentPane());
        RenameDialog.getContentPane().setLayout(RenameDialogLayout);
        RenameDialogLayout.setHorizontalGroup(
            RenameDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RenameDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RenameDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(RenameDialogLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RenameDialogLayout.setVerticalGroup(
            RenameDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RenameDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(RenameDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.getAccessibleContext().setAccessibleParent(null);

        TextEditor.setTitle("Editor");
        TextEditor.setMinimumSize(new java.awt.Dimension(700, 340));
        TextEditor.setModal(true);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton4.setText("保存");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("取消");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TextEditorLayout = new javax.swing.GroupLayout(TextEditor.getContentPane());
        TextEditor.getContentPane().setLayout(TextEditorLayout);
        TextEditorLayout.setHorizontalGroup(
            TextEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TextEditorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(TextEditorLayout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(jButton4)
                .addGap(71, 71, 71)
                .addComponent(jButton5)
                .addContainerGap(254, Short.MAX_VALUE))
        );
        TextEditorLayout.setVerticalGroup(
            TextEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TextEditorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TextEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setRollover(true);

        jButton1.setText("后退");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jTextField1.setPreferredSize(new java.awt.Dimension(800, 28));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jTextField1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "文件名", "文件大小", "文件类型", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (FileController.getCurrentFolder().getFileID() == 0) {
            return;
        }
        FileController.setCurrentFolder(
                FileManagement.getFCB(FileController.getCurrentFolder().getParent())
        );
        refreshTable(FileController.getCurrentFolder().getFileID());
        Path.remove(Path.size() - 1);
        refreshTextField();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        //左键双击，打开文件
        if (2 == evt.getClickCount() && MouseEvent.BUTTON1 == evt.getButton()) {
            int row = jTable1.getSelectedRows()[0];
            Object objID = jTable1.getValueAt(row, 3);
            if (null == objID) {
                return;
            }
            int fileid = Integer.parseInt(objID.toString());
            if(1 == FileManagement.getFCB(fileid).getFileType())//file is a folder
            {
                Path.add(FileManagement.getFCB(fileid).getFileID());
                refreshTable(fileid);
                refreshTextField();
            }
            else
            {
                String text = FileController.readFile(fileid);
                TempFileID = fileid;
                jTextArea1.setText(text);
                TextEditor.setVisible(true);
            }
            
        }
        //左键单击，取消选择
        if(1 == evt.getClickCount() && MouseEvent.BUTTON1 == evt.getButton())
        {
            int row = jTable1.getSelectedRow();
            Object  obj;
            if(row != -1)
            {
                obj = jTable1.getValueAt(row, 3);
                if(null == obj)
                {
                    jTable1.clearSelection();
                }
            }
            

        }
            
        //右键单击
        if (1 == evt.getClickCount() && MouseEvent.BUTTON3 == evt.getButton()) {

            //空选
            if (0 == jTable1.getSelectedRows().length) {
                //创建弹出菜单
                JPopupMenu popmenu2 = new JPopupMenu();
                //创建菜单项
                JMenuItem item1 = new JMenuItem("New Folder");
                item1.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        
                        FileController.createFolder("NewFolder", (int)(Math.random() * 100000));
                        refreshTable(FileController.getCurrentFolder().getFileID());
                    }
                });
                popmenu2.add(item1);
                
                JMenuItem item2 = new JMenuItem("New Text File");
                item2.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        FileController.createFile("NewFile", (int)(Math.random() * 100000));
                        refreshTable(FileController.getCurrentFolder().getFileID());
                    }
                });
                popmenu2.add(item2);
                
                JMenuItem item3 = new JMenuItem("Format");
                item3.addMouseListener(new MouseAdapter(){
                    public void mousePressed(MouseEvent evt)
                    {
                        FileController.format(FileController.getCurrentFolder().getFileID());
                        refreshTable(FileController.getCurrentFolder().getFileID());
                    }
                });
                popmenu2.add(item3);
                
                
                popmenu2.show(jTable1, evt.getX(), evt.getY());

            } else {
                //选到文件
                int row = jTable1.getSelectedRows()[0];
                Object objID = jTable1.getValueAt(row, 3);
                if (null == objID) {
                    return;
                }
                final int fileid = Integer.parseInt(objID.toString());
                JPopupMenu popmenu = new JPopupMenu();
                //open item
                JMenuItem item1 = new JMenuItem("Open");
                item1.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        if(1 == FileManagement.getFCB(fileid).getFileType())
                        {
                            Path.add(FileManagement.getFCB(fileid).getFileID());
                            refreshTextField();
                            FileController.setCurrentFolder(FileManagement.getFCB(fileid));
                            refreshTable(FileController.getCurrentFolder().getFileID());
                        }
                        else
                        {
                            //code to open a text
                        }
                        
                        
                    }
                });
                popmenu.add(item1);
                //delete item
                JMenuItem item2 = new JMenuItem("Delete");
                item2.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        FileController.deleteFile(fileid);
                        refreshTable(FileController.getCurrentFolder().getFileID());
                    }
                });
                popmenu.add(item2);
                
                JMenuItem item3 = new JMenuItem("Rename");
                item3.addMouseListener(new MouseAdapter(){
                    public void mousePressed(MouseEvent evt)
                    {
                        TempFileID = fileid;
                        RenameDialog.setVisible(true);
                    }
                });
                popmenu.add(item3);
                
                popmenu.show(jTable1, evt.getX(), evt.getY());
            }

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        FileController.store();
    }//GEN-LAST:event_formWindowClosing

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        RenameDialog.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        NewNameContainer.append(jTextField2.getText());
        FileManagement.getFCB(TempFileID).setFileName(NewNameContainer.toString());
        
        jTextField2.setText("");
        NewNameContainer.delete(0, NewNameContainer.length());
        TempFileID = -1;
        refreshTable(FileController.getCurrentFolder().getFileID());
        RenameDialog.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        FileController.writeFile(FileManagement.getFCB(TempFileID), jTextArea1.getText());
        TextEditor.setVisible(false);
        refreshTable(FileController.getCurrentFolder().getFileID());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        TextEditor.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void refreshTable(int fileid) {
        
        FCB NewCurFile = FileManagement.getFCB(fileid);
        ArrayList<Integer> SubFileList = NewCurFile.getSubFiles();
        FileController.setCurrentFolder(NewCurFile);
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        if( SubFileList.size() < 30)
        {
            tableModel.setRowCount(30);
        }
        else
        {
            tableModel.setRowCount(SubFileList.size());
        }            
        int end = SubFileList.size();
        for (int j = 0; j < end; j++) {
            FCB tempFile = FileManagement.getFCB(SubFileList.get(j));
            jTable1.setValueAt(tempFile.getFileName(), j, 0);
            jTable1.setValueAt(tempFile.getFileSize(), j, 1);
            if (0 == tempFile.getFileType()) {
                jTable1.setValueAt("text", j, 2);
            } else {
                jTable1.setValueAt("folder", j, 2);
            }
            jTable1.setValueAt(tempFile.getFileID(), j, 3);
        }


    }

    private void refreshTextField() {
        StringBuilder StrPath = new StringBuilder();
        for (int i = 0; i < Path.size(); i++) {
            StrPath.append(FileManagement.getFCB(Path.get(i)).getFileName() + '\\');
        }
        jTextField1.setText(StrPath.toString());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fronter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fronter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fronter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fronter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fronter().setVisible(true);
            }
        });
    }

    private StringBuilder  NewNameContainer;
    private int TempFileID;
    private FileManagement FileController;
    private Vector<String> List;
    private Vector<Integer> Path;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog RenameDialog;
    private javax.swing.JDialog TextEditor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
