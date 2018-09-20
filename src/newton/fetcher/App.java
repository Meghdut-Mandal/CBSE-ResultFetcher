/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import newton.fetcher.ResultAnalyser.MODE;
import newton.resultApi.HtmlUnitClient;
import newton.resultApi.ResultClient;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFormattedTextField;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 *
 *
 * @author Meghdut Mandal
 */
public class App extends JFrame {
    //   JFrame {

    private static final long serialVersionUID = 1L;
    private final List<BatchView> batches;
    private BatchView Current;
    private CBSEResultLoader re;
    private final PageViewer view;
    private final ResultClient fetch;

    private File userdir;
    private int batch = 0;

    /**
     *
     */
    public App() {

        initComponents();
        userdir = new File(System.getProperty("user.dir") + "\\ResultScraper");
        if (!userdir.exists()) {
            userdir.mkdirs();
        }
        this.savePath.setText(this.userdir.getAbsolutePath());
        this.batches = new java.util.ArrayList<>();
        fetch = new HtmlUnitClient();
        this.startField.setText("6622401");
        this.endField.setText("6622430");
        view = new PageViewer();
        YearSlectBoxItemStateChanged(null);
        this.mergeProgressBar.setUI(new ProgressCircleUI());
    }

    /**
     *
     * @param Current
     */
    public void setCurrentBatch(BatchView Current) {
        this.Current = Current;
    }

    /**
     *
     * @return
     */
    public JProgressBar getMergeProgressBar() {
        return mergeProgressBar;
    }

    /**
     *
     * @param text
     */
    public void setAnalysisInfo(String text) {
        this.mergeInfo.setText(text);
    }

    /**
     *
     * @param prog
     */
    public void setProgress(int prog) {
        this.mergeProgressBar.setValue(prog);
    }

    /**
     *
     * @param view
     */
    public void remove(BatchView view) {
        batches.remove(view);
        this.batchTabes.remove(view);
        Current = (BatchView) batchTabes.getSelectedComponent();
        this.batch--;
    }

    /**
     *
     * @return
     */
    public File getUserdir() {
        return userdir;
    }

    /**
     * Creates new form HTML
     *
     * @return
     */
    public JXBusyLabel getInfoLable() {

        return infoLable;
    }

    /**
     *
     * @return
     */
    public int getEndRegno() {
        return Integer.parseInt(endField.getText());
    }

    /**
     *
     * @return
     */
    public int getStartRegno() {

        return Integer.parseInt(startField.getText());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox YearSlectBox;
    private javax.swing.JButton aboutButton;
    private javax.swing.JTabbedPane batchTabes;
    private javax.swing.JButton browseSavePath;

    private void launchAnalyser(MODE mod) {
        this.setAnalyseButtonsEnabled(false);
        JFileChooser dir = new JFileChooser(this.getSavePath().getText());
        dir.setDialogType(JFileChooser.OPEN_DIALOG);
        dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int showOpenDialog = dir.showOpenDialog(this);

        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            try {
                File resDir = dir.getSelectedFile();
                this.getMergePathField().setText(resDir.getAbsolutePath());

                ResultAnalyser resultsMerger = new ResultAnalyser(this, resDir, mod);
                resultsMerger.execute();
            } catch (Exception ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            this.setAnalyseButtonsEnabled(true);

        }
    }

    /**
     *
     * @return
     */
    public JFormattedTextField getMergeOutput() {
        return mergeOutput;
    }

    private javax.swing.JPanel cbsePanel;

    /**
     *
     * @return
     */
    public PageViewer getHTMLViewer() {
        return view;
    }

    /**
     *
     * @return
     */
    public JXButton getStartButton() {
        return startButton;
    }

    /**
     *
     * @return
     */
    public String getSchCode() {
        return this.schCodeField.getText();
    }

    /**
     *
     * @return
     */
    public String getCenterNo() {
        return this.centerNoField.getText();
    }

    /**
     *
     * @param isshowing
     */
    public void setAnalyseButtonsEnabled(boolean isshowing) {
        this.mergeButton.setEnabled(isshowing);
        this.subjAnalyse.setEnabled(isshowing);
        this.totalAnalyse.setEnabled(isshowing);

    }

    /**
     *
     * @return
     */
    public JButton getMergeButton() {
        return mergeButton;
    }

    private org.jdesktop.swingx.JXFormattedTextField centerNoField;

    // Code for dispatching events from components to event handlers.
    private javax.swing.JCheckBox defProvide;
    private org.jdesktop.swingx.JXFormattedTextField endField;
    private org.jdesktop.swingx.JXBusyLabel infoLable;

    private void mergeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mergeButtonActionPerformed
        launchAnalyser(MODE.RESULT_MERGE);
    }//GEN-LAST:event_mergeButtonActionPerformed

    private void savePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePathActionPerformed
        // TODO add your handling code here:
        if (this.savePath.getText().isEmpty()) {
            return;
        }
        if (new File(this.savePath.getText()).exists()) {
            ErrorDialog.showError("Sorry ! cound'nt find the required Folder");
            this.savePath.setText("");
            return;
        }

        userdir = new File(this.savePath.getText() + "\\ResultScraper");
        if (!userdir.exists()) {
            if (!userdir.mkdirs()) {
                ErrorDialog.showError("Unable to create the Save folder !");
                this.savePath.setText("");
                return;
            }
        }
        this.savePath.setText(this.userdir.getAbsolutePath());
    }//GEN-LAST:event_savePathActionPerformed

    private void YearSlectBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_YearSlectBoxItemStateChanged
        // TODO add your handling code here:
        //  System.out.println(this.getSelectedYear());

        schCodeField.setEnabled(getSelectedYear() >= 2016);
        centerNoField.setEnabled(getSelectedYear() >= 2017);
        if (this.defProvide.isSelected()) {
            if (getSelectedYear() == 2016) {
                schCodeField.setText("04040");
                this.startField.setText("2644300");
                this.endField.setText("2644309");
            } else if (getSelectedYear() < 2016) {
                this.startField.setText("6622401");
                this.endField.setText("6622430");
            } else if (getSelectedYear() == 2018) {
                this.startField.setText("6625645");
                this.endField.setText("6625999");
                this.schCodeField.setText("08423");
                this.centerNoField.setText("6219");
            }

        }

    }//GEN-LAST:event_YearSlectBoxItemStateChanged

    private void viewHTMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewHTMLActionPerformed
        // TODO add your handling code here:

        javax.swing.JFileChooser fi = new javax.swing.JFileChooser(this.userdir.getAbsoluteFile());
        fi.setDialogTitle("Open HTML Page");
        fi.setDialogType(JFileChooser.OPEN_DIALOG);
        fi.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (fi.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            view.loadHtml(fi.getSelectedFile().getAbsolutePath());
            view.setVisible(true);
        }

    }//GEN-LAST:event_viewHTMLActionPerformed

    private void defProvideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defProvideActionPerformed
        // TODO add your handling code here

    }//GEN-LAST:event_defProvideActionPerformed

    private void YearSlectBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearSlectBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearSlectBoxActionPerformed

    private void batchTabesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_batchTabesPropertyChange

    }//GEN-LAST:event_batchTabesPropertyChange

    private void subjAnalyseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjAnalyseActionPerformed
        launchAnalyser(MODE.SUBJECTWISELIST);
    }//GEN-LAST:event_subjAnalyseActionPerformed

    private void totalAnalyseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalAnalyseActionPerformed

        launchAnalyser(MODE.TOTALWISELIST);

    }//GEN-LAST:event_totalAnalyseActionPerformed

    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton mergeButton;
    private javax.swing.JLabel mergeInfo;
    private javax.swing.JFormattedTextField mergeOutput;
    private javax.swing.JFormattedTextField mergePathField;
    private javax.swing.JProgressBar mergeProgressBar;
    private org.jdesktop.swingx.JXFormattedTextField savePath;
    private org.jdesktop.swingx.JXFormattedTextField schCodeField;
    private javax.swing.JPanel settingsPanel;
    private org.jdesktop.swingx.JXButton startButton;
    private org.jdesktop.swingx.JXFormattedTextField startField;
    private javax.swing.JButton stopButton;
    private javax.swing.JButton subjAnalyse;
    private javax.swing.JPanel toolPanel;
    private javax.swing.JButton totalAnalyse;
    private javax.swing.JButton viewHTML;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }

            new App().setVisible(true);
        });
    }

    /**
     * @return
     */
    public ResultClient getCBSEFetcher() {
        return fetch;
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    public int getSelectedYear() {
        return Integer.parseInt(Objects.requireNonNull(this.YearSlectBox.getSelectedItem()).toString());
    }

    /**
     * @return
     */
    public BatchView getNewBatch() {
        BatchView newbatch = new BatchView(this);
        newbatch.setId(batch + " " + getSelectedYear());
        this.batches.add(newbatch);
        this.batchTabes.add(newbatch.getId(), newbatch);
        this.batchTabes.setSelectedIndex(batch);
        batch++;
        return newbatch;

    }

    /**
     * @return
     */
    private JXFormattedTextField getSavePath() {
        return savePath;
    }

    /**
     * @return
     */
    private JFormattedTextField getMergePathField() {
        return mergePathField;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        cbsePanel = new javax.swing.JPanel();
        endField = new org.jdesktop.swingx.JXFormattedTextField();
        startButton = new org.jdesktop.swingx.JXButton();
        YearSlectBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        infoLable = new org.jdesktop.swingx.JXBusyLabel();
        startField = new org.jdesktop.swingx.JXFormattedTextField();
        schCodeField = new org.jdesktop.swingx.JXFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        centerNoField = new org.jdesktop.swingx.JXFormattedTextField();
        stopButton = new javax.swing.JButton();
        toolPanel = new javax.swing.JPanel();
        mergeButton = new javax.swing.JButton();
        viewHTML = new javax.swing.JButton();
        mergeProgressBar = new javax.swing.JProgressBar();
        mergeInfo = new javax.swing.JLabel();
        mergePathField = new javax.swing.JFormattedTextField();
        mergeOutput = new javax.swing.JFormattedTextField();
        subjAnalyse = new javax.swing.JButton();
        totalAnalyse = new javax.swing.JButton();
        settingsPanel = new javax.swing.JPanel();
        savePath = new org.jdesktop.swingx.JXFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        browseSavePath = new javax.swing.JButton();
        defProvide = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        aboutButton = new javax.swing.JButton();
        batchTabes = new javax.swing.JTabbedPane();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Result Fetcher");
        setLocationByPlatform(true);

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        cbsePanel.setName("cbsePanel"); // NOI18N

        endField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        endField.setName("endField"); // NOI18N
        endField.setPrompt("End");
        endField.setPromptForeground(new java.awt.Color(153, 153, 153));

        startButton.setText("Start ! ");
        startButton.setName("startButton"); // NOI18N
        startButton.addActionListener(formListener);

        YearSlectBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004"}));
        YearSlectBox.setName("YearSlectBox"); // NOI18N
        YearSlectBox.addItemListener(formListener);
        YearSlectBox.addActionListener(formListener);

        jLabel2.setText("Reg Number From ");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel5.setText("CBSE YEAR ");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel3.setText("To ");
        jLabel3.setName("jLabel3"); // NOI18N

        infoLable.setName("infoLable"); // NOI18N

        startField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###"))));
        startField.setName("startField"); // NOI18N
        startField.setPrompt("Start");
        startField.setPromptForeground(new java.awt.Color(153, 153, 153));

        schCodeField.setToolTipText("SchoolCode year 2016  onward");
        schCodeField.setEnabled(false);
        schCodeField.setFormatterFactory(null);
        schCodeField.setName("schCodeField"); // NOI18N
        schCodeField.setPrompt("School Code");
        schCodeField.setPromptForeground(new java.awt.Color(153, 153, 153));

        jLabel6.setText("School No.");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel9.setText("Center No.");
        jLabel9.setName("jLabel9"); // NOI18N

        centerNoField.setToolTipText("Center number  2017 onwards");
        centerNoField.setEnabled(false);
        centerNoField.setFormatterFactory(null);
        centerNoField.setName("centerNoField"); // NOI18N
        centerNoField.setPrompt("Center Number");
        centerNoField.setPromptForeground(new java.awt.Color(153, 153, 153));

        stopButton.setText("Stop");
        stopButton.setName("stopButton"); // NOI18N
        stopButton.addActionListener(formListener);

        javax.swing.GroupLayout cbsePanelLayout = new javax.swing.GroupLayout(cbsePanel);
        cbsePanel.setLayout(cbsePanelLayout);
        cbsePanelLayout.setHorizontalGroup(
                cbsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cbsePanelLayout.createSequentialGroup()
                                          .addContainerGap()
                                          .addGroup(cbsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addGroup(cbsePanelLayout.createSequentialGroup()
                                                                              .addComponent(infoLable, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                              .addGap(37, 37, 37)
                                                                              .addComponent(jLabel9)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(centerNoField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(cbsePanelLayout.createSequentialGroup()
                                                                              .addComponent(jLabel5)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(YearSlectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                              .addComponent(jLabel2)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(startField, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(jLabel3)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(endField, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(jLabel6)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(schCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                          .addGap(30, 30, 30)
                                          .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                          .addComponent(stopButton)
                                          .addContainerGap())
        );
        cbsePanelLayout.setVerticalGroup(
                cbsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cbsePanelLayout.createSequentialGroup()
                                          .addContainerGap()
                                          .addGroup(cbsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel2)
                                                            .addComponent(startField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel3)
                                                            .addComponent(endField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(YearSlectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel5)
                                                            .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(schCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel6)
                                                            .addComponent(stopButton))
                                          .addGroup(cbsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(cbsePanelLayout.createSequentialGroup()
                                                                              .addGap(12, 12, 12)
                                                                              .addComponent(infoLable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(cbsePanelLayout.createSequentialGroup()
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addGroup(cbsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(centerNoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(jLabel9))))
                                          .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CBSE Results", cbsePanel);

        toolPanel.setName("toolPanel"); // NOI18N

        mergeButton.setText("Merge  Results");
        mergeButton.setName("mergeButton"); // NOI18N
        mergeButton.addActionListener(formListener);

        viewHTML.setText("View HTML Page");
        viewHTML.setName("viewHTML"); // NOI18N
        viewHTML.addActionListener(formListener);

        mergeProgressBar.setUI(new ProgressCircleUI());
        mergeProgressBar.setBorderPainted(false);
        mergeProgressBar.setName("mergeProgressBar"); // NOI18N
        mergeProgressBar.setStringPainted(true);

        mergeInfo.setName("mergeInfo"); // NOI18N

        mergePathField.setEditable(false);
        mergePathField.setName("mergePathField"); // NOI18N

        mergeOutput.setEditable(false);
        mergeOutput.setName("mergeOutput"); // NOI18N

        subjAnalyse.setText("Subject Wise Result");
        subjAnalyse.setName("subjAnalyse"); // NOI18N
        subjAnalyse.addActionListener(formListener);

        totalAnalyse.setText("Total Wise Result");
        totalAnalyse.setName("totalAnalyse"); // NOI18N
        totalAnalyse.addActionListener(formListener);

        javax.swing.GroupLayout toolPanelLayout = new javax.swing.GroupLayout(toolPanel);
        toolPanel.setLayout(toolPanelLayout);
        toolPanelLayout.setHorizontalGroup(
                toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(toolPanelLayout.createSequentialGroup()
                                          .addContainerGap()
                                          .addGroup(toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                              .addGroup(toolPanelLayout.createSequentialGroup()
                                                                                                .addComponent(mergeButton)
                                                                                                .addGap(44, 44, 44))
                                                                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toolPanelLayout.createSequentialGroup()
                                                                                      .addComponent(subjAnalyse)
                                                                                      .addGap(18, 18, 18)))
                                                            .addGroup(toolPanelLayout.createSequentialGroup()
                                                                              .addComponent(totalAnalyse)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                          .addGroup(toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(mergePathField, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                                            .addComponent(mergeOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                                            .addComponent(mergeInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                          .addGap(10, 10, 10)
                                          .addComponent(mergeProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                          .addGap(51, 51, 51)
                                          .addComponent(viewHTML)
                                          .addGap(0, 226, Short.MAX_VALUE))
        );
        toolPanelLayout.setVerticalGroup(
                toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(toolPanelLayout.createSequentialGroup()
                                          .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                          .addGroup(toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(toolPanelLayout.createSequentialGroup()
                                                                              .addComponent(mergeButton)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(subjAnalyse)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(totalAnalyse)
                                                                              .addGap(22, 22, 22))
                                                            .addGroup(toolPanelLayout.createSequentialGroup()
                                                                              .addGap(12, 12, 12)
                                                                              .addGroup(toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                                .addGroup(toolPanelLayout.createSequentialGroup()
                                                                                                                  .addGap(3, 3, 3)
                                                                                                                  .addComponent(mergePathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                                  .addComponent(mergeOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addGroup(toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                  .addComponent(mergeProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                  .addComponent(viewHTML)))
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                              .addComponent(mergeInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                              .addContainerGap())))
        );

        jTabbedPane1.addTab("Tools", toolPanel);

        settingsPanel.setName("settingsPanel"); // NOI18N

        savePath.setName("savePath"); // NOI18N
        savePath.setPrompt("Path To Folder");
        savePath.setPromptForeground(new java.awt.Color(153, 153, 153));
        savePath.addActionListener(formListener);

        jLabel4.setText("Save Results to ");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel1.setText("Saved Results  : ");
        jLabel1.setName("jLabel1"); // NOI18N

        browseSavePath.setText("Browse");
        browseSavePath.setName("browseSavePath"); // NOI18N
        browseSavePath.addActionListener(formListener);

        defProvide.setSelected(true);
        defProvide.setText("Provie default Range for roll numbers etc.");
        defProvide.setName("defProvide"); // NOI18N
        defProvide.addActionListener(formListener);

        jButton1.setText("Advanced >>");
        jButton1.setName("jButton1"); // NOI18N

        aboutButton.setText("About");
        aboutButton.setName("aboutButton"); // NOI18N
        aboutButton.addActionListener(formListener);

        javax.swing.GroupLayout settingsPanelLayout = new javax.swing.GroupLayout(settingsPanel);
        settingsPanel.setLayout(settingsPanelLayout);
        settingsPanelLayout.setHorizontalGroup(
                settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(settingsPanelLayout.createSequentialGroup()
                                          .addGap(45, 45, 45)
                                          .addComponent(jLabel1)
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                          .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(settingsPanelLayout.createSequentialGroup()
                                                                              .addComponent(jLabel4)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                              .addComponent(savePath, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                              .addGap(18, 18, 18)
                                                                              .addComponent(browseSavePath, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                                                            .addGroup(settingsPanelLayout.createSequentialGroup()
                                                                              .addComponent(defProvide)
                                                                              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                              .addComponent(aboutButton)
                                                                              .addGap(49, 49, 49)
                                                                              .addComponent(jButton1)))
                                          .addContainerGap())
        );
        settingsPanelLayout.setVerticalGroup(
                settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(settingsPanelLayout.createSequentialGroup()
                                          .addContainerGap()
                                          .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel1)
                                                            .addComponent(jLabel4)
                                                            .addComponent(savePath, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(browseSavePath))
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                          .addGroup(settingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(defProvide)
                                                            .addComponent(jButton1)
                                                            .addComponent(aboutButton))
                                          .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Settings", settingsPanel);

        batchTabes.setName("batchTabes"); // NOI18N
        batchTabes.setOpaque(true);
        batchTabes.addPropertyChangeListener(formListener);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
                        .addComponent(batchTabes)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                          .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                          .addComponent(batchTabes, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
        );

        pack();
    }

    private void browseSavePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseSavePathActionPerformed
        // TODO add your handling code here:
        JFileChooser dir = new JFileChooser(savePath.getText());
        dir.setDialogType(JFileChooser.OPEN_DIALOG);
        dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int showOpenDialog = dir.showOpenDialog(this);

        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            File selectedFile = dir.getSelectedFile();
            savePath.setText(selectedFile.getAbsolutePath());
            savePathActionPerformed(evt);
        }

    }//GEN-LAST:event_browseSavePathActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (re != null && re.isDone()) {
            re = null;
        }
        if (re == null) {
            re = new CBSEResultLoader(this);
            re.execute();
        }

    }//GEN-LAST:event_startButtonActionPerformed

    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed

        AboutDialog dialog = new AboutDialog(this, true);
        dialog.setVisible(true);

    }//GEN-LAST:event_aboutButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        // TODO add your handling code here:
        if (re != null) {
            re.cancel(true);
            re = null;
        }
    }//GEN-LAST:event_stopButtonActionPerformed

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.ItemListener, java.beans.PropertyChangeListener {
        FormListener() {
        }

        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == startButton) {
                App.this.startButtonActionPerformed(evt);
            } else if (evt.getSource() == YearSlectBox) {
                App.this.YearSlectBoxActionPerformed(evt);
            } else if (evt.getSource() == mergeButton) {
                App.this.mergeButtonActionPerformed(evt);
            } else if (evt.getSource() == viewHTML) {
                App.this.viewHTMLActionPerformed(evt);
            } else if (evt.getSource() == subjAnalyse) {
                App.this.subjAnalyseActionPerformed(evt);
            } else if (evt.getSource() == totalAnalyse) {
                App.this.totalAnalyseActionPerformed(evt);
            } else if (evt.getSource() == savePath) {
                App.this.savePathActionPerformed(evt);
            } else if (evt.getSource() == browseSavePath) {
                App.this.browseSavePathActionPerformed(evt);
            } else if (evt.getSource() == defProvide) {
                App.this.defProvideActionPerformed(evt);
            } else if (evt.getSource() == aboutButton) {
                App.this.aboutButtonActionPerformed(evt);
            } else if (evt.getSource() == stopButton) {
                App.this.stopButtonActionPerformed(evt);
            }
        }

        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            if (evt.getSource() == YearSlectBox) {
                App.this.YearSlectBoxItemStateChanged(evt);
            }
        }

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == batchTabes) {
                App.this.batchTabesPropertyChange(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents
    // End of variables declaration//GEN-END:variables

}
