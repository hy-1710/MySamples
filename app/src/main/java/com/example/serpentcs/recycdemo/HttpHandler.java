package com.example.serpentcs.recycdemo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by serpentcs on 24/11/16.
 */

public class HttpHandler {
    public static final String TAG = "HttpHandler";

    public HttpHandler() {
    }

    public String makeGetServicecall(String reqUrl) {
        String response = null;
        try {
            //1.pass the url
            URL url = new URL(reqUrl);
            //2.open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //3.setMehod
            conn.setRequestMethod("GET");
            //4.read response
            InputStream is = new BufferedInputStream(conn.getInputStream());
            response = convertStreamtoString(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public String makePostServiceCall(String reqUrl, HashMap<String, String> postDataParams) {
        String response = "";
        try {
            //1.pass the url
            URL url = new URL(reqUrl);
            //2.open connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(15000);
//            conn.setConnectTimeout(15000);
            //3.setMehod
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //4.write request
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    response += line;
                }

            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //for loop for one by one key and pair
        for (Map.Entry<String, String> entry : params.entrySet())
            if (first) {
                first = false;
                //  result.append("?");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } else {
                result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        return result.toString();
    }

    private String convertStreamtoString(InputStream is) {
        // convert the value from stream to string
        // 1. BufferReader obj
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        //3.
        StringBuilder bs = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                bs.append(line).append('\n');
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bs.toString();
        }
    }

    public String getToServer(String url, Map<String, String> map) throws Exception {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        if (map != null) {
            int index = -1;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                index++;
                if (index == 0) {
                    url += "?";
                }
                url += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
                if (index != map.size() - 1) {
                    url += "&";
                }
            }
        }
        // Log.i(TAG, "getToServer: url is: " + url);
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url);
        okhttp3.Request request = builder.build();
        okhttp3.Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException(response.message() + " " + response.toString());
        }
        return response.body().string();
    }

    /**
     * <strong>Uses:</strong><br/>
     * <p>
     * {@code
     * Map<String, String> map = new HashMap<>();}
     * <br/>
     * {@code map.put("key1", "value1");}<br/>
     * {@code map.put("key2", "value2");}<br/>
     * {@code map.put("key3", "value3");}<br/>
     * <br/>
     * {@code Utils.postToServer("http://www.example.com/", map);}<br/>
     * </p>
     *
     * @param url
     * @param map of java.util.Map
     * @return response from server in String format
     * @throws Exception
     */
    public String postToServer(String url, Map<String, String> map) throws Exception {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url);
        if (map != null) {
            okhttp3.FormBody.Builder postData = new okhttp3.FormBody.Builder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                postData.add(entry.getKey(), entry.getValue());
            }
            builder.post(postData.build());
        }
        okhttp3.Request request = builder.build();
        okhttp3.Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException(response.message() + " " + response.toString());
        }
        return response.body().string();
    }

}
