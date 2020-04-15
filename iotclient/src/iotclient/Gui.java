/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotclient;

import java.util.*;
import javax.swing.DefaultListModel;

public class Gui extends javax.swing.JFrame {

    public HashMap< String, HashMap< String, Vector>> fields = new HashMap< String, HashMap< String, Vector>>();

    public void addField(String fieldName) {
        if (fields.containsKey(fieldName) == false) {
            fields.put(fieldName, new HashMap< String, Vector>());
            populateFieldList();
        }
    }

    public void addSensorToField(String fieldName, String sensorName) {
        if (fields.containsKey(fieldName) == false) {
            addField(fieldName);
        }
        fields.get(fieldName).put(sensorName, new Vector());
        String selectedField = String.valueOf(fieldSelectBox.getModel().getElementAt(fieldSelectBox.getSelectedIndex()));
        if (selectedField.equals(fieldName)) {
            showWeatherStationsInField(fieldName);
        }
    }

    public void addSensorDataToField(String fieldName, String sensorName, String sensorData) {
        if (fields.containsKey(fieldName) == false) {
            addField(fieldName);
        }
        if (fields.get(fieldName).containsKey(sensorName) == false) {
            addSensorToField(fieldName, sensorName);
        }
        fields.get(fieldName).get(sensorName).add(sensorData);
        try {
            String selectedField = String.valueOf(fieldSelectBox.getModel().getElementAt(fieldSelectBox.getSelectedIndex()));
            String selectedSensor = String.valueOf(sensorList.getModel().getElementAt(sensorList.getSelectedIndex()));
            if (selectedField.equals(fieldName) && sensorName.equals(selectedSensor)) {
                showWeatherStationData(fieldName, sensorName);
            }
        } catch (Exception e) {
        }

    }

    public Gui() {
        initComponents();
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

        jLabel2.setText("WeatherStations ");

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fieldSelectBox, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fieldSelectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //exit button, exits the Gui
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void fieldSelectBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fieldSelectBoxItemStateChanged
        //whenever user switches the field, showWeatherStationsInField triggers
        //        String selectedField = fieldSelectBox.getSelectedItem().toString();
        String fieldName = String.valueOf(fieldSelectBox.getModel().getElementAt(fieldSelectBox.getSelectedIndex()));
        showWeatherStationsInField(fieldName);
    }//GEN-LAST:event_fieldSelectBoxItemStateChanged

    private void sensorListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sensorListMouseClicked
        //on mouse click index of the clicked item is sent to showWeatherStationData function which displays the local data
        //String indexOfWeatherStation = String.valueOf(sensorList.getModel().getElementAt(sensorList.getSelectedIndex()));
        try {
            String fieldName = String.valueOf(fieldSelectBox.getModel().getElementAt(fieldSelectBox.getSelectedIndex()));
            String sensorName = String.valueOf(sensorList.getModel().getElementAt(sensorList.getSelectedIndex()));
            showWeatherStationData(fieldName, sensorName);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_sensorListMouseClicked

    /**
     * appends the combobox with a up to date list of all available fields.
     *
     * The function takes in a string array of all available fields and updates
     * whenever a new field is added.
     *
     * @param Fields string array of fields.
     */
    private void populateFieldList() {
        fieldSelectBox.removeAllItems();
        Set< String> a = fields.keySet();
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
    public void showWeatherStationsInField(String selectedField) {
        try {
            DefaultListModel dlm = new DefaultListModel();
            HashMap< String, Vector> x = fields.get(selectedField);
            Set< String> a = x.keySet();
            for (String c : a) {
                dlm.addElement(c);
            }
            sensorList.setModel(dlm);
        } catch (Exception e) {
        }

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
    public void showWeatherStationData(String fieldName, String sensorName) {
        try {
            //System.out.println(fieldName);
            //System.out.println(sensorName);
            sensorDataField.setText(null);
            sensorDataField.append(sensorName);
            sensorDataField.append("\n");
            Vector v = fields.get(fieldName).get(sensorName);
            for (Object o : v) {
                sensorDataField.append((String) o);
            }

        } catch (Exception e) {

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
