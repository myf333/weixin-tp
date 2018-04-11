package com.myf.weixin.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by maoyf0503 on 2018-4-11.
 *
 * @author maoyf0503
 */
@Service
public class HttpRequestService {
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_NORAML_FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final String ACCEPT_TYPE = "text/json";

    private String processUrl(String url,Map<String,String> params){
        if(params!=null && params.size()>0){
            url += "?"+processParams(params);
        }
        return url;
    }

    private String processParams(Map<String,String> paramsMap){
        String params = "";
        if(paramsMap!=null && paramsMap.size()>0){
            int i = 0;
            for (String param:paramsMap.keySet()) {
                if (i == 0) {
                    params += String.format("%s=%s", param, paramsMap.get(param));
                } else {
                    params += String.format("&%s=%s", param, paramsMap.get(param));
                }
                i++;
            }
        }
        return params;
    }

    public String get(String url,Map<String,String> params) throws Exception {
        url = processUrl(url,params);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", ACCEPT_TYPE)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("请求失败,code: " + response);
        }
        return response.body().string();
    }

    public String get(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", ACCEPT_TYPE)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("请求失败,code: " + response);
        }
        return response.body().string();
    }

    public String post(String url,Map<String,String> params) throws Exception{
        RequestBody formBody = RequestBody.create(MEDIA_TYPE_NORAML_FORM,processParams(params));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", ACCEPT_TYPE)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("请求失败,code: " + response);
        }
        return response.body().string();
    }

    public String postJson(String url,String jsonStr) throws Exception{
        RequestBody jsonBody = RequestBody.create(MEDIA_TYPE_JSON,jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", ACCEPT_TYPE)
                .post(jsonBody)
                .build();
        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()) {
            throw new IOException("请求失败,code: " + response);
        }
        return response.body().string();
    }

    public  Response download(String url,Map<String,String> params) throws Exception {
        url = processUrl(url,params);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("请求失败,code: " + response);
        }
        return response;
    }

    public String uploadFile(String url,Map<String,String> params,String formData,String fileName,String filePath) throws Exception{
        File file = new File(filePath);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .addPart(RequestBody.create(MEDIA_TYPE_NORAML_FORM,processParams(params)))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "text/json")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("请求失败,code: " + response);
        }
        return response.body().string();
    }
}
