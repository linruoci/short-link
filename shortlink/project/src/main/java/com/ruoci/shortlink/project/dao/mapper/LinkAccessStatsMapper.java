package com.ruoci.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoci.shortlink.project.dao.entity.LinkAccessStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 短链接基础访问监控持久层
 **/
public interface LinkAccessStatsMapper extends BaseMapper<LinkAccessStatsDO> {

    /**
     * 记录基础访问监控数据
     */
    @Insert(
            """
            INSERT INTO t_link_access_stats (full_short_url, gid, date, pv, uv, uip, hour, weekday, create_time, update_time, del_flag )
            VALUES(#{linkAccessStatsDO.fullShortUrl}, #{linkAccessStatsDO.gid}, #{linkAccessStatsDO.date}, #{linkAccessStatsDO.pv}, #{linkAccessStatsDO.uv}, #{linkAccessStatsDO.uip}, #{linkAccessStatsDO.hour}, #{linkAccessStatsDO.weekday}, NOW(), NOW(), 0)
            ON DUPLICATE KEY UPDATE
            pv = pv + #{linkAccessStatsDO.pv}, uv = uv + #{linkAccessStatsDO.uv}, uip = uip + #{linkAccessStatsDO.uip};
            """
    )
    void shortLinkStats(@Param("linkAccessStatsDO") LinkAccessStatsDO linkAccessStatsDO);
}
