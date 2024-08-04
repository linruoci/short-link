package com.ruoci.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoci.shortlink.project.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.ruoci.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.ruoci.shortlink.project.dto.resp.ShortLinkStatesAccessRecordRespDTO;
import com.ruoci.shortlink.project.dto.resp.ShortLinkStatsRespDTO;

/**
 * 短链接监控接口层
 */
public interface ShortLinkStatsService {

    /**
     * 获取单个短链接监控数据
     *
     * @param requestParam 获取短链接监控数据入参
     * @return 短链接监控数据
     */
    ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam);

    /**
     * 访问单个短链接指定时间范围内访问记录监控数据
     *
     * @param requestParam 获取短链接监控访问记录数据入参
     * @return 访问记录监控数据
     */
    IPage<ShortLinkStatesAccessRecordRespDTO> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam);
}