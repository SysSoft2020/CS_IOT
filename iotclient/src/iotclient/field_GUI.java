/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotclient;

import java.util.*;
import javax.swing.DefaultListModel;

public class field_GUI extends javax.swing.JFrame {

    HashMap<String, HashMap<String,Vector>> fields = new HashMap<String, HashMap<String,Vector>>();

    /**
     * Creates new form field_GUI
     */
    public field_GUI() {
        initComponents();
        fields.put("Field 1", new HashMap<String,Vector>());
        fields.put("Field 2", new HashMap<String,Vector>());
        fields.put("Field 3", new HashMap<String,Vector>());
        fields.get("Field 1").put("Weather station 1", new Vector());
                fields.get("Field 1").put("Weather station 2", new Vector());


        populateComboBox();  //populating the combobox with available Fields

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        fieldSelectBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sensorList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sensorDataField = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fieldSelectBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fieldSelectBoxItemStateChanged(evt);
            }
        });

        jLabel1.setText("Fields:");

        sensorList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sensorListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(sensorList);

        jLabel2.setText("WeatherStation/s in selected field:");

        sensorDataField.setColumns(20);
        sensorDataField.setRows(5);
        jScrollPane2.setViewportView(sensorDataField);

        jLabel3.setText("Data from selected WeatherStation:");

        jButton2.setText("EXIT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fieldSelectBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 119, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 336, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldSelectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //exit button, exits the gui
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void fieldSelectBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fieldSelectBoxItemStateChanged
        //whenever user switches the field, showWeatherStationsInField triggers
        showWeatherStationsInField();
    }//GEN-LAST:event_fieldSelectBoxItemStateChanged

    private void sensorListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sensorListMouseClicked
        //on mouse click index of the clicked item is sent to showWeatherStationData function which displays the local data
        System.out.print("Ola");
                String indexOfWeatherStation = String.valueOf(sensorList.getModel().getElementAt(sensorList.getSelectedIndex()));
        System.out.print(indexOfWeatherStation);

    }//GEN-LAST:event_sensorListMouseClicked

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
            java.util.logging.Logger.getLogger(field_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(field_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(field_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(field_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new field_GUI().setVisible(true);
            }
        });
    }

    /**
     * appends the combobox with a up to date list of all available fields.
     *
     * The function takes in a string array of all available fields and updates
     * whenever a new field is added.
     *
     * @param Fields string array of fields.
     */
    public void populateComboBox() {
        Set<String> a = fields.keySet();
        for (String x : a) {
            fieldSelectBox.addItem(x);
        }

    }

    /**
     * Displays all Weather Stations located on the field user selects.
     *
     * This method utilizes the jComboBox1ItemStateChanged, so whenever combo
     * box value changes the function displays appropriate weather stations for
     * selected field.
     *
     * Use this function when you need to display all available weather stations
     * for a field.
     *
     * @param String array containing names of available fields.
     */
    public void showWeatherStationsInField() {
        String selectedField = fieldSelectBox.getSelectedItem().toString();
        DefaultListModel dlm = new DefaultListModel();
        
        HashMap<String,Vector> x = fields.get(selectedField);
        Set<String> a = x.keySet();
        for (String c : a) {
            dlm.addElement(c);
        }
        /*
        //ArrayList<String> field = new ArrayList<String>();

        String[] field1 = {"Weather Station 1", "Weather Station 2", "Weather Station 3", "Weather Station 4"};
        String[] field2 = {"Weather Station 1", "Weather Station 2", "Weather Station 3", "Weather Station 4", "Weather Station 5", "Weather Station 6"};
        String[] field3 = {"Weather Station 1", "Weather Station 2", "Weather Station 3"};
        String[] field4 = {"Weather Station 1", "Weather Station 2"};
        String[] field5 = {"Weather Station 1"};
        String[] field6 = {"Weather Station 1", "Weather Station 2", "Weather Station 3"};
        String[] field7 = {"Weather Station 1", "Weather Station 2"};

        if (selectedField == "Field 1") {
            for (int i = 0; i < field1.length; i++) {
                dlm.addElement(field1[i]);
            }
        }
        if (selectedField == "Field 2") {
            for (int i = 0; i < field2.length; i++) {
                dlm.addElement(field2[i]);
            }
        }
        if (selectedField == "Field 3") {
            for (int i = 0; i < field3.length; i++) {
                dlm.addElement(field3[i]);
            }
        }
        if (selectedField == "Field 4") {
            for (int i = 0; i < field4.length; i++) {
                dlm.addElement(field4[i]);
            }
        }
        if (selectedField == "Field 5") {
            for (int i = 0; i < field5.length; i++) {
                dlm.addElement(field5[i]);
            }
        }
        if (selectedField == "Field 6") {
            for (int i = 0; i < field6.length; i++) {
                dlm.addElement(field6[i]);
            }
        }
        if (selectedField == "Field 7") {
            for (int i = 0; i < field7.length; i++) {
                dlm.addElement(field7[i]);
            }
        }
        */
        sensorList.setModel(dlm);
    }

    /**
     * Displays Weather Station data according to unique index.
     *
     * This method utilizes the mouse click event, takes the current selected
     * index from JList and matches it with the appropriate string array to
     * display in Text Area.
     *
     * Use this function when you need to display measurements made by a Weather
     * Station on the specified field.
     */
    public void showWeatherStationData() {

        String indexOfWeatherStation = String.valueOf(sensorList.getModel().getElementAt(sensorList.getSelectedIndex()));

        String[] data1 = {"Weather Station 1: \n", "weather index: 12 \n", "erosion: 12%\n", "humidity: 4%\n", "chance of rain: 0%\n"};
        String[] data2 = {"Weather Station 2: \n", "weather index: 11 \n", "erosion: 22%\n", "humidity: 34%\n", "chance of rain: 12%\n"};
        String[] data3 = {"Weather Station 3: \n", "weather index: 22 \n", "erosion: 9%\n", "humidity: 30%\n", "chance of rain: 99%\n"};
        String[] data4 = {"Weather Station 4: \n", "weather index: 7 \n", "erosion: 97%\n", "humidity: 42%\n", "chance of rain: 60%\n"};
        String[] data5 = {"Weather Station 5: \n", "weather index: 2 \n", "erosion: 33%\n", "humidity: 57%\n", "chance of rain: 20%\n"};
        String[] data6 = {"Weather Station 6: \n", "weather index: 100 \n", "erosion: 18%\n", "humidity: 2%\n", "chance of rain: 100%\n"};

        if (indexOfWeatherStation == "Weather Station 1") {
            for (int i = 0; i < data1.length; i++) {
                sensorDataField.append(data1[i]);
            }
            sensorDataField.append("\n");
        }
        if (indexOfWeatherStation == "Weather Station 2") {
            for (int i = 0; i < data2.length; i++) {
                sensorDataField.append(data2[i]);
            }
            sensorDataField.append("\n");
        }
        if (indexOfWeatherStation == "Weather Station 3") {
            for (int i = 0; i < data3.length; i++) {
                sensorDataField.append(data3[i]);
            }
            sensorDataField.append("\n");
        }
        if (indexOfWeatherStation == "Weather Station 4") {
            for (int i = 0; i < data4.length; i++) {
                sensorDataField.append(data4[i]);
            }
            sensorDataField.append("\n");
        }
        if (indexOfWeatherStation == "Weather Station 5") {
            for (int i = 0; i < data5.length; i++) {
                sensorDataField.append(data5[i]);
            }
            sensorDataField.append("\n");
        }
        if (indexOfWeatherStation == "Weather Station 6") {
            for (int i = 0; i < data6.length; i++) {
                sensorDataField.append(data6[i]);
            }
            sensorDataField.append("\n");
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> fieldSelectBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea sensorDataField;
    private javax.swing.JList<String> sensorList;
    // End of variables declaration//GEN-END:variables
}
