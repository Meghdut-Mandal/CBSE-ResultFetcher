/*
 * Copyright 2018 MICROSOFT.
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
import newton.resultApi.OkHttpClient;
import newton.resultApi.ResultClient;
import newton.test.comparabler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.nio.file.StandardOpenOption.APPEND;

/**
 *
 * @author MICROSOFT
 */
class ResultsOld {

    private static ResultClient fetc = new OkHttpClient();
    private static ForkJoinPool pool;

    private final int year, start, end;
    private static final File parent = new File("F:\\rfetcger\\Results\\");
    private static final String NOTFOUND = "Not found";

    /**
     * @param year
     * @param start
     */
    private ResultsOld(int year, int start) {
        this.year = year;
        this.start = start;
        this.end = this.start + 999;

    }

    private static boolean lookUP(int roll, int year) {

        String page = null;
        //TODO work as to find
        File path = getPath(roll + "", year);
        if (path.exists()) {
            return path.length() >= 5000;
        }
        try {
            page = fetc.getCBSEOldResult(roll + "", HtmlUnitClient.getSiteByYear(year));
            //System.out.println(page);
        } catch (Exception ex) {
            Logger.getLogger(ResultsOld.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        boolean ty = false;
        try {
            ty = (hasResult(page));
        } catch (java.lang.IllegalStateException f) {
            return false;
        } catch (Exception ex) {
            Logger.getLogger(ResultsOld.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ty;
    }

    private boolean exist(int roll) {
        return getPath(roll + "", year).exists();
    }

    private static File getPath(String roll, int year) {
        String name = roll + ".html";
        String up = parent.getAbsolutePath() + "\\" + year + "\\" + roll.substring(0, 2) + "\\" + roll.substring(0, 3) + "\\";
        File upFile = new File(up);
        if (!upFile.exists()) {
            upFile.mkdirs();
        }
        return new File(upFile, name);
    }

    private static boolean hasResult(String res) {
        return !(res == null || res.contains("Not Found") || res.contains("Please enter valid Roll no"));
    }

    private static void split(int JUMP, int end, int s, int year) {
        pool = new ForkJoinPool(10);
        CompletableFuture<ResultsOld>[] toArray = IntStream
                .iterate(s, i -> i + JUMP).limit((end - s) / JUMP)
                .parallel().mapToObj(i -> getResultsOld(i, year, pool))
                .toArray(CompletableFuture[]::new);

        //  CompletableFuture.allOf(toArray).join();
        Arrays.stream(toArray).map(CompletableFuture::join).filter(Objects::nonNull).forEach(ResultsOld::doRange);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        split(49, 6632710, 6622710, 2012);

    }

    private static CompletableFuture<ResultsOld> getResultsOld(int b, int year, ForkJoinPool pool) {

        return CompletableFuture.supplyAsync(
                () -> {

                    ResultsOld re = null;
                    try {
                        Thread.sleep(200l);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(comparabler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (lookUP(b, year)) {
                        re = new ResultsOld(year, b);
                        System.out.println("Adding " + re);
                        re.doRange();

                    } else {
                        System.out.println("Skipping " + " " + b);
                    }
                    return re;
                }, pool);

    }

    private void doRange() {
        if (year == 0) {
            return;
        }

        IntStream peek = IntStream.range(start, end).parallel().filter(in -> !exist(in)).peek(oi -> System.out.println(" ----- " + oi));
        pool.execute(() -> {
            peek.forEach(this::probe);
            System.out.println(" Current Thread " + Thread.currentThread().getName());
        });
    }

    private boolean savePage(String page, File output, int roll) {

        try (java.io.FileWriter wr = new java.io.FileWriter(output)) {
            Document doc = Jsoup.parse(page);
            Elements elements = doc.select("table");
            elements.forEach((fg) -> {
                if (fg.text().contains("Roll No") || fg.text().contains("SUB CODE")) {
                    try {
                        System.out.println("Saving Page ");
                        wr.write(fg.toString()
                                         .replace("Â Â Â Â", "").replace("Â", "")//.replace("mediumblue", "blue")
                        );
                    } catch (IOException ex) {
                        Logger.getLogger(HtmlUnitClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            wr.flush();
        } catch (IOException ex) {
            Logger.getLogger(ResultsOld.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        System.out.println("Found " + roll);
        return true;
    }

    /**
     *
     * @throws IOException
     */
    @Override
    public String toString() {
        return "ResultsOld{" + "year=" + year + ", start=" + start + ", end=" + end + '}';
    }

    private boolean probe(int r) {
        String roll = r + "";
        File output = getPath(roll, year);
        if (output.exists()) {

            return true;
        }
        //System.out.println("Probing " + roll);
        ResultClient f = new OkHttpClient();
        String page = null;
        try {
            page = f.getCBSEOldResult(roll, HtmlUnitClient.getSiteByYear(year));

            if (hasResult(page)) {
                return savePage(page, output, r);
            } else {
                try (java.io.FileWriter wr = new java.io.FileWriter(output)) {
                    wr.append(NOTFOUND);
                    wr.flush();
                }
                System.out.println("X - " + roll);
            }

        } catch (Exception ex) {
            String sd = roll + ";" + roll + String.format("%n");
            File excpt = new File("exceptions.txt");
            try {
                Files.write(excpt.toPath(), sd.getBytes(), APPEND);
            } catch (IOException ex1) {
                Logger.getLogger(ResultsOld.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ResultsOld.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

}
