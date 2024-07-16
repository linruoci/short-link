package com.ruoci.shortlink.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoci.shortlink.admin.common.convention.result.Result;
import com.ruoci.shortlink.admin.remote.ShortLinkRemoteService;
import com.ruoci.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.ruoci.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.ruoci.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.ruoci.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.ruoci.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接中台远程调用服务
 **/
@RestController
public class ShortLinkController {


    /**
     * TODO: 后期修改为SpringCloud
     */
    private ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {};

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam){
        return shortLinkRemoteService.createShortLink(requestParam);
    }

    /**
     * 短链接分页
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        return shortLinkRemoteService.pageShortLink(requestParam);
    }

    @GetMapping("/api/short-link/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam List<String> requestParam){
        return shortLinkRemoteService.listGroupShortLinkCount(requestParam);
    }


}
