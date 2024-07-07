package com.ruoci.shortlink.admin.dto.req.group;

import lombok.Data;

/**
 * 短链接分组创建参数
 **/
@Data
public class ShortLinkGroupUpdateReqDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名
     */
    private String name;
}
