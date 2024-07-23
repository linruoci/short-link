package com.ruoci.shortlink.project.service;

/**
 * URL 标题接口层
 **/
public interface UrlTitleService {

    /**
     * 根据链接获取网站标题
     * @param url 目标网站地址
     * @return 目标网站标题
     */
    String getTitleByUrl(String url);

}
