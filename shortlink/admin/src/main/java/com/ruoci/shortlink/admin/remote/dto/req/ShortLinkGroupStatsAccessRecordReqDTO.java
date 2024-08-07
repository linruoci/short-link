package com.ruoci.shortlink.admin.remote.dto.req;

import lombok.Data;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * 分组短链接监控访问记录请求参数
 */
@Data
public class ShortLinkGroupStatsAccessRecordReqDTO extends Page {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
}