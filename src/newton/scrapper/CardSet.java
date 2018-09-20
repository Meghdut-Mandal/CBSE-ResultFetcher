/*
 * Copyright 2018 Nitin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package newton.scrapper;


import newton.resultApi.HtmlUnitClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Nitin
 */
public class CardSet {

    private final static int MAX_THREADS = 12;
    private static java.util.concurrent.ForkJoinPool threadPool;
    private static final File parent = new File("D:\\CBSEPRO\\ResultFetcher\\CBSE17\\");

    /**
     *
     * @param ac
     */
    private CardSet(AdmitCard ac) {

        aff = ac.getAffNo();
        schCode = ac.getSchoolCode();
        if (schCode.length() == 4) {
            schCode = "0" + schCode;
        }
        roll = Integer.parseInt(ac.getRollno());
        cntrCode = ac.getCenterCode();

    }

    private File affFolder;
    private int roll;
    private static AtomicInteger c = new AtomicInteger(0);

    private String schCode, cntrCode, aff;

    /**
     *
     * @param ac
     * @return
     */
    private static CardSet toCardSet(AdmitCard ac) {
        return new CardSet(ac);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "6");
        File list = new File("D:\\CBSEPRO\\ResultFetcher\\ResultScraper\\ac");
        File[] listFiles = list.listFiles();

        threadPool = new java.util.concurrent.ForkJoinPool(MAX_THREADS);

        for (File file : requireNonNull(listFiles)) {

            Stream<CardSet> parallel = Arrays.stream(Objects.requireNonNull(file.listFiles())).map(RollRange::getAdmitcard).map(CardSet::toCardSet).parallel(); //.forEach(CardSet::start);
            threadPool.submit(() -> parallel.forEach(CardSet::process));
        }

        threadPool.shutdown();

    }

    private boolean probe(String roll) throws IOException {
        HtmlUnitClient f = new HtmlUnitClient();

        File output = new File(affFolder.getAbsoluteFile(), roll + ".html");
        if (output.exists()) {
            return true;
        }
        String page = null;
        try {
            page = f.getCBSE17(roll, schCode, cntrCode);

            if (!(page == null || page.contains("Not Found")
                    || page.contains("Please enter valid Roll no"))) {

                FileWriter wr = new FileWriter(output);
                String str;
                Document doc = Jsoup.parse(page);
                Elements elements = doc.getElementsByTag("table");
                elements.forEach((fg) -> {
                    if (fg.text().contains("Roll No") || fg.text().contains("SUB CODE")) {
                        try {
                            wr.write(fg.toString()
                                             .replace("Ã‚Â Ã‚Â Ã‚Â Ã‚", "").replace("Ã‚", "")//.replace("mediumblue", "blue")
                            );
                        } catch (IOException ex) {
                            Logger.getLogger(HtmlUnitClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                wr.flush();
                wr.close();
//                System.out.println("Wrote file "+output.getAbsolutePath());
                return true;
            }

        } catch (Exception ex) {
            String sd = aff + ";" + schCode + ";" + cntrCode + ";" + roll + String.format("%n");
            File excpt = new File("exceptions.txt");
            Files.write(excpt.toPath(), sd.getBytes(), APPEND);
            Logger.getLogger(CardSet.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    /**
     *
     * @return
     */
    private synchronized String process() {

        int fail = 0;
        c.getAndIncrement();
        File test = new File("CBSE17/D" + aff);
        if (test.exists()) {
            c.getAndDecrement();
            return "Alredy Done " + aff;

        }

        affFolder = new File(parent, aff);
        if (!affFolder.exists()) {
            affFolder.mkdirs();
        }
        int end = roll;
        for (end = roll; fail < 6; end++) {
            try {
                if (!probe(end + "")) {
                    fail++;
                    System.out.println("Fail " + aff + " Roll:" + end);
                } else {
                    System.out.println("Success " + aff + " Roll:" + end);
                }
            } catch (Exception ex) {
                Logger.getLogger(CardSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fail = 0;
        int start;
        for (start = roll - 1; fail < 6; start--)
            try {

                if (!probe(start + "")) {
                    fail++;
                } else System.out.println("Success " + aff + " Roll:" + start);
            } catch (Exception ex) {
                Logger.getLogger(CardSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        affFolder.renameTo(new File("CBSE17/D" + aff));
        c.getAndDecrement();
        String fh = "Aff :" + aff + " " + start + " to " + end;
        System.out.println(fh);
        return fh;
    }
}
