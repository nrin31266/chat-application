package com.raven.component;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class DateViewIn extends javax.swing.JPanel {

    public JComboBox<String> getComBoxDay() {
        return comBoxDay;
    }

    public void setComBoxDay(JComboBox<String> comBoxDay) {
        this.comBoxDay = comBoxDay;
    }

    public JComboBox<String> getComboBoxMon() {
        return comboBoxMon;
    }

    public void setComboBoxMon(JComboBox<String> comboBoxMon) {
        this.comboBoxMon = comboBoxMon;
    }

    public JComboBox<String> getComboBoxYear() {
        return comboBoxYear;
    }

    public void setComboBoxYear(JComboBox<String> comboBoxYear) {
        this.comboBoxYear = comboBoxYear;
    }
    
    public DateViewIn() {
        initComponents();
        init();
    }
    
    private void init() {
        // Khởi tạo comboBoxMon với các tháng
        String[] months = new String[] {"01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"};
        comboBoxMon.setModel(new DefaultComboBoxModel<>(months));
        
        // Khởi tạo comboBoxYear với các năm từ 1900 đến năm hiện tại
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        DefaultComboBoxModel<String> yearModel = new DefaultComboBoxModel<>();
        for (int i = currentYear; i >= 1900; i--) {
            yearModel.addElement(String.valueOf(i));
        }
        
        comboBoxYear.setModel(yearModel);

        // Khởi tạo comboBoxDay với các ngày
        updateDays();

        // Thêm bộ lắng nghe sự kiện cho comboBoxMon và comboBoxYear
        comboBoxMon.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateDays();
                }
            }
        });

        comboBoxYear.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateDays();
                }
            }
        });
    }

    private void updateDays() {
        int selectedMonth = comboBoxMon.getSelectedIndex();
        int selectedYear = Integer.parseInt((String) comboBoxYear.getSelectedItem());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, selectedMonth);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        DefaultComboBoxModel<String> dayModel = new DefaultComboBoxModel<>();
        for (int i = 1; i <= daysInMonth; i++) {
            dayModel.addElement(String.valueOf(i));
        }
        
        comBoxDay.setModel(dayModel);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Day = new javax.swing.JLabel();
        comBoxDay = new javax.swing.JComboBox<>();
        Mon = new javax.swing.JLabel();
        comboBoxMon = new javax.swing.JComboBox<>();
        Year = new javax.swing.JLabel();
        comboBoxYear = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(296, 30));
        setPreferredSize(new java.awt.Dimension(266, 30));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Ns ");
        add(jLabel1);

        Day.setText("N");
        add(Day);

        comBoxDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(comBoxDay);

        Mon.setText("T");
        add(Mon);

        comboBoxMon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(comboBoxMon);

        Year.setText("N");
        add(Year);

        comboBoxYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(comboBoxYear);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Day;
    private javax.swing.JLabel Mon;
    private javax.swing.JLabel Year;
    private javax.swing.JComboBox<String> comBoxDay;
    private javax.swing.JComboBox<String> comboBoxMon;
    private javax.swing.JComboBox<String> comboBoxYear;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
