package com.kevin.confcenter.common.utils;

import com.kevin.confcenter.common.exception.RemoteCallException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理系统对外HTTP调用的工具类.
 *
 * @author hanzhiwei
 * @version 1.0
 * @title HttpUtils
 * @description 处理系统对外HTTP调用的工具类
 * @date 2016年8月29日
 */
public class HttpUtil {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 连接超时时间
     */
    private static final int CONNECT_TIME_OUT = 60000;

    /**
     * socket超时时间
     */
    private static final int SOCKET_TIME_OUT = 15000;

    /**
     * 错误码
     */
    private static final int ERROR_STATUS_CODE = 200;

    /**
     * utf8
     */
    private static final String UTF8 = "utf-8";

    /**
     * 配置
     */
    private static final RequestConfig CONFIG = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT).setSocketTimeout(SOCKET_TIME_OUT)
            .build();

    /**
     * 私有构造函数
     */
    private HttpUtil() {
    }

    /**
     * 发送HTTP GET请求到指定URL
     *
     * @param url     请求的URL
     * @param params  get请求的附加参数
     * @param headers header中需要添加的内容
     * @return get response内容
     * @throws RemoteCallException 远程调用异常
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headers)
            throws RemoteCallException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(CONFIG).build();
        HttpGet httpGet = null;
        InputStream inputStream = null;
        try {
            // url中追加参数
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (StringUtils.isNotEmpty(value)) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                String seperator = (url.indexOf("?") == -1) ? "?" : "&";
                url += seperator + EntityUtils.toString(new UrlEncodedFormEntity(pairs, UTF8));
            }
            LOGGER.info("client http doGet url:{}", url);
            httpGet = new HttpGet(url);

            // 增加header信息
            appendHeader(httpGet, headers);

            // 发送请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            checkStatus(response, httpGet);

            // 处理response
            inputStream = response.getEntity().getContent();
            String result = IOUtils.toString(inputStream, UTF8);
            LOGGER.debug("the http response is:{}", result);
            return result;
        } catch (Exception e) {
            LOGGER.error(" Client do get exception,the url is:" + url, e);
            throw new RemoteCallException(e.getMessage(), e);
        } finally {
            cleanup(inputStream, httpGet, httpClient);
        }
    }

    /**
     * Client发送HTTP GET请求到指定URL，传入的参数会以URL参数对的方式追加在url之后
     *
     * @param url    请求的URL
     * @param params 参数信息
     * @return 结果
     * @throws RemoteCallException     远程请求异常
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(String url, Map<String, String> params) throws RemoteCallException {
        return doGet(url, params, null);
    }

    /**
     * Client通过HTTP POST方法发送body内容至指定URL
     *
     * @param url  目标URL
     * @param body 发送的内容
     * @return 返回结果
     * @throws RemoteCallException 远程调用异常
     */
    public static String doPost(String url, String body) throws RemoteCallException {
        return doPost(url, body, null);
    }

    /**
     * Client通过HTTP POST方法发送body内容至指定URL
     *
     * @param url     目标URL
     * @param body    发送的内容
     * @param headers header中需要添加的内容
     * @return 返回结果
     * @throws RemoteCallException 远程调用异常
     */
    public static String doPost(String url, String body, Map<String, String> headers) throws RemoteCallException {
        InputStream inputStream = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(CONFIG).build();

        HttpPost httpPost = new HttpPost(url);
        // 增加header信息
        appendHeader(httpPost, headers);

        try {
            HttpEntity entity = new StringEntity(body, UTF8);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            checkStatus(response, httpPost);
            inputStream = response.getEntity().getContent();
            String result = IOUtils.toString(inputStream, UTF8);
            LOGGER.debug("the http response is:{}", result);
            return result;
        } catch (Exception e) {
            LOGGER.error("Client do post exception,the url is:" + url, e);
            throw new RemoteCallException(e.getMessage(), e);
        } finally {
            cleanup(inputStream, httpPost, httpClient);
        }
    }

    /**
     * @param response    回复
     * @param httpRequest 请求
     */
    public static void checkStatus(CloseableHttpResponse response, HttpRequestBase httpRequest) {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != ERROR_STATUS_CODE) {
            httpRequest.abort();
            throw new RemoteCallException("Http post error status code :" + statusCode);
        }
    }

    /**
     * @param inputStream 输入流
     * @param request     请求
     * @param httpClient  httpclient
     */
    public static void cleanup(InputStream inputStream, HttpRequestBase request, CloseableHttpClient httpClient) {
        IOUtils.closeQuietly(inputStream);
        if (request != null) {
            request.releaseConnection();
        }
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            LOGGER.error("close client exception", e);
        }
    }

    /**
     * @param headers 头信息
     * @param http    http信息
     */
    public static void appendHeader(HttpMessage http, Map<String, String> headers) {
        http.addHeader("content-type", "application/json;charset=UTF-8");
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                http.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }
}
