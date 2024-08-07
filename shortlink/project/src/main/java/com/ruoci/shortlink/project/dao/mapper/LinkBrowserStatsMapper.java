package com.ruoci.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoci.shortlink.project.dao.entity.LinkBrowserStatsDO;
import com.ruoci.shortlink.project.dto.req.ShortLinkGroupStatsReqDTO;
import com.ruoci.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * 短链接监控浏览器持久层
 **/
public interface LinkBrowserStatsMapper extends BaseMapper<LinkBrowserStatsDO> {
    @Insert(
            """
            INSERT INTO 
                t_link_browser_stats (full_short_url, gid, date, cnt, browser, create_time, update_time, del_flag )
            VALUES(
                #{linkBrowserStatsDO.fullShortUrl}, #{linkBrowserStatsDO.gid}, #{linkBrowserStatsDO.date}, #{linkBrowserStatsDO.cnt}, 
                #{linkBrowserStatsDO.browser}, NOW(), NOW(), 0
            )
            ON DUPLICATE KEY UPDATE
                cnt = cnt + #{linkBrowserStatsDO.cnt};
            """
    )
    void shortLinkBrowserState(@Param("linkBrowserStatsDO") LinkBrowserStatsDO linkBrowserStatsDO);

    /**
     * 根据短链接获取指定日期内浏览器监控数据
     */
    @Select(
            """
            SELECT
              browser, SUM(cnt) AS count
            FROM 
                t_link_browser_stats
            WHERE 
                full_short_url = #{param.fullShortUrl}
                AND gid = #{param.gid}
                 AND date BETWEEN #{param.startDate} and #{param.endDate}
            GROUP BY 
                full_short_url, gid, browser;
            """
    )
    List<HashMap<String, Object>> listBrowserStatsByShortLink(@Param("param") ShortLinkStatsReqDTO requestParam);


    /**
     * 根据分组获取指定日期内浏览器监控数据
     */
    @Select(
            """
            SELECT
                browser,
                SUM(cnt) AS count
            FROM
                t_link_browser_stats
            WHERE
                gid = #{param.gid}
            AND date BETWEEN #{param.startDate} and #{param.endDate}
            GROUP BY
                gid, browser;
            """
            )
    List<HashMap<String, Object>> listBrowserStatsByGroup(@Param("param") ShortLinkGroupStatsReqDTO requestParam);



}
