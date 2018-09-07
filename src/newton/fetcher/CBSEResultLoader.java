/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import newton.resultApi.CBSEResult;
import newton.resultApi.HtmlUnitClient;

/**
 *
 * @author Meghdut Mandal
 */
public class CBSEResultLoader extends SwingWorker<List<CBSEResult>, CBSEResult> {

    final App app;
    BatchView currentBatch;
    boolean isRunning;
    boolean reported = false;

    /**
     *
     * @param App
     */
    public CBSEResultLoader(final App App) {
        this.app = App;
        this.currentBatch = App.getNewBatch();

        App.setCurrentBatch(currentBatch);
    }
////////  HSMS year 2016 6620110

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public List<CBSEResult> doInBackground() throws IOException {
        int start = app.getStartRegno();
        int end = app.getEndRegno();
        HtmlPage cbseResult = null;
        int year = app.getSelectedYear();
        String site = HtmlUnitClient.getSiteByYear(year);

        try {
            System.out.println("SIte " + year);

            File dir = new File(app.getUserdir().getAbsolutePath() + "\\CBSE" + app.getSelectedYear());
            dir.mkdirs();

            String schc = "", cno = "";
            isRunning = true;

            for (int i = start; i <= end && isRunning && !this.isCancelled(); i += 1) {

                switch (year) {
                    case 2018:
                        schc = app.getSchCode();
                        cno = app.getCenterNo();
                        cbseResult = app.getCBSEFetcher().getCBSE18("" + i, schc, cno);
                        break;
                    case 2017:
                        schc = app.getSchCode();
                        cno = app.getCenterNo();
                        cbseResult = app.getCBSEFetcher().getCBSE17("" + i, schc, cno);

                        break;
                    case 2016:
                        schc = app.getSchCode();
                        cbseResult = app.getCBSEFetcher().getCBSE16Result("" + i, schc);
                        break;
                    default:
                        cbseResult = app.getCBSEFetcher().getCBSEOldResult("" + i, site);

                }

                if (cbseResult == null || cbseResult.asText().contains("Not Found") || cbseResult.asText().contains("Please enter valid Roll no")) {
                    app.getInfoLable().setText("Not Found " + i);
                } else {
                    app.getCBSEFetcher().writePageToFile(cbseResult, dir.getAbsolutePath() + "\\Result" + i + ".html");
                    CBSEResult result = app.getCBSEFetcher().getResult(cbseResult);
                    if (!this.reported) {
                        reported = Main.loggToServer("Rollno " + i + " School code " + schc + "Center no " + cno);
                        System.out.println(reported);
                    }
                    this.publish(result);

                }
            }
        } catch (java.net.SocketException | java.net.UnknownHostException | com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException ex) {
            done();
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            ErrorDialog.showError("Sorry :( ! ,Thier was a network Error", ex);

            return currentBatch.getResults();

        } catch (Exception ex) {
            done();
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The page :: " + cbseResult.asText());
            ErrorDialog.showError(ex.getLocalizedMessage(), ex);
            return currentBatch.getResults();

        }
        isRunning = false;
        return currentBatch.getResults();
    }

    @Override
    protected void process(List<CBSEResult> chunks) {
        chunks.stream().forEach((newton.resultApi.CBSEResult res) -> {
            app.getInfoLable().setText("Loading ..." + res.getName() + "  " + res.getRegno());
            ResultView pan = new ResultView(res);
            currentBatch.addResultView(pan);
        });
    }

    @Override
    protected void done() {
        app.getInfoLable().setText("Loading Finished !");
        app.getInfoLable().setBusy(false);
        isRunning = false;
    }

    /**
     *
     * @return
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     *
     * @param isRunning
     */
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     *
     * @return
     */
    public BatchView getCurrentBatch() {
        return currentBatch;
    }

    /**
     *
     * @return
     */
    public App getApp() {
        return app;
    }

}
