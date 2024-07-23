package com.ruoci.shortlink.project.service.impl;

import com.ruoci.shortlink.project.common.convention.exception.ClientException;
import com.ruoci.shortlink.project.service.UrlTitleService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * URL 标题接口实现层
 **/
@Service
public class UrlTitleServiceImpl implements UrlTitleService {

    private static final int TIMEOUT_MILLIS = 5000; // 设置超时时间为5秒

    @SneakyThrows
    @Override
    public String getTitleByUrl(String url) {
        URL tagetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) tagetUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Document document = null;
            try{
                document = Jsoup.connect(url)
                        .timeout(TIMEOUT_MILLIS)
                        .get();
            } catch (Exception e){
                throw new ClientException("连接超时");
            }
            return document.title();
        }
        return "Error while fetching title";
    }
}
