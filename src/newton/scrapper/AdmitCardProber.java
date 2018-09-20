/*
 * Copyright 2018 Meghdut  Mandal.
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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 *
 * @author Meghdut Mandal
 */
class AdmitCardProber {

    private static File folder = new File("ResultScraper\\ac\\");
    private static String postUrl = "http://cnvg.in/cbseapi/detail_api.php";

    /**
     *
     * @param dig
     * @param num
     * @return
     */
    public static String padNumber(int dig, int num) {
        StringBuilder numb = new StringBuilder();
        for (int i = 1; i < dig - Math.log10(num); i++) {
            numb.append("0");
        }
        return numb.toString() + num;
    }

    private static void saveAdmitcard(AdmitCard card) {
        OutputStream outStr = null;
        try {

            String roll = card.getRollno();
            File out = new File(folder, roll.substring(0, 3) + "\\out" + roll + ".obj");
            if (!out.getParentFile().exists()) {
                out.getParentFile().mkdir();
            }
            if (out.exists()) {
                return;
            }
            outStr = Files.newOutputStream(out.toPath());
            System.out.println("Saved " + card.toString());
            java.io.ObjectOutputStream objOut = new java.io.ObjectOutputStream(outStr);
            objOut.writeObject(card);
            objOut.flush();
            outStr.close();
        } catch (Exception ex) {
            Logger.getLogger(AdmitCardProber.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static boolean doesnotExist(String roll) {
        //   System.out.println("Req ");n

        return !(new File(folder, roll.substring(0, 3) + "\\out" + roll + ".obj").exists());

    }

    public static void main(String[] args) {
        System.out.println("The out " + lookUp("2117223", "10"));
    }


    /**
     * @param args
     */
    public static void maind(String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");
        List<Integer> ch = Arrays.asList(
                16, 17, 18, 19, 21, 26, 27, 28, 29, 36, 37, 38, 39, 46, 47, 48, 49, 56, 57, 58, 59,
                61, 62, 66,
                67, 68, 69, 76, 77, 78, 79, 91, 92, 93, 94, 95, 96, 97, 98, 99);

        System.out.println("probed " + peekAtRange(159));

    }

    private static boolean peekAtRange(int ch) {
        int dig = (int) (Math.floor(Math.log10(ch)) + 1);

        return IntStream
                .rangeClosed(0, (int) Math.pow(10, 7 - dig) - 1)
                .filter(num -> num % 497 == 0)
                .mapToObj((i) -> String.format("%d%s", ch, padNumber(dig, i)))
                .parallel()
                .filter(AdmitCardProber::doesnotExist)
                .map((genRoll) -> lookUp(genRoll, "12"))
                .filter(Objects::nonNull)
                .peek(AdmitCardProber::saveAdmitcard)
                .anyMatch(obj -> !(obj instanceof NullAdmitCard));

    }

    private static void probeCards(int ch) {

        int dig = (int) (Math.floor(Math.log10(ch)) + 1);

        IntStream.rangeClosed(0, (int) Math.pow(10, 7 - dig) - 1)
                .filter(num -> num % 48 == 0)
                .mapToObj((i) -> ch + padNumber(dig, i))
                .parallel()
                .filter(AdmitCardProber::doesnotExist)
                //.forEach(System.out::println);
                .map((genroll) -> lookUp(genroll, "12"))
                .filter(Objects::nonNull)
                .forEach(AdmitCardProber::saveAdmitcard);

    }

    /**
     *
     * @param roll
     * @param clazz
     * @return
     */
    public static AdmitCard lookUp(String roll, String clazz) {
        System.out.println("Probing " + roll);
        try {
            HashMap<String, String> value = new HashMap<>();
            value.put("rollno", roll);// "5850560");
            value.put("class", clazz);//"12");
            Gson gson = new Gson();
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(postUrl);
            StringEntity postingString = new StringEntity(gson.toJson(value));//gson.tojson() converts your pojo to json
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            org.apache.http.HttpResponse execute = httpClient.execute(post);
            HttpEntity entity = execute.getEntity();
            InputStream instream = entity.getContent();
            String result = convertStreamToString(instream);
            // now you have the string representation of the HTML request
            // System.out.println("RESPONSE: " + result);
            JSONObject jsonObject = new JSONObject(String.valueOf(result));
            String success = jsonObject.get("success_message").toString();
            if (success.contains("DATA NOT FOUND")) {
                AdmitCard fg = new NullAdmitCard();
                fg.setRollno(roll);
                return fg;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONObject jsobj = jsonArray.getJSONObject(0);
            return gson.fromJson(jsobj.toString(), AdmitCard.class);
        } catch (IOException | UnsupportedOperationException | JSONException | JsonSyntaxException ex) {
            Logger.getLogger(AdmitCardProber.class.getName()).log(Level.SEVERE, null, ex);
            AdmitCard fg = new NullAdmitCard();
            fg.setRollno(roll);
            return fg;

        }
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
        } finally {

            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(AdmitCardProber.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return sb.toString();
    }
}
