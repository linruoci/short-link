package com.ruoci.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoci.shortlink.admin.common.convention.result.Result;
import com.ruoci.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.ruoci.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;

/**
 * URL 回收站接口层
 **/
public interface RecycleBinService {

    /**
     * 分页查询回收站短链接
     *
     * @param requestParam 请求参数
     * @return 返回短链接列表
     */
    Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO requestParam);

}
