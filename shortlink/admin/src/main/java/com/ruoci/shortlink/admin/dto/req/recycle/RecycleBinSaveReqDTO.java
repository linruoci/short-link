package com.ruoci.shortlink.admin.dto.req.recycle;

import lombok.Data;

/**
 * 回收站保存请求参数
 **/
@Data
public class RecycleBinSaveReqDTO {

    /**
     * 分组标识
     */
    private String gid;
    /**
     * 完整短链接
     */
    private String fullShortUrl;


}
