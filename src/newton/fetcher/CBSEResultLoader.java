/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import newton.resultApi.CBSEResult;
import newton.resultApi.HtmlUnitClient;
import newton.resultApi.ResultClient;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Meghdut Mandal
 */
class CBSEResultLoader extends SwingWorker<List<CBSEResult>, CBSEResult> {

    final App app;
    BatchView currentBatch;
    private int start;
    private int end;
    private int year;

    private String site;
    private String schCode = "", cntrNo = "";
    private File dir;

    private boolean reportedToServer = false;
////////  HSMS year 2016 6620110

    /**
     * @param App
     */
    public CBSEResultLoader(final App App) {
        this.app = App;
        this.currentBatch = App.getNewBatch();

        App.setCurrentBatch(currentBatch);

        start = app.getStartRegno();

        end = app.getEndRegno();
        year = app.getSelectedYear();

        site = HtmlUnitClient.getSiteByYear(year);

        dir = new File(app.getUserdir().getAbsolutePath() + "\\CBSE" + app.getSelectedYear());
        dir.mkdirs();
    }

    private String getCbseResult(String roll) throws IOException {
        String pageHtml = "";
        switch (year) {
            case 2018:
                schCode = app.getSchCode();
                cntrNo = app.getCenterNo();
                pageHtml = app.getCBSEFetcher().getCBSE18(roll, schCode, cntrNo);
                break;
            case 2017:
                schCode = app.getSchCode();
                cntrNo = app.getCenterNo();
                pageHtml = app.getCBSEFetcher().getCBSE17(roll, schCode, cntrNo);
                break;
            case 2016:
                schCode = app.getSchCode();
                pageHtml = app.getCBSEFetcher().getCBSE16Result(roll, schCode);
                break;
            default:
                pageHtml = app.getCBSEFetcher().getCBSEOldResult(roll, site);

        }
        return pageHtml;
    }

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public List<CBSEResult> doInBackground() {
        String pageHtml = null;
        try {

            for (int roll = start; roll <= end && !this.isCancelled(); roll += 1) {

                pageHtml = getCbseResult(roll + "");
                if (hasResult(pageHtml)) {
                    app.getInfoLable().setText("Not Found " + roll);

                } else {
                    app.getCBSEFetcher().writePageToFile(pageHtml, dir.getAbsolutePath() + "\\Result" + roll + ".html");
                    CBSEResult result = ResultClient.getResult(pageHtml);
                    if (!this.reportedToServer) {
                        reportedToServer = Main.loggToServer("Rollno " + roll + " School code " + schCode + "Center no " + cntrNo);
                    }
                    this.publish(result);

                }
            }
        } catch (java.net.SocketException | java.net.UnknownHostException | com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException ex) {

            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            ErrorDialog.showError("Sorry :( ! ,Thier was a network Error", ex);

        } catch (Exception ex) {

            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The page :: " + pageHtml);
            ErrorDialog.showError(ex.getLocalizedMessage(), ex);
        }

        return currentBatch.getResults();
    }

    @Override
    protected void process(List<CBSEResult> chunks) {
        chunks.forEach((newton.resultApi.CBSEResult res) -> {
            app.getInfoLable().setText("Loading ..." + res.getName() + "  " + res.getRegno());
            ResultView pan = new ResultView(res);
            currentBatch.addResultView(pan);
        });
    }

    @Override
    protected void done() {
        app.getInfoLable().setText("Loading Finished !");
        app.getInfoLable().setBusy(false);

    }

    /**
     * @return
     */
    public BatchView getCurrentBatch() {
        return currentBatch;
    }

    /**
     * @return
     */
    public App getApp() {
        return app;
    }

    boolean hasResult(String cbseResult) {
        return cbseResult != null && !cbseResult.contains("Not Found") && !cbseResult.contains("Please enter valid Roll no");
    }

}
