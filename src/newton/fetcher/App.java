/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import newton.fetcher.ResultAnalyser.MODE;
import newton.resultApi.HtmlUnitClient;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFormattedTextField;

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
    private final HtmlUnitClient fetch;

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

    /**
     *
     * @return
     */
    public HtmlUnitClient getCBSEFetcher() {
        return fetch;
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public int getSelectedYear() {
        return Integer.parseInt(this.YearSlectBox.getSelectedItem().toString());
    }

    /**
     *
     * @return
     */
    public BatchView getNewBatch() {
        batch++;
        BatchView newbatch = new BatchView(this);
        newbatch.setId(batch + " " + getSelectedYear());
        this.batches.add(newbatch);
        this.batchTabes.add(newbatch.getId(), newbatch);
        this.batchTabes.setSelectedIndex(batch - 1);
        return newbatch;

    }

    /**
     *
     * @return
     */
    public JXFormattedTextField getSavePath() {
        return savePath;
    }

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

    /**
     *
     * @return
     */
    public JFormattedTextField getMergePathField() {
        return mergePathField;
    }

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - unknown
    private void initComponents() {
        jTabbedPane1 = new JTabbedPane();
        cbsePanel = new JPanel();
        endField = new JXFormattedTextField();
        startButton = new JXButton();
        YearSlectBox = new JComboBox<>();
        jLabel2 = new JLabel();
        jLabel5 = new JLabel();
        jLabel3 = new JLabel();
        infoLable = new JXBusyLabel();
        startField = new JXFormattedTextField();
        schCodeField = new JXFormattedTextField();
        jLabel6 = new JLabel();
        jLabel9 = new JLabel();
        centerNoField = new JXFormattedTextField();
        toolPanel = new JPanel();
        mergeButton = new JButton();
        viewHTML = new JButton();
        mergeProgressBar = new JProgressBar();
        mergeInfo = new JLabel();
        mergePathField = new JFormattedTextField();
        mergeOutput = new JFormattedTextField();
        subjAnalyse = new JButton();
        totalAnalyse = new JButton();
        settingsPanel = new JPanel();
        savePath = new JXFormattedTextField();
        jLabel4 = new JLabel();
        jLabel1 = new JLabel();
        browseSavePath = new JButton();
        defProvide = new JCheckBox();
        jButton1 = new JButton();
        jButton2 = new JButton();
        batchTabes = new JTabbedPane();

        //======== this ========
        setDefaultCloseOperation(3);
        setTitle("Result Fetcher");
        setName("this");
        Container contentPane = getContentPane();

        //======== jTabbedPane1 ========
        {
            jTabbedPane1.setName("jTabbedPane1");

            //======== cbsePanel ========
            {
                cbsePanel.setName("cbsePanel");

                cbsePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


                //---- endField ----
                endField.setName("endField");
                endField.setPrompt("End");
                endField.setPromptForeground(new Color(153, 153, 153));

                //---- startButton ----
                startButton.setText("Start ! ");
                startButton.setName("startButton");
                startButton.addActionListener(e -> startButtonActionPerformed(e));

                //---- YearSlectBox ----
                YearSlectBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "2018",
                    "2017",
                    "2016",
                    "2015",
                    "2014",
                    "2013",
                    "2012",
                    "2011",
                    "2010",
                    "2009",
                    "2008",
                    "2007",
                    "2006",
                    "2005",
                    "2004"
                }));
                YearSlectBox.setName("YearSlectBox");
                YearSlectBox.addItemListener(e -> YearSlectBoxItemStateChanged(e));
                YearSlectBox.addActionListener(e -> YearSlectBoxActionPerformed(e));

                //---- jLabel2 ----
                jLabel2.setText("Reg Number From ");
                jLabel2.setName("jLabel2");

                //---- jLabel5 ----
                jLabel5.setText("CBSE YEAR ");
                jLabel5.setName("jLabel5");

                //---- jLabel3 ----
                jLabel3.setText("To ");
                jLabel3.setName("jLabel3");

                //---- infoLable ----
                infoLable.setName("infoLable");

                //---- startField ----
                startField.setName("startField");
                startField.setPrompt("Start");
                startField.setPromptForeground(new Color(153, 153, 153));

                //---- schCodeField ----
                schCodeField.setFormatterFactory(null);
                schCodeField.setToolTipText("SchoolCode year 2016  onward");
                schCodeField.setEnabled(false);
                schCodeField.setName("schCodeField");
                schCodeField.setPrompt("School Code");
                schCodeField.setPromptForeground(new Color(153, 153, 153));

                //---- jLabel6 ----
                jLabel6.setText("School No.");
                jLabel6.setName("jLabel6");

                //---- jLabel9 ----
                jLabel9.setText("Center No.");
                jLabel9.setName("jLabel9");

                //---- centerNoField ----
                centerNoField.setFormatterFactory(null);
                centerNoField.setToolTipText("Center number  2017 onwards");
                centerNoField.setEnabled(false);
                centerNoField.setName("centerNoField");
                centerNoField.setPrompt("Center Number");
                centerNoField.setPromptForeground(new Color(153, 153, 153));

                GroupLayout cbsePanelLayout = new GroupLayout(cbsePanel);
                cbsePanel.setLayout(cbsePanelLayout);
                cbsePanelLayout.setHorizontalGroup(
                    cbsePanelLayout.createParallelGroup()
                        .addGroup(cbsePanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(cbsePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(cbsePanelLayout.createSequentialGroup()
                                    .addComponent(infoLable, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)
                                    .addGap(37, 37, 37)
                                    .addComponent(jLabel9)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(centerNoField, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
                                .addGroup(cbsePanelLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(YearSlectBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(startField, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(endField, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(schCodeField, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
                            .addGap(30, 30, 30)
                            .addComponent(startButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                );
                cbsePanelLayout.setVerticalGroup(
                    cbsePanelLayout.createParallelGroup()
                        .addGroup(cbsePanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(cbsePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(startField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(endField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(YearSlectBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(startButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(schCodeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                            .addGroup(cbsePanelLayout.createParallelGroup()
                                .addGroup(cbsePanelLayout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(infoLable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(cbsePanelLayout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(cbsePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(centerNoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9))))
                            .addContainerGap(37, Short.MAX_VALUE))
                );
            }
            jTabbedPane1.addTab("CBSE Results", cbsePanel);

            //======== toolPanel ========
            {
                toolPanel.setName("toolPanel");

                //---- mergeButton ----
                mergeButton.setText("Merge  Results");
                mergeButton.setName("mergeButton");
                mergeButton.addActionListener(e -> mergeButtonActionPerformed(e));

                //---- viewHTML ----
                viewHTML.setText("View HTML Page");
                viewHTML.setName("viewHTML");
                viewHTML.addActionListener(e -> viewHTMLActionPerformed(e));

                //---- mergeProgressBar ----
                mergeProgressBar.setUI(new ProgressCircleUI());
                mergeProgressBar.setBorderPainted(false);
                mergeProgressBar.setName("mergeProgressBar");
                mergeProgressBar.setStringPainted(true);

                //---- mergeInfo ----
                mergeInfo.setName("mergeInfo");

                //---- mergePathField ----
                mergePathField.setEditable(false);
                mergePathField.setName("mergePathField");

                //---- mergeOutput ----
                mergeOutput.setEditable(false);
                mergeOutput.setName("mergeOutput");

                //---- subjAnalyse ----
                subjAnalyse.setText("Subject Wise Result");
                subjAnalyse.setName("subjAnalyse");
                subjAnalyse.addActionListener(e -> subjAnalyseActionPerformed(e));

                //---- totalAnalyse ----
                totalAnalyse.setText("Total Wise Result");
                totalAnalyse.setName("totalAnalyse");
                totalAnalyse.addActionListener(e -> totalAnalyseActionPerformed(e));

                GroupLayout toolPanelLayout = new GroupLayout(toolPanel);
                toolPanel.setLayout(toolPanelLayout);
                toolPanelLayout.setHorizontalGroup(
                    toolPanelLayout.createParallelGroup()
                        .addGroup(toolPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(toolPanelLayout.createParallelGroup()
                                .addGroup(toolPanelLayout.createParallelGroup()
                                    .addGroup(toolPanelLayout.createSequentialGroup()
                                        .addComponent(mergeButton)
                                        .addGap(44, 44, 44))
                                    .addGroup(GroupLayout.Alignment.TRAILING, toolPanelLayout.createSequentialGroup()
                                        .addComponent(subjAnalyse)
                                        .addGap(18, 18, 18)))
                                .addGroup(toolPanelLayout.createSequentialGroup()
                                    .addComponent(totalAnalyse)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                            .addGroup(toolPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(mergePathField, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                .addComponent(mergeOutput, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                .addComponent(mergeInfo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(10, 10, 10)
                            .addComponent(mergeProgressBar, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                            .addGap(51, 51, 51)
                            .addComponent(viewHTML)
                            .addGap(0, 226, Short.MAX_VALUE))
                );
                toolPanelLayout.setVerticalGroup(
                    toolPanelLayout.createParallelGroup()
                        .addGroup(toolPanelLayout.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(toolPanelLayout.createParallelGroup()
                                .addGroup(toolPanelLayout.createSequentialGroup()
                                    .addComponent(mergeButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(subjAnalyse)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(totalAnalyse)
                                    .addGap(22, 22, 22))
                                .addGroup(toolPanelLayout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addGroup(toolPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(toolPanelLayout.createSequentialGroup()
                                            .addGap(3, 3, 3)
                                            .addComponent(mergePathField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(mergeOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(toolPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(mergeProgressBar, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(viewHTML)))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(mergeInfo, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())))
                );
            }
            jTabbedPane1.addTab("Tools", toolPanel);

            //======== settingsPanel ========
            {
                settingsPanel.setName("settingsPanel");

                //---- savePath ----
                savePath.setName("savePath");
                savePath.setPrompt("Path To Folder");
                savePath.setPromptForeground(new Color(153, 153, 153));
                savePath.addActionListener(e -> savePathActionPerformed(e));

                //---- jLabel4 ----
                jLabel4.setText("Save Results to ");
                jLabel4.setName("jLabel4");

                //---- jLabel1 ----
                jLabel1.setText("Saved Results  : ");
                jLabel1.setName("jLabel1");

                //---- browseSavePath ----
                browseSavePath.setText("Browse");
                browseSavePath.setName("browseSavePath");
                browseSavePath.addActionListener(e -> browseSavePathActionPerformed(e));

                //---- defProvide ----
                defProvide.setSelected(true);
                defProvide.setText("Provie default Range for roll numbers etc.");
                defProvide.setName("defProvide");
                defProvide.addActionListener(e -> defProvideActionPerformed(e));

                //---- jButton1 ----
                jButton1.setText("Advanced >>");
                jButton1.setName("jButton1");

                //---- jButton2 ----
                jButton2.setText("Help");
                jButton2.setName("jButton2");
                jButton2.addActionListener(e -> jButton2ActionPerformed(e));

                GroupLayout settingsPanelLayout = new GroupLayout(settingsPanel);
                settingsPanel.setLayout(settingsPanelLayout);
                settingsPanelLayout.setHorizontalGroup(
                    settingsPanelLayout.createParallelGroup()
                        .addGroup(settingsPanelLayout.createSequentialGroup()
                            .addGap(62, 62, 62)
                            .addGroup(settingsPanelLayout.createParallelGroup()
                                .addGroup(settingsPanelLayout.createSequentialGroup()
                                    .addGap(87, 87, 87)
                                    .addComponent(jLabel4))
                                .addComponent(jLabel1))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(savePath, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(browseSavePath))
                        .addGroup(settingsPanelLayout.createSequentialGroup()
                            .addGap(127, 127, 127)
                            .addComponent(defProvide)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, settingsPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jButton2)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1)
                            .addGap(52, 52, 52))
                );
                settingsPanelLayout.setVerticalGroup(
                    settingsPanelLayout.createParallelGroup()
                        .addGroup(settingsPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addGroup(settingsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(savePath, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(browseSavePath))
                                .addGroup(settingsPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(15, 15, 15)))
                            .addGroup(settingsPanelLayout.createParallelGroup()
                                .addGroup(settingsPanelLayout.createSequentialGroup()
                                    .addComponent(defProvide)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                    .addComponent(jButton1)
                                    .addContainerGap())
                                .addComponent(jButton2, GroupLayout.Alignment.TRAILING)))
                );
            }
            jTabbedPane1.addTab("Settings", settingsPanel);
        }

        //======== batchTabes ========
        {
            batchTabes.setName("batchTabes");
            batchTabes.setOpaque(true);
            batchTabes.addPropertyChangeListener(e -> batchTabesPropertyChange(e));
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(jTabbedPane1)
                .addComponent(batchTabes)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(batchTabes, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents

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

        if (re == null || !re.isRunning()) {
            re = new CBSEResultLoader(this);
            if (System.getProperty("ide.run") == null) {
                re = new CBSEResultLoader(this);
            }
            infoLable.setBusy(true);
            infoLable.setText("Connecting....");
            this.startButton.setText("Stop !");

            re.execute();
        } else {
            re.setRunning(false);
            this.startButton.setText("Start ");
            this.infoLable.setText("");
        }
    }//GEN-LAST:event_startButtonActionPerformed

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     * @throws javax.swing.UnsupportedLookAndFeelException
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {

        java.awt.EventQueue.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }

            new App().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JTabbedPane jTabbedPane1;
    private JPanel cbsePanel;
    private JXFormattedTextField endField;
    private JXButton startButton;
    private JComboBox<String> YearSlectBox;
    private JLabel jLabel2;
    private JLabel jLabel5;
    private JLabel jLabel3;
    private JXBusyLabel infoLable;
    private JXFormattedTextField startField;
    private JXFormattedTextField schCodeField;
    private JLabel jLabel6;
    private JLabel jLabel9;
    private JXFormattedTextField centerNoField;
    private JPanel toolPanel;
    private JButton mergeButton;
    private JButton viewHTML;
    private JProgressBar mergeProgressBar;
    private JLabel mergeInfo;
    private JFormattedTextField mergePathField;
    private JFormattedTextField mergeOutput;
    private JButton subjAnalyse;
    private JButton totalAnalyse;
    private JPanel settingsPanel;
    private JXFormattedTextField savePath;
    private JLabel jLabel4;
    private JLabel jLabel1;
    private JButton browseSavePath;
    private JCheckBox defProvide;
    private JButton jButton1;
    private JButton jButton2;
    private JTabbedPane batchTabes;
    // End of variables declaration//GEN-END:variables

}
