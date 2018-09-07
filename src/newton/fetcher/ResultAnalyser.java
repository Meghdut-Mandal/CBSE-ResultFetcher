/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import newton.resultApi.CBSEResult;
import newton.resultApi.HtmlUnitClient;
import newton.resultApi.ProcessObserver;

/**
 * arranging
 *
 * @author Meghdut Mandal
 */
public class ResultAnalyser extends SwingWorker<Void, Object> implements ProcessObserver {

    private final App app;
    private File analysisFile;
    private final File resDir;
    private final MODE mode;

    /**
     *
     */
    public static enum MODE {

        /**
         *
         */
        RESULT_MERGE, 

        /**
         *
         */
        TOTALWISELIST, 

        /**
         *
         */
        SUBJECTWISELIST
    }

    /**
     *
     * @param app
     * @param resdir
     * @param mode
     */
    public ResultAnalyser(App app, File resdir, MODE mode) {
        this.app = app;

        if (mode == MODE.RESULT_MERGE) {
            this.analysisFile = new File(resdir.getAbsolutePath() + "\\merge.html");
        } else if (mode == MODE.SUBJECTWISELIST) {
            this.analysisFile = new File(resdir.getAbsolutePath() + "\\subjectWiseResults.txt");
        } else if (mode == MODE.TOTALWISELIST) {
            this.analysisFile = new File(resdir.getAbsolutePath() + "\\topperWiseResults.txt");
        }
        analysisFile.delete();
        this.resDir = resdir;
        this.mode = mode;
        app.setAnalyseButtonsEnabled(false);

    }

    @Override
    protected void done() {

        app.setAnalyseButtonsEnabled(true);

        app.setProgress(0);
        app.setAnalysisInfo("");

        if (mode == MODE.RESULT_MERGE) {

            app.getHTMLViewer().loadHtml(analysisFile.getAbsolutePath());
        } else if (mode == MODE.SUBJECTWISELIST || mode == MODE.TOTALWISELIST) {
            try {
                app.getHTMLViewer().loadText(analysisFile.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(ResultAnalyser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        app.getHTMLViewer().setVisible(true);
    }

    @Override
    protected Void doInBackground() throws IOException {

        List<File> fList = Arrays.asList(resDir.listFiles());

        if (mode == MODE.RESULT_MERGE) {

            mergeResults(fList, analysisFile);

        } else if (mode == MODE.SUBJECTWISELIST) {
            this.subjectWise(fList, analysisFile);

        } else if (mode == MODE.TOTALWISELIST) {
            this.totalWise(fList, analysisFile);

        }
        return null;
    }

    /**
     *
     * @param htmls
     * @param output
     * @throws IOException
     */
    @SuppressWarnings(value = "UnusedAssignment")
    public void mergeResults(List<File> htmls, File output) throws IOException {
        output.createNewFile();
        if (!output.exists()) {
            throw new java.io.FileNotFoundException("Doesnt exist ! ");
        }
        System.gc();
        try (java.io.StringWriter wr = new java.io.StringWriter()) {
            for (int i = 0; i < htmls.size(); i++) {
                File html = htmls.get(i);
                double progress = htmls.size();
                progress = (i / progress) * 100.00;
                this.publish((int) progress);
                if (html.getName().contains(output.getName())) {
                    continue;
                }
                if (html.exists()) {
                    this.publishString("Working on " + html.getName());
                    HtmlPage page = null;

                    page = app.getCBSEFetcher().getWebClient().getPage(html.toURI().toURL());

                    if (page == null) {
                        continue;
                    }
                    DomNodeList<DomElement> elements = page.getElementsByTagName("table");
                    elements.stream().filter(docElem -> docElem.asText().contains("Roll No") || docElem.asText().contains("SUB CODE"))
                            .forEach(docElemF -> wr.write(docElemF.asXml()
                                            .replace("Â Â Â Â", "").replace("Â", "").replace("mediumblue", "blue")
                                    ));
                }
            }

            wr.flush();
            Files.write(output.toPath(), wr.getBuffer().toString().getBytes(), CREATE);
        }
        System.gc();

    }

    /**
     *
     * @param folder
     * @param output
     * @throws IOException
     */
    public void totalWise(List<File> folder, File output) throws IOException {

        String totalWise = HtmlUnitClient.totalWise(folder, this);
        Files.write(output.toPath(), totalWise.getBytes(), CREATE);

    }

    /**
     *
     * @param folder
     * @param output
     * @throws IOException
     */
    public void subjectWise(List<File> folder, File output) throws IOException {
        String subjwise = HtmlUnitClient.subjectWise(folder, this);
        Files.write(output.toPath(), subjwise.getBytes(), CREATE);
    }

    @Override
    protected void process(List<Object> chunks) {
        chunks.stream().forEach(chunk -> {
            if (chunk instanceof Integer) {
                int last = (Integer) chunk;
                app.setProgress(last);
            } else {
                app.setAnalysisInfo(chunk.toString());
            }
        });
    }

    /**
     *
     * @param text
     */
    @Override
    public void publishString(String text) {
        this.publish(text);
        //  System.out.println(text);
    }

    /**
     *
     * @param d
     */
    @Override
    public void setProgress(double d) {
        this.publish((int) d);

    }

    /**
     *
     * @param res
     */
    @Override
    public void finished(CBSEResult res) {
        //  System.out.println(res);
    }

}
