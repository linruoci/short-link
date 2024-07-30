package com.ruoci.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoci.shortlink.project.dao.entity.LinkBrowserStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 短链接监控浏览器持久层
 **/
public interface LinkBrowserStatsMapper extends BaseMapper<LinkBrowserStatsDO> {
    @Insert(
            """
            INSERT INTO t_link_browser_stats (full_short_url, gid, date, cnt, browser, create_time, update_time, del_flag )
            VALUES(#{linkBrowserStatsDO.fullShortUrl}, #{linkBrowserStatsDO.gid}, #{linkBrowserStatsDO.date}, #{linkBrowserStatsDO.cnt}, 
            #{linkBrowserStatsDO.browser}, NOW(), NOW(), 0)
            ON DUPLICATE KEY UPDATE
            cnt = cnt + #{linkBrowserStatsDO.cnt};
            """
    )
    void shortLinkBrowserState(@Param("linkBrowserStatsDO") LinkBrowserStatsDO linkBrowserStatsDO);

}
