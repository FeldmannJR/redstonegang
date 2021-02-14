package dev.feldmann.redstonegang.common.api;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.utils.formaters.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {


    String baseUrl;
    String apiVersion;
    String token;

    String endpoint;

    HashMap<String, String> params;
    HashMap<String, File> files;
    String json = null;

    private int timeout = 5000;


    public RequestBuilder(String baseUrl, String apiVersion, String token, String endpoint) {
        this.endpoint = endpoint;
        this.params = new HashMap<>();
        this.files = new HashMap<>();
        this.baseUrl = baseUrl;
        this.apiVersion = apiVersion;
        this.token = token;
        if (StringUtils.isNullOrEmpty(baseUrl) || StringUtils.isNullOrEmpty(token) || StringUtils.isNullOrEmpty(apiVersion)) {
            RedstoneGang.instance().alert("API Não foi configurada!");
        }
    }


    public RequestBuilder data(String key, String value) {
        params.put(key, value);
        return this;
    }

    public RequestBuilder data(String key, Integer value) {
        params.put(key, value.toString());
        return this;
    }

    public RequestBuilder file(String key, File file) {
        files.put(key, file);
        return this;
    }

    public RequestBuilder json(String postjson)
    {
        this.json = postjson;
        return this;
    }

    public <T> Response<T> get(Class<T> responseClass) {
        return response("GET", responseClass);
    }

    public Response get() {
        return response("GET", null);
    }

    public <T> Response<T> post(Class<T> responseClass) {
        return response("POST", responseClass);
    }

    public Response post() {
        return post(null);
    }

    private String buildUrl() {
        return removeLastBar(baseUrl) + '/' + apiVersion + '/' + removeFirstBar(endpoint);
    }

    private HttpUriRequest buildRequest(String method) {
        org.apache.http.client.methods.RequestBuilder builder = org.apache.http.client.methods.RequestBuilder.create(method)
                .addHeader("RG-Api-Key", token)
                .setUri(buildUrl());
        if (!files.isEmpty()) {
            MultipartEntityBuilder multipart = MultipartEntityBuilder.create();
            for (Map.Entry<String, File> file : files.entrySet()) {
                multipart.addPart(file.getKey(), new FileBody(file.getValue()));
            }
            for (Map.Entry<String, String> entry : params.entrySet()) {
                multipart.addTextBody(entry.getKey(), entry.getValue());
            }
            builder.setEntity(multipart.build());
        }
        else if (this.json != null)
        {
            builder.addHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
            StringEntity postEntity = new StringEntity(this.json, ContentType.APPLICATION_JSON);
            builder.setEntity(postEntity);
        }
        else
        {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addParameter(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    private RequestConfig getConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setCircularRedirectsAllowed(false)
                .build();
    }

    public <T> Response<T> response(String method, Class<T> responseClass) {
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(getConfig()).build();
        boolean success = true;
        String json = null;
        int code = -1;
        try {
            HttpUriRequest request = buildRequest(method);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            code = response.getStatusLine().getStatusCode();
            if (code < 200 || code >= 300) {
                success = false;
            }
            json = EntityUtils.toString(entity);
            RedstoneGang.instance().debug(method + ' ' + buildUrl() + " -> " + code + "  " + json);
        } catch (IOException e) {
            success = false;
        }

        Response<T> r = new Response<T>(responseClass, code, json, success);
        return r;

    }

    private File download(String method, String filePath) {
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(getConfig()).build();

        try {
            HttpUriRequest request = buildRequest(method);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            int code = response.getStatusLine().getStatusCode();
            RedstoneGang.instance().debug(method + ' ' + buildUrl() + " -> " + code + "  ");
            if (code < 200 || code >= 300) {
                return null;
            }
            entity.writeTo(new FileOutputStream(filePath));
            return new File(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File getFile(String path) {
        return download("GET", path);
    }


    private String removeFirstBar(String s) {
        if (s == null) return null;
        if (s.length() == 0) return s;
        if (s.charAt(0) == '/') {
            s = s.substring(1);
        }
        return s;
    }

    private String removeLastBar(String s) {
        if (s == null) return null;
        if (s.length() == 0) return s;
        if (s.charAt(s.length() - 1) == '/') {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

}
