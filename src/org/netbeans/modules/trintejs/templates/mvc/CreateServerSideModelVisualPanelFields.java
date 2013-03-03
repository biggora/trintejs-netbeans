package org.netbeans.modules.trintejs.templates.mvc;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.netbeans.modules.trintejs.ui.FieldsTypes;

public final class CreateServerSideModelVisualPanelFields extends JPanel {

    static final String[] MODEL_FIELDS = {"name"};
    /**
     * Creates new form CreateServerSideModelVisualPanelFields
     */
    public CreateServerSideModelVisualPanelFields() {
        initComponents();
        TableColumn typeColumn = FieldsTable.getColumnModel().getColumn(1);
        JComboBox<String> comboBox = new JComboBox<String>();

        FieldsTypes[] fieldsTypes = FieldsTypes.values();
        for (int i = 0; i < fieldsTypes.length; i++) {
            String fieldsType = fieldsTypes[i].toString();
            comboBox.addItem(fieldsType);
        }

        typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    @Override
    public String getName() {
        return "Model Fields";
    }

    public JTable getFieldsTable() {
        return FieldsTable;
    }

    public static int[] getSelectedRowsModelIndices(JTable table) {
        if (table == null) {
            throw new NullPointerException("table == null");
        }

        int[] selectedRowIndices = table.getSelectedRows();
        int countSelected = selectedRowIndices.length;

        for (int i = 0; i < countSelected; i++) {
            selectedRowIndices[i] = table.convertRowIndexToModel(selectedRowIndices[i]);
        }

        return selectedRowIndices;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RemoveFieldButton = new javax.swing.JButton();
        AddFieldButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        FieldsTable = new javax.swing.JTable();

        org.openide.awt.Mnemonics.setLocalizedText(RemoveFieldButton, org.openide.util.NbBundle.getMessage(CreateServerSideModelVisualPanelFields.class, "CreateServerSideModelVisualPanelFields.RemoveFieldButton.text")); // NOI18N
        RemoveFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveFieldButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(AddFieldButton, org.openide.util.NbBundle.getMessage(CreateServerSideModelVisualPanelFields.class, "CreateServerSideModelVisualPanelFields.AddFieldButton.text")); // NOI18N
        AddFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFieldButtonActionPerformed(evt);
            }
        });

        FieldsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Field Name", "Field Type", "Default value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(FieldsTable);
        FieldsTable.getColumnModel().getColumn(0).setResizable(false);
        FieldsTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(CreateServerSideModelVisualPanelFields.class, "CreateServerSideModelVisualPanelFields.FieldsTable.columnModel.title0")); // NOI18N
        FieldsTable.getColumnModel().getColumn(1).setResizable(false);
        FieldsTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(CreateServerSideModelVisualPanelFields.class, "CreateServerSideModelVisualPanelFields.FieldsTable.columnModel.title1")); // NOI18N
        FieldsTable.getColumnModel().getColumn(2).setResizable(false);
        FieldsTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(CreateServerSideModelVisualPanelFields.class, "CreateServerSideModelVisualPanelFields.FieldsTable.columnModel.title2")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(AddFieldButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RemoveFieldButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RemoveFieldButton)
                    .addComponent(AddFieldButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AddFieldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFieldButtonActionPerformed
        DefaultTableModel TableModel = (DefaultTableModel) FieldsTable.getModel();
        int count = TableModel.getRowCount();
        String rowName = "name_" + (count + 1);
        
        TableModel.addRow(new String[]{
                    rowName, "String", null
                });
    }//GEN-LAST:event_AddFieldButtonActionPerformed

    private void RemoveFieldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveFieldButtonActionPerformed
        int[] selectedRowIndices = getSelectedRowsModelIndices(FieldsTable);
        DefaultTableModel tableModel = (DefaultTableModel) FieldsTable.getModel();
        int countRemoved = 0;

        for (int selectedRowIndex : selectedRowIndices) {
            int removeIndex = selectedRowIndex - countRemoved;
            tableModel.removeRow(removeIndex);
            countRemoved++;
        }
    }//GEN-LAST:event_RemoveFieldButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddFieldButton;
    private javax.swing.JTable FieldsTable;
    private javax.swing.JButton RemoveFieldButton;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
