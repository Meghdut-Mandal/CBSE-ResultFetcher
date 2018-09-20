/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.resultApi;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Meghdut Mandal
 */
public class HtmlUnitClient extends ResultClient {

    /**
     *
     * @return
     */
    public WebClient getWebClient() {
        return webClient;
    }

    /**
     *
     */


    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        HtmlUnitClient hb = new HtmlUnitClient();
        String page = hb.getCBSEOldResult("6622710", getSiteByYear(2011));

        System.out.println("out " + hb.getResult(page));
        //   System.out.println(page);
    }

    private static void frequency() {
        String arr[] = {
            "E:\\CBSE\\dav18", "E:\\CBSE\\davmtps",
            "E:\\CBSE\\DAVPandevshar",
            "E:\\CBSE\\hsms18",
            // "‪E:\\CBSE\\naraysn\\" ,
            "E:\\CBSE\\tegbahadur18"
        };
        List<CBSEResult> rl = new java.util.ArrayList<>();
        System.out.println(new File("‪E:\\CBSE\\naraynakhasi\\").exists()
        );
        HtmlUnitClient gClient = new HtmlUnitClient();
        Arrays.stream(arr).filter(Objects::nonNull).forEach(fname -> {
            rl.addAll(gClient.getResultList(Arrays.asList(Objects.requireNonNull(new File(fname).listFiles())), new ProcessObserver() {

                @Override
                public void publishString(String text) {
                    System.out.println(text);

                }

                @Override
                public void setProgress(double d) {
                    System.out.println("Progress " + Math.ceil(d));
                }

                @Override
                public void finished(CBSEResult res) {
                }
            }));
            System.out.println("Adding " + fname);
        });
        // java.util.HashMap<String, List<Subject>> tabel = new java.util.HashMap<>();
        List<Subject> sublist = new java.util.ArrayList<>();
        rl.stream().map(CBSEResult::getMainSubjects).forEach(sublist::addAll);
        Map<Integer, List<Subject>> table = sublist.stream().collect(Collectors.groupingBy(Subject::getMarks));

        System.out.println("Data\n\n\n\n");
        table.keySet().stream().sorted().map(table::get).forEach((subs) -> {
            long count = (long) subs.size();
            System.out.println(subs.get(0).getMarks() + ";" + count);
        });
    }

    private HtmlPage getWBHS(String roll, String no, String regisno) throws IOException {
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = webClient.getPage("http://wbresults.nic.in/highersecondary18/wbhsres.htm");

        final HtmlForm form = page.getFormByName("FrontPage_Form1");

        final HtmlTextInput rollField = form.getInputByName("roll");
        rollField.type(roll);

        final HtmlTextInput noField = form.getInputByName("rno");
        noField.type(no);

        final HtmlTextInput reg = form.getInputByName("reg_pre");
        reg.type(regisno);

        final HtmlSubmitInput button = form.getInputByName("B1");
        webClient.waitForBackgroundJavaScript(1000);
        webClient.getOptions().setJavaScriptEnabled(false);

        final HtmlPage page2 = button.click();
        webClient.getOptions().setJavaScriptEnabled(true);
        return page2;
    }

    /**
     * Fetches a HtmlPage form the
     *
     * @param roll
     * @param schCode
     * @param centerno
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public String getCBSE17(String roll, String schCode, String centerno) throws MalformedURLException, IOException {
        //   webClient.getOptions().setJavaScriptEnabled(true);
        String site = "http://resultsarchives.nic.in/cbseresults/cbseresults2017/class12npy/class12th17reval.asp";
        URL url = new URL(site);
        WebRequest requestSettings = new WebRequest(url, HttpMethod.POST);

        requestSettings.setAdditionalHeader("Pragma", " no-cache");//d
        requestSettings.setAdditionalHeader("Origin", "http://resultsarchives.nic.in");//d
        requestSettings.setAdditionalHeader("Accept-Encoding", "gzip, deflate");//d
        requestSettings.setAdditionalHeader("Host", "resultsarchives.nic.in");//d
        requestSettings.setAdditionalHeader("Accept-Language", "en-US,en;q=0.9");//
        requestSettings.setAdditionalHeader("Upgrade-Insecure-Requests", "1");//
        requestSettings.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");//d
        requestSettings.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded");//d
        requestSettings.setAdditionalHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");//d
        requestSettings.setAdditionalHeader("Cache-Control", "no-cache");//d
        requestSettings.setAdditionalHeader("Referer", site);//d
        requestSettings.setAdditionalHeader("Connection", "keep-alive");//
        requestSettings.setAdditionalHeader("DNT", "1");//d
        requestSettings.setRequestBody("regno=" + roll + "&sch=" + schCode + "&cno=" + centerno + "&B2=Submit");

        HtmlPage page = webClient.getPage(requestSettings);
        return page.asXml();
    }

    /**
     *
     * @param roll
     * @param schCode
     * @param centerno
     * @return
     * @throws IOException
     */
    public String getCBSE18(String roll, String schCode, String centerno) throws IOException {
        //   webClient.getOptions().setJavaScriptEnabled(true);

        URL url = new URL(Objects.requireNonNull(getSiteByYear(2018)));
        WebRequest requestSettings = new WebRequest(url, HttpMethod.POST);

        requestSettings.setAdditionalHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");//d
        requestSettings.setAdditionalHeader("Accept-Encoding", "gzip, deflate");//d
        requestSettings.setAdditionalHeader("Accept-Language", "en-US,en;q=0.9");//d
        requestSettings.setAdditionalHeader("Connection", "keep-alive");//
        requestSettings.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded");//d
        requestSettings.setAdditionalHeader("DNT", "1");//d
        requestSettings.setAdditionalHeader("Host", "cbseresults.nic.in");//d
        requestSettings.setAdditionalHeader("Origin", "http://cbseresults.nic.in");
        requestSettings.setAdditionalHeader("Referer", "http://cbseresults.nic.in/class12zpq/class12th18.asp");//d
        requestSettings.setAdditionalHeader("Upgrade-Insecure-Requests", "1");//d
        requestSettings.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");//d

        requestSettings.setRequestBody("regno=" + roll + "&sch=" + schCode + "&cno=" + centerno + "&B2=Submit");


        HtmlPage page = webClient.getPage(requestSettings);
        return page.asXml();
    }


    private final WebClient webClient;

    /**
     *
     */
    public HtmlUnitClient() {
        this.webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);

    }

    /**
     *
     * @param regno
     * @param schCode
     * @return
     * @throws IOException
     */
    public String getCBSE16Result(String regno, String schCode) throws IOException {
        HtmlPage page = webClient.getPage(Objects.requireNonNull(getSiteByYear(2016)));
        // System.out.println("Page done");
        final HtmlForm form = page.getFormByName("FrontPage_Form1");

        final HtmlTextInput regField = form.getInputByName("regno");
        regField.type(regno);

        final HtmlTextInput schField = form.getInputByName("schcode");
        schField.type(schCode);

        final HtmlSubmitInput button = form.getInputByName("B1");

        final HtmlPage page2 = button.click();

        return page2.asXml();
    }










    /**
     *
     * @param regno
     * @param site
     * @return
     * @throws IOException
     */
    public String getCBSEOldResult(String regno, String site) throws IOException {

        URL url = new URL(site.replace("htm", "asp"));
        WebRequest requestSettings = new WebRequest(url, HttpMethod.POST);

        requestSettings.setAdditionalHeader("Pragma", " no-cache");//d
        requestSettings.setAdditionalHeader("Origin", "http://resultsarchives.nic.in");//d
        requestSettings.setAdditionalHeader("Accept-Encoding", "gzip, deflate");//d
        requestSettings.setAdditionalHeader("Host", "resultsarchives.nic.in");//d
        requestSettings.setAdditionalHeader("Accept-Language", "en-US,en;q=0.9");//
        requestSettings.setAdditionalHeader("Upgrade-Insecure-Requests", "1");//
        requestSettings.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");//d
        requestSettings.setAdditionalHeader("Content-Type", "application/x-www-form-urlencoded");//d
        requestSettings.setAdditionalHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");//d
        requestSettings.setAdditionalHeader("Cache-Control", "no-cache");//d
        requestSettings.setAdditionalHeader("Referer", site);//d
        requestSettings.setAdditionalHeader("Connection", "keep-alive");//
        requestSettings.setAdditionalHeader("DNT", "1");//d

        requestSettings.setRequestBody("regno=" + regno + "&B1=Submit");

        HtmlPage page = webClient.getPage(requestSettings);
        return page.asXml();


    }

    /**
     *
     * @param htmls
     * @param output
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void mergeResults(List<File> htmls, File output) throws FileNotFoundException, IOException {
        output.createNewFile();
        if (!output.exists()) {
            throw new java.io.FileNotFoundException("Doesnt exist ! ");
        }
        try (java.io.FileWriter wr = new java.io.FileWriter(output)) {
            for (File html : htmls) {
                if (html.exists()) {

                    HtmlPage page = null;
                    try {
                        page = webClient.getPage(html.toURI().toURL());
                    } catch (com.gargoylesoftware.htmlunit.ScriptException iOException) {
                    } catch (FailingHttpStatusCodeException failingHttpStatusCodeException) {
                    }
                    if (page == null) {
                        continue;
                    }
                    DomNodeList<DomElement> elements = page.getElementsByTagName("table");
                    elements.forEach((fg) -> {
                        if (fg.asText().contains("Roll No") || fg.asText().contains("SUB CODE")) {
                            try {
                                wr.write(fg.asXml()
                                        .replace("Â Â Â Â", "").replace("Â", "")//.replace("mediumblue", "blue")
                                );
                            } catch (IOException ex) {
                                Logger.getLogger(HtmlUnitClient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
            }

            wr.flush();
        }
    }

}
