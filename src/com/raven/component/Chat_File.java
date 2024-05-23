package com.raven.component;

public class Chat_File extends javax.swing.JPanel {

    public Chat_File() {
        initComponents();
        setOpaque(false);
        cmdDow.setVisible(false);
    }

    public void setFile(String fileName, String size) {
        lbFileName.setText(fileName);
        lbFileSize.setText(size);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pictureBox1 = new com.raven.swing.PictureBox();
        progress = new com.raven.swing.Progress();
        jPanel1 = new javax.swing.JPanel();
        lbFileName = new javax.swing.JLabel();
        lbFileSize = new javax.swing.JLabel();
        cmdDow = new com.raven.component.OptionButton();

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/file.png"))); // NOI18N

        progress.setToolTipText("");
        progress.setProgressType(com.raven.swing.Progress.ProgressType.CANCEL);

        pictureBox1.setLayer(progress, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout pictureBox1Layout = new javax.swing.GroupLayout(pictureBox1);
        pictureBox1.setLayout(pictureBox1Layout);
        pictureBox1Layout.setHorizontalGroup(
            pictureBox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pictureBox1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );
        pictureBox1Layout.setVerticalGroup(
            pictureBox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pictureBox1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        lbFileName.setText("My File Name.file");
        jPanel1.add(lbFileName);

        lbFileSize.setForeground(new java.awt.Color(7, 98, 153));
        lbFileSize.setText("5 MB");
        jPanel1.add(lbFileSize);

        cmdDow.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/save.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pictureBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdDow, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmdDow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pictureBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.component.OptionButton cmdDow;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbFileName;
    private javax.swing.JLabel lbFileSize;
    private com.raven.swing.PictureBox pictureBox1;
    private com.raven.swing.Progress progress;
    // End of variables declaration//GEN-END:variables
}
