/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.resultApi;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import newton.fetcher.Var;

/**
 *
 * @author Meghdut Mandal
 */
public class HtmlUnitClient {

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
    public static final String sites[] = {
        "http://cbseresults.nic.in/class12zpq/class12th18.asp",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2017/class12npy/class12th17reval.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2016/class12/cbse1216revised.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2015/class12/cbse122015_all_rev.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2014/class12/cbse122014_total.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2013/class12/cbse122013.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2012/class1211/cbse122012.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2011/class1211/cbse1211.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2010/class12/cbse12.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2009/class12/cbse12.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2008/class12/cbse12.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2007/class12/cbse12.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2006/class12/cbse12.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2005/class12/cbse12.htm",
        "http://resultsarchives.nic.in/cbseresults/cbseresults2004/class12/cbse12.htm"};

    /**
     *
     * @param year
     * @return
     */
    public static String getSiteByYear(int year) {

        if (year <= 2018 && year >= 2004) {
            year -= 2018;
            year *= -1;
            return sites[year];
        }

        return null;

    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        File sb = new File("Results\\2011\\26\\262\\2620002.html");
        HtmlUnitClient hb = new HtmlUnitClient();
        HtmlPage page = hb.webClient.getPage(sb.toURI().toURL());

        System.out.println("out " + hb.getTabel(page));

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
        Arrays.asList(arr).stream().filter(e -> e != null).forEach(fname -> {
            rl.addAll(HtmlUnitClient.getResultList(Arrays.asList(new File(fname).listFiles()), new ProcessObserver() {

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
        rl.stream().map(res -> res.getMainSubjects()).forEach(sublist::addAll);
        Map<Integer, List<Subject>> table = sublist.stream().collect(Collectors.groupingBy(Subject::getMarks));

        System.out.println("Data\n\n\n\n");
        table.keySet().stream().sorted().map(table::get).forEach((subs) -> {
            long count = subs.stream().count();
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
    public HtmlPage getCBSE17(String roll, String schCode, String centerno) throws MalformedURLException, IOException {
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

        return webClient.getPage(requestSettings);
    }

    /**
     *
     * @param roll
     * @param schCode
     * @param centerno
     * @return
     * @throws IOException
     */
    public HtmlPage getCBSE18(String roll, String schCode, String centerno) throws IOException {
        //   webClient.getOptions().setJavaScriptEnabled(true);

        URL url = new URL(getSiteByYear(2018));
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

        return webClient.getPage(requestSettings);
    }

    /**
     * The personal Information of the candidate
     *
     * @param all
     * @return
     */
    public static List<List<String>> getPerInfoTable(List<List<String>> all) {
        List<List<String>> perInfo
                = all.stream().filter((list) -> {
                    return list.size() == 2;
                }).collect(Collectors.toList());

        return perInfo;
    }

    /**
     *
     * @param all
     * @return
     */
    public static List<List<String>> getSubInfoTable(List<List<String>> all) {
        List<List<String>> subInfo
                = all.stream().filter((list) -> {
                    return list.size() != 2;
                }).collect(Collectors.toList());

        return subInfo;
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
    public HtmlPage getCBSE16Result(String regno, String schCode) throws IOException {
        HtmlPage page = webClient.getPage(getSiteByYear(2016));
        System.out.println("Page done");
        final HtmlForm form = page.getFormByName("FrontPage_Form1");

        final HtmlTextInput regField = form.getInputByName("regno");
        regField.type(regno);

        final HtmlTextInput schField = form.getInputByName("schcode");
        schField.type(schCode);

        final HtmlSubmitInput button = form.getInputByName("B1");

        final HtmlPage page2 = button.click();

        return page2;
    }

    /**
     *
     * @param asList
     * @param ob
     * @return
     */
    public static List<CBSEResult> getResultList(List<File> asList, ProcessObserver ob) {

        if (ob == null) {
            ob = ProcessObserver.NULL_OBSERVER;
        }
        HtmlUnitClient f = new HtmlUnitClient();
        f.getWebClient().getOptions().setJavaScriptEnabled(false);

        double total = asList.size();

        AtomicInteger progress = new AtomicInteger();

        Var<ProcessObserver> ober = new Var<>();
        ober.obj = ob;
        List<CBSEResult> resList = asList.stream().map(
                res -> {

                    ober.obj.setProgress(progress.incrementAndGet() / total * 100.0);

                    CBSEResult result = f.getResultNoEx(res);
                    ober.obj.publishString("Pharsed " + result.getName());
                    return result;
                }
        ).filter(obj -> obj != null).collect(Collectors.toList());
        f.getWebClient().getOptions().setJavaScriptEnabled(true);

        return resList;
    }

    /**
     *
     * @param fi
     * @param ob
     * @return
     */
    public static String totalWise(List<File> fi, ProcessObserver ob) {
        return CBSEResult.totalWise(getResultList(fi, ob));
    }

    /**
     *
     * @param fi
     * @param ob
     * @return
     */
    public static String subjectWise(List<File> fi, ProcessObserver ob) {
        return CBSEResult.subjectWise(getResultList(fi, ob));
    }

    private static List<String> processHTMLRow(HtmlElement htmlrow) {
        List<String> cellList = new java.util.ArrayList<>();
        DomNodeList<HtmlElement> rowElements = htmlrow.getElementsByTagName("td");
        rowElements.stream().map(HtmlElement::asText).forEach(celtxt -> {
            cellList.add(celtxt.replace("Â Â Â Â ", "").replace(" Â Â Â Â ", "").replace("Â", ""));
        });
        return cellList;
    }

    /**
     *
     * @param html
     * @return
     */
    public CBSEResult getResultNoEx(File html) {
        Page page;
        try {

            page = webClient.getPage(html.toURI().toURL());
            if (page instanceof com.gargoylesoftware.htmlunit.html.HtmlPage) {
                return getResult((com.gargoylesoftware.htmlunit.html.HtmlPage) page);
            } else {
                return null;
            }
        } catch (IOException | FailingHttpStatusCodeException | java.lang.ClassCastException ex) {
            Logger.getLogger(HtmlUnitClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     *
     * @param html
     * @return
     * @throws IOException
     */
    public CBSEResult getResult(File html) throws IOException {
        HtmlPage page = webClient.getPage(html.toURI().toURL());
        return getResult(page);
    }

    /**
     *
     * @param page
     * @return
     * @throws IOException
     */
    public CBSEResult getResult(HtmlPage page) throws IOException {
        List<List<String>> tabel = this.getTabel(page);
        return new CBSEResult(getPerInfoTable(tabel), getSubInfoTable(tabel));

    }

    private List<List<String>> getTabel(HtmlPage page) throws IOException {
        // WebClient webClient = new WebClient();

        DomNodeList<DomElement> tabelElements = page.getElementsByTagName("table");
        List<List<String>> tabel = tabelElements.stream().
                filter(tableElement -> (tableElement.asText().contains("Roll No")
                        || tableElement.asText().contains("SUB CODE")))
                .flatMap(tableElement -> tableElement.getElementsByTagName("tr").stream())
                .map(HtmlUnitClient::processHTMLRow).collect(Collectors.toList());
        // System.out.println("@tabel");
        //   tabel.stream().forEach(System.out::println);
        return tabel;
    }

    /**
     *
     * @param regno
     * @param schcode
     * @param file
     * @return
     * @throws IOException
     */
    public File write16ResultToFile(String regno, String schcode, String file) throws IOException {

        HtmlPage s = getCBSE16Result(regno, schcode);
        return writePageToFile(s, file);
    }

    /**
     *
     * @param regno
     * @param site
     * @param file
     * @return
     * @throws IOException
     */
    public File write15ResultToFile(String regno, String site, String file) throws IOException {
        HtmlPage s = getCBSEOldResult(regno, site);
        return writePageToFile(s, file);
    }

    /**
     *
     * @param page
     * @param file
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public File writePageToFile(HtmlPage page, String file) throws UnsupportedEncodingException, IOException {
        File f = new File(file);
        f.delete();
        f.createNewFile();
        Files.write(f.toPath(), page.asXml().getBytes("windows-1252"), StandardOpenOption.APPEND);
        return f;

    }

    /**
     *
     * @param regno
     * @param site
     * @return
     * @throws IOException
     */
    public HtmlPage getCBSEOldResult(String regno, String site) throws IOException {

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

        return webClient.getPage(requestSettings);

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
                    elements.stream().forEach((fg) -> {
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
