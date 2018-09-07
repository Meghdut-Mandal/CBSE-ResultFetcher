/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import newton.resultApi.CBSEResult;
import java.util.List;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXSearchField;

/**
 *
 * @author Meghdut Mandal
 */
public class BatchView extends javax.swing.JPanel {

    private List<CBSEResult> results;
    private String id;
    private final App app;
    private final List<ResultView> resultViewList;
    private SearchHandler searchHandler;

    /**
     *
     * @param ad
     */
    public BatchView(App ad) {
        initComponents();
        searchHandler = new SearchHandler(this);
        app = ad;
        results = new java.util.ArrayList<>();
        resultViewList = new java.util.ArrayList<>();
        this.resultScrollView.getVerticalScrollBar().setUnitIncrement(16);
        this.resultScrollView.getVerticalScrollBar().setUI(new newton.fetcher.BatchViewScrollUI(this));

    }

    /**
     *
     * @return
     */
    public List<CBSEResult> getResults() {
        return results;
    }

    /**
     *
     * @param results
     */
    public void setResults(List<CBSEResult> results) {
        this.results = results;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Creates new form BatchView
     */
    public JScrollPane getResultScrollView() {
        return resultScrollView;
    }

    /**
     *
     * @return
     */
    public JXSearchField getSeachField() {
        return seachField;
    }

    /**
     *
     * @return
     */
    public String getPatternText() {
        return seachField.getText();
    }

    /**
     *
     * @return
     */
    public List<ResultView> getResultViewList() {
        return resultViewList;
    }

    /**
     *
     * @param res
     */
    public void addResultView(ResultView res) {
        resultViewList.add(res);
        res.setSearch(this.searchHandler.getRenderer());
        results.add(res.getResult());
        resultList.add(res);
        resultList.add(Box.createHorizontalStrut(25));
        resultList.revalidate();
        JScrollBar sb = resultScrollView.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.add(new BatchView(null));
        jFrame.setSize(600, 600);
        jFrame.pack();

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        resultScrollView = new javax.swing.JScrollPane();
        resultList = new javax.swing.JPanel();
        close = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        seachField = new org.jdesktop.swingx.JXSearchField();
        prevFind = new javax.swing.JButton();
        nextFind = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        resultScrollView1 = new javax.swing.JScrollPane();
        resultList1 = new javax.swing.JPanel();
        close1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        seachField1 = new org.jdesktop.swingx.JXSearchField();
        prevFind1 = new javax.swing.JButton();
        nextFind1 = new javax.swing.JButton();

        FormListener formListener = new FormListener();

        addFocusListener(formListener);

        resultScrollView.setName("resultScrollView"); // NOI18N

        resultList.setBackground(getBackground());
        resultList.setName("resultList"); // NOI18N
        resultList.setLayout(new javax.swing.BoxLayout(resultList, javax.swing.BoxLayout.PAGE_AXIS));
        resultScrollView.setViewportView(resultList);

        close.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        close.setText("X");
        close.setBorder(null);
        close.setContentAreaFilled(false);
        close.setFocusPainted(false);
        close.setFocusable(false);
        close.setName("close"); // NOI18N
        close.addActionListener(formListener);

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText("Find :");
        jLabel1.setName("jLabel1"); // NOI18N

        seachField.setName("seachField"); // NOI18N
        seachField.addActionListener(formListener);

        prevFind.setText("< Previous");
        prevFind.setName("prevFind"); // NOI18N
        prevFind.addActionListener(formListener);

        nextFind.setText("Next >");
        nextFind.setName("nextFind"); // NOI18N
        nextFind.addActionListener(formListener);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(seachField, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(prevFind)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextFind)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(seachField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prevFind)
                    .addComponent(nextFind))
                .addGap(0, 0, 0))
        );

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.addFocusListener(formListener);

        resultScrollView1.setName("resultScrollView1"); // NOI18N

        resultList1.setBackground(getBackground());
        resultList1.setName("resultList1"); // NOI18N
        resultList1.setLayout(new javax.swing.BoxLayout(resultList1, javax.swing.BoxLayout.PAGE_AXIS));
        resultScrollView1.setViewportView(resultList1);

        close1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        close1.setText("X");
        close1.setBorder(null);
        close1.setContentAreaFilled(false);
        close1.setFocusPainted(false);
        close1.setFocusable(false);
        close1.setName("close1"); // NOI18N
        close1.addActionListener(formListener);

        jPanel3.setName("jPanel3"); // NOI18N

        jLabel2.setText("Find :");
        jLabel2.setName("jLabel2"); // NOI18N

        seachField1.setName("seachField1"); // NOI18N
        seachField1.addActionListener(formListener);

        prevFind1.setText("< Previous");
        prevFind1.setName("prevFind1"); // NOI18N
        prevFind1.addActionListener(formListener);

        nextFind1.setText("Next >");
        nextFind1.setName("nextFind1"); // NOI18N
        nextFind1.addActionListener(formListener);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(seachField1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(prevFind1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextFind1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(seachField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prevFind1)
                    .addComponent(nextFind1))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(close1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(resultScrollView1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultScrollView1, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                    .addComponent(close1))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(resultScrollView)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultScrollView, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                    .addComponent(close))
                .addGap(0, 0, 0))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == close) {
                BatchView.this.closeActionPerformed(evt);
            }
            else if (evt.getSource() == seachField) {
                BatchView.this.seachFieldActionPerformed(evt);
            }
            else if (evt.getSource() == prevFind) {
                BatchView.this.prevFindActionPerformed(evt);
            }
            else if (evt.getSource() == nextFind) {
                BatchView.this.nextFindActionPerformed(evt);
            }
            else if (evt.getSource() == close1) {
                BatchView.this.close1ActionPerformed(evt);
            }
            else if (evt.getSource() == seachField1) {
                BatchView.this.seachField1ActionPerformed(evt);
            }
            else if (evt.getSource() == prevFind1) {
                BatchView.this.prevFind1ActionPerformed(evt);
            }
            else if (evt.getSource() == nextFind1) {
                BatchView.this.nextFind1ActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == BatchView.this) {
                BatchView.this.formFocusGained(evt);
            }
            else if (evt.getSource() == jPanel2) {
                BatchView.this.jPanel2formFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
        }
    }// </editor-fold>//GEN-END:initComponents

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed

        app.remove(this);

    }//GEN-LAST:event_closeActionPerformed

    private void prevFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevFindActionPerformed

    }//GEN-LAST:event_prevFindActionPerformed

    private void nextFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextFindActionPerformed

    }//GEN-LAST:event_nextFindActionPerformed

    private void seachFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seachFieldActionPerformed

        this.searchHandler.searchUpdate();
    }//GEN-LAST:event_seachFieldActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained

    }//GEN-LAST:event_formFocusGained

    private void close1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_close1ActionPerformed

    private void seachField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seachField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seachField1ActionPerformed

    private void prevFind1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevFind1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prevFind1ActionPerformed

    private void nextFind1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextFind1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nextFind1ActionPerformed

    private void jPanel2formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel2formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2formFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton close;
    private javax.swing.JButton close1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton nextFind;
    private javax.swing.JButton nextFind1;
    private javax.swing.JButton prevFind;
    private javax.swing.JButton prevFind1;
    private javax.swing.JPanel resultList;
    private javax.swing.JPanel resultList1;
    private javax.swing.JScrollPane resultScrollView;
    private javax.swing.JScrollPane resultScrollView1;
    private org.jdesktop.swingx.JXSearchField seachField;
    private org.jdesktop.swingx.JXSearchField seachField1;
    // End of variables declaration//GEN-END:variables
}