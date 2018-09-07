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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.APPEND;
import java.util.logging.Level;
import java.util.logging.Logger;
import newton.resultApi.HtmlUnitClient;
import newton.resultApi.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Nitin
 */
public class Results2017 {

    private static boolean save(File affFolder, String aff, String schCode, String cntrCode, String roll) throws IOException {
        OkHttpClient f = new OkHttpClient();
        //6622397;6622457
        // HtmlPage hj = f.getCBSE16Result("6620110", "09909");

        // File affFolder = new File("CBSE17/"+aff);
        File output = new File("CBSE17/" + aff + "/" + roll + ".html");
        if (output.exists()) {
            return true;
        }
        String page = null;
        try {
            page = f.getCBSE17(roll, schCode, cntrCode);

            if (!(page == null || page.contains("Not Found") || page.contains("Please enter valid Roll no"))) {

                java.io.FileWriter wr = new java.io.FileWriter(output);
                Document doc = Jsoup.parse(page);
                Elements elements = doc.select("table");
                elements.stream().forEach((fg) -> {
                    if (fg.text().contains("Roll No") || fg.text().contains("SUB CODE")) {
                        try {
                            wr.write(fg.toString()
                                    .replace("Â Â Â Â", "").replace("Â", "")//.replace("mediumblue", "blue")
                            );
                        } catch (IOException ex) {
                            Logger.getLogger(HtmlUnitClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                wr.flush();
                wr.close();
                return true;
            }

        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            String sd = aff + ";" + schCode + ";" + cntrCode + ";" + roll + String.format("%n");
            File excpt = new File("exceptions.txt");
            Files.write(excpt.toPath(), sd.getBytes(), APPEND);
        }
        return false;
    }

    private static void scanLine(String line) {
        String[] split = line.split(";");
        String aff = split[0];
        String schCode = split[1];
        if (schCode.length() == 4) {
            schCode = "0" + schCode;
        }

        int roll = Integer.parseInt(split[3]);
        String cnterCode = split[2];
        int fail = 0;

        File test = new File("CBSE17/D" + aff);
        if (test.exists()) {
            return;
        }

        File affFolder = new File("CBSE17/" + aff);
        if (!affFolder.exists()) {
            affFolder.mkdirs();
        }
        for (int i = roll; fail < 6; i++) {
            try {
                System.out.println("Testing " + aff + " roll " + i);
                if (!save(affFolder, aff, schCode, cnterCode, i + "")) {
                    fail++;
                }
            } catch (IOException ex) {
                Logger.getLogger(Results2017.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fail = 0;
        for (int i = roll - 1; fail < 6; i--) {
            try {
                System.out.println("Testing " + aff + " roll " + i);
                if (!save(affFolder, aff, schCode, cnterCode, i + "")) {
                    fail++;
                }
            } catch (IOException ex) {
                Logger.getLogger(Results2017.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        affFolder.renameTo(new File("CBSE17/D" + aff));
        System.out.println("Done aff " + aff);
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "10");
        File list = new File("newSave.txt");
        Files.lines(list.toPath()).parallel().forEach(Results2017::scanLine);

    }
}
