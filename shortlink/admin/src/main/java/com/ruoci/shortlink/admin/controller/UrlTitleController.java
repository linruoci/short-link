package com.ruoci.shortlink.admin.controller;

import com.ruoci.shortlink.admin.common.convention.result.Result;
import com.ruoci.shortlink.admin.remote.ShortLinkRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * URL 标题控制层
 **/
@RestController
@RequiredArgsConstructor
public class UrlTitleController {


    /**
     * TODO: 后期修改为SpringCloud
     */
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {};

    /**
     * 根据链接获取网站标题
     */
    @GetMapping("/api/short-link/admin/v1/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url){
        return shortLinkRemoteService.getTitleByUrl(url);
    }


}
