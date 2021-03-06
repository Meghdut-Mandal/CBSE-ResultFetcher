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
package newton.resultApi;

import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

/**
 * @author MICROSOFT
 */
public class OkHttpClient extends ResultClient {

    private okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

    public static void main(String[] args) throws IOException {
        ResultClient t = new OkHttpClient();
        String roll = "2650504";
        int year = 2012;
        String df = t.getCBSE18("6625902", "08423", "6219");
        CBSEResult result = t.getResult(df);
        System.out.println(result);
    }

    @Override
    public String getCBSE18(String roll, String schCode, String centerno) throws IOException {

        String site = getSiteByYear(2018);
        URL url = new URL(Objects.requireNonNull(site).replace("htm", "asp"));
        if (schCode.length() == 4) {
            schCode = "0" + schCode;
        }
        RequestBody body = new FormBody.Builder()
                .add("regno", roll)
                .add("sch", schCode)
                .add("cno", centerno)
                .add("B2", "Submit")
                .build();
        Map<String, String> tabel = new java.util.HashMap<>();

        tabel.put("Origin", "http://cbseresults.nic.in");//d
        tabel.put("Accept-Encoding", "gzip, deflate");//d
        tabel.put("Accept-Language", "en-US,en;q=0.9");//
        tabel.put("Upgrade-Insecure-Requests", "1");//
        tabel.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");//d
        tabel.put("Content-Type", "application/x-www-form-urlencoded");//d
        tabel.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");//d
        tabel.put("Referer", site);//d

        Headers headers = Headers.of(tabel);
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    @Override
    public String getCBSE17(String roll, String schCode, String centerno) throws IOException {
        String site = getSiteByYear(2017);
        URL url = new URL(Objects.requireNonNull(site).replace("htm", "asp"));
        if (schCode.length() == 4) {
            schCode = "0" + schCode;
        }
        RequestBody body = new FormBody.Builder()
                .add("regno", roll)
                .add("sch", schCode)
                .add("cno", centerno)
                .add("B2", "Submit")
                .build();
        Map<String, String> tabel = new java.util.HashMap<>();

        tabel.put("Origin", "http://resultsarchives.nic.in");//d
        tabel.put("Accept-Encoding", "gzip, deflate");//d
        tabel.put("Accept-Language", "en-US,en;q=0.9");//
        tabel.put("Upgrade-Insecure-Requests", "1");//
        tabel.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");//d
        tabel.put("Content-Type", "application/x-www-form-urlencoded");//d
        tabel.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");//d
        tabel.put("Referer", site);//d

        Headers headers = Headers.of(tabel);
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public String getCBSE16Result(String regno, String schCode) throws IOException {
        String site = "http://resultsarchives.nic.in/cbseresults/cbseresults2016/class12/cbse1216Revised.asp?regno=" + regno + "&schcode=" + schCode + "&B1=Submit";
        URL url = new URL(site);
        Map<String, String> tabel = new java.util.HashMap<>();

        tabel.put("Accept-Encoding", "gzip, deflate");//d
        tabel.put("Referer", "http://resultsarchives.nic.in/cbseresults/cbseresults2016/class12/cbse1216revised.htm");//d

        tabel.put("Accept-Language", "en-US,en;q=0.9");//
        tabel.put("Upgrade-Insecure-Requests", "1");//
        tabel.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");//d
        tabel.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");//d
        Headers headers = Headers.of(tabel);
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    @Override
    public String getCBSEOldResult(String regno, String site) throws IOException {

        URL url = new URL(site.replace("htm", "asp"));
        Map<String, String> tabel = new java.util.HashMap<>();

        RequestBody body = new FormBody.Builder()
                .add("regno", regno)
                .add("B1", "Submit")
                .build();

        tabel.put("Pragma", " no-cache");//d
        tabel.put("Origin", "http://resultsarchives.nic.in");//d
        tabel.put("Accept-Encoding", "gzip, deflate");//d
        tabel.put("Host", "resultsarchives.nic.in");//d
        tabel.put("Accept-Language", "en-US,en;q=0.9");//
        tabel.put("Upgrade-Insecure-Requests", "1");//
        tabel.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");//d
        tabel.put("Content-Type", "application/x-www-form-urlencoded");//d
        tabel.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");//d
        tabel.put("Cache-Control", "no-cache");//d
        tabel.put("Referer", site);//d
        tabel.put("Connection", "keep-alive");//
        tabel.put("DNT", "1");//d
        Headers headers = Headers.of(tabel);
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}

