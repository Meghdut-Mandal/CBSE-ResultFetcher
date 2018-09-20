/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newton.fetcher;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.CREATE;

/**
 *  *
 * @author Meghdut Mandal
 */
class Main {

    /**
     *
     * @param text
     * @return
     */
    public static boolean loggToServer(String text) {
        return true;
    }

    private static boolean logg(String text) {

        try {
            WebClient webClient = new WebClient();
            webClient.getOptions().setUseInsecureSSL(true);

            HtmlPage page = webClient.getPage("https://meghdutwindows.000webhostapp.com");
            final HtmlForm form = page.getFormByName("form");
            final HtmlTextInput regField = form.getInputByName("data");
            regField.type(text);
            HtmlTextArea erfTextarea = form.getTextAreaByName("txtAnswer");
            erfTextarea.setText(text);
            final HtmlSubmitInput button = form.getInputByName("submit");
            button.click();
        } catch (IOException | FailingHttpStatusCodeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        loggToServer("heloo ");
    }

    /**
     *
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void mainhh(String[] args) throws FileNotFoundException, IOException {
        StringBuilder sbr = new StringBuilder();
        Var<Integer> var = new Var<>();
        var.obj = 0;
        var.obj++;
        Files.lines(new File("subjcode.txt").toPath()).forEach(line -> {
            List<String> li = Arrays.asList(line.split(" ", 2));
            var.obj++;
            sbr.append("{\"").append(li.get(0)).append("\",\"").append(li.get(1)).append("\"}, ");
            if (var.obj % 6 == 0) {
                sbr.append(String.format("%n"));
            }
        });
        Files.write(new File("out.txt").toPath(), sbr.toString().getBytes(), CREATE);

    }

    /**
     *
     * @param args
     */
    public static void majin(String[] args) {
        App.main(args);
    }

}
