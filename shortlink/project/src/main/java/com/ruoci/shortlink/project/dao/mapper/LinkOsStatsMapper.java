package com.ruoci.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoci.shortlink.project.dao.entity.LinkOsStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 短链接监控浏览器持久层
 **/
public interface LinkOsStatsMapper extends BaseMapper<LinkOsStatsDO> {
    @Insert(
            """
            INSERT INTO t_link_os_stats (full_short_url, gid, date, cnt, os, create_time, update_time, del_flag )
            VALUES(#{linkOsStatsDO.fullShortUrl}, #{linkOsStatsDO.gid}, #{linkOsStatsDO.date}, #{linkOsStatsDO.cnt}, 
            #{linkOsStatsDO.os}, NOW(), NOW(), 0)
            ON DUPLICATE KEY UPDATE
            cnt = cnt + #{linkOsStatsDO.cnt};
            """
    )
    void shortLinkOsState(@Param("linkOsStatsDO") LinkOsStatsDO linkOsStatsDO);

}
