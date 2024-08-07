package com.ruoci.shortlink.admin.dto.resp.group;

import lombok.Data;

/**
 * 短链接分组返回对象
 **/
@Data
public class ShortLinkGroupRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 创建分组用户
     */
    private String username;

    /**
     * 分组排序
     */
    private Integer sortOrder;

    /**
     * 短链接数量
     */
    private Integer shortLinkCount;


}
