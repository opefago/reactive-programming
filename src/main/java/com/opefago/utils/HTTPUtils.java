package com.opefago.utils;
import com.opefago.interfaces.OnRequestCompleted;
import com.opefago.models.BlockingResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class HTTPUtils {
    public static void get(final String url, final OnRequestCompleted completed){
        Executors.newCachedThreadPool().submit(()->{
            HttpGet request = new HttpGet(url);
            try(CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(request)){
                completed.requestCompleted(response.getStatusLine().getStatusCode(),
                        response.getEntity()!=null ?
                                EntityUtils.toString(response.getEntity()): null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static BlockingResponse getBlocking(final String url){
        HttpGet request = new HttpGet(url);
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(request)){
            return new BlockingResponse(response.getStatusLine().getStatusCode(),
                    response.getEntity()!=null ?
                            EntityUtils.toString(response.getEntity()): null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static BlockingResponse postBlocking(final String url, String payload){
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        return postBlocking(url, header, payload);
    }
    public static BlockingResponse postBlocking(final String url, HashMap<String, String> headers, String payload){
            HttpPost post = new HttpPost(url);
            for (Map.Entry<String, String> headerValue: headers.entrySet()) {
                post.addHeader(headerValue.getKey(), headerValue.getValue());
            }
            try {
                post.setEntity(new StringEntity(payload));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {

                return new BlockingResponse(response.getStatusLine().getStatusCode(),
                        response.getEntity()!=null ?
                                EntityUtils.toString(response.getEntity()): null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    public static void post(final String url, HashMap<String, String> headers,
                            String payload, final OnRequestCompleted completed){
        Executors.newCachedThreadPool().submit(()->{
            HttpPost post = new HttpPost(url);
            for (Map.Entry<String, String> headerValue: headers.entrySet()) {
                post.addHeader(headerValue.getKey(), headerValue.getValue());
            }
            try {
                post.setEntity(new StringEntity(payload));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {

                completed.requestCompleted(response.getStatusLine().getStatusCode(),
                        response.getEntity()!=null ?
                                EntityUtils.toString(response.getEntity()): null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void post(final String url, String payload, final OnRequestCompleted completed){
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        post(url, header, payload, completed);
    }
}
