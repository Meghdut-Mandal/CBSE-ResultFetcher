/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author Meghdut Mandal
 */
public class PageViewer extends javax.swing.JFrame {

    /**
     *
     */
    private final JScrollBar scrollbar = new JScrollBar(Adjustable.VERTICAL);

    /**
     *
     */
    private final transient Highlighter.HighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

    /**
     * Creates new form HTMLViewer
     */
    public PageViewer() {
        initComponents();

        if (scrollbar.getUI() instanceof WindowsScrollBarUI) {
            scrollbar.setUI(new WindowsHighlightScrollBarUI(pane));
        } else {
            scrollbar.setUI(new MetalHighlightScrollBarUI(pane));
        }
        scrollbar.setUnitIncrement(16);
        scrollbar.addMouseMotionListener(new MouseListener());
        this.scrollPane.setVerticalScrollBar(scrollbar);

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
                if ("Wjindows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PageViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PageViewer().setVisible(true));
    }

    /**
     *
     * @param jtc
     * @param pattern
     */
    private void setHighlight(JTextComponent jtc, String pattern) {
        Highlighter highlighter = jtc.getHighlighter();
        highlighter.removeAllHighlights();
        Document doc = jtc.getDocument();
        try {
            String text = doc.getText(0, doc.getLength());
            Matcher matcher = Pattern.compile(pattern.toUpperCase()).matcher(text);
            int pos = 0;
            while (matcher.find(pos)) {
                int start = matcher.start();
                int end = matcher.end();
                highlighter.addHighlight(start, end, highlightPainter);
                pos = end;
            }
            matcher = Pattern.compile(pattern.toLowerCase()).matcher(text);
            pos = 0;
            while (matcher.find(pos)) {
                int start = matcher.start();
                int end = matcher.end();
                highlighter.addHighlight(start, end, highlightPainter);
                pos = end;
            }
        } catch (BadLocationException | PatternSyntaxException ex) {
            ex.printStackTrace();
        }
        repaint();
    }

    private void setUPScrollUI() {

    }

    /**
     *
     * @param path
     */
    public void loadHtml(String path) {
        this.path.setText(path);

        new Thread(() -> {
            pane.setText("");
            File f = new File(path);

            java.awt.EventQueue.invokeLater(() -> {
                try {
                    info.setText("Loading File...");
                    this.scrollPane.setViewportView(null);
                    pane.setVisible(false);
                    pane.setPage(f.toURI().toURL());
                    pane.revalidate();
                    pane.setVisible(true);
                    this.scrollPane.setViewportView(pane);

                    info.setText("");

                } catch (Exception ex) {
                    Logger.getLogger(PageViewer.class.getName()).log(Level.SEVERE, null, ex);
                    info.setText(ex.getMessage());
                }

            });
            // StringReader stringReader = new StringReader(sb.toString());
            // pane.read(stringReader, null);
            pane.revalidate();
            pane.setVisible(false);
            pane.setVisible(true);
            info.setText("");
        }).start();
        // TODO add your handling code here:

    }

    /**
     *
     * @param Path
     * @throws IOException
     */
    @SuppressWarnings("ReplaceStringBufferByString")
    public void loadText(String Path) throws IOException {

        if (new File(Path).exists()) {
            StringBuilder sb = new StringBuilder();
            Files.lines(new File(Path).toPath()).forEach(line -> sb.append(line).append(String.format("%n")));
            pane.setContentType("text/plain");
            pane.setFont(new java.awt.Font("Consolas", 0, 14));
            pane.revalidate();
            pane.setText(sb.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regene rated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        path = new org.jdesktop.swingx.JXFormattedTextField();
        jButton1 = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        pane = new org.jdesktop.swingx.JXEditorPane();
        info = new javax.swing.JLabel();
        searchField = new org.jdesktop.swingx.JXSearchField();

        FormListener formListener = new FormListener();

        setTitle("HTMLViewer");

        jPanel2.setName("jPanel2"); // NOI18N

        jButton2.setText("Browse");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(formListener);

        path.setName("path"); // NOI18N
        path.setPrompt("Path To HTML File");
        path.setPromptForeground(new java.awt.Color(153, 153, 153));

        jButton1.setText("ReLoad");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(formListener);

        scrollPane.setName("scrollPane"); // NOI18N

        pane.setEditable(false);
        pane.setName("pane"); // NOI18N
        scrollPane.setViewportView(pane);

        info.setName("info"); // NOI18N

        searchField.setName("searchField"); // NOI18N
        searchField.addCaretListener(formListener);
        searchField.addActionListener(formListener);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(path, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(385, 385, 385))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, javax.swing.event.CaretListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == jButton2) {
                PageViewer.this.jButton2ActionPerformed(evt);
            }
            else if (evt.getSource() == jButton1) {
                PageViewer.this.jButton1ActionPerformed(evt);
            }
            else if (evt.getSource() == searchField) {
                PageViewer.this.searchFieldActionPerformed(evt);
            }
        }

        public void caretUpdate(javax.swing.event.CaretEvent evt) {
            if (evt.getSource() == searchField) {
                PageViewer.this.searchFieldCaretUpdate(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //  new Thread(this::loadFile).start();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void searchFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_searchFieldCaretUpdate
        // TODO add your handling code here:
        if (!searchField.getText().isEmpty()) {
            setHighlight(pane, this.searchField.getText());
            this.scrollPane.repaint();
        } else {
            pane.getHighlighter().removeAllHighlights();
            this.scrollPane.repaint();
        }

    }//GEN-LAST:event_searchFieldCaretUpdate

    class MouseListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel info;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel2;
    private org.jdesktop.swingx.JXEditorPane pane;
    private org.jdesktop.swingx.JXFormattedTextField path;
    private javax.swing.JScrollPane scrollPane;
    private org.jdesktop.swingx.JXSearchField searchField;
    // End of variables declaration//GEN-END:variables
}
