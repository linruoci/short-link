package com.ruoci.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoci.shortlink.project.dao.entity.LinkLocaleStatsDO;
import com.ruoci.shortlink.project.dto.req.ShortLinkGroupStatsReqDTO;
import com.ruoci.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 短链接监控地区持久层
 **/
public interface LinkLocaleStatsMapper extends BaseMapper<LinkLocaleStatsDO> {
    @Insert(
            """
            INSERT INTO t_link_locale_stats (full_short_url, gid, date, cnt, province, city, adcode, country, create_time, update_time, del_flag )
            VALUES(#{linkLocaleStatsDO.fullShortUrl}, #{linkLocaleStatsDO.gid}, #{linkLocaleStatsDO.date}, #{linkLocaleStatsDO.cnt}, 
            #{linkLocaleStatsDO.province}, #{linkLocaleStatsDO.city},#{linkLocaleStatsDO.adcode},  
            #{linkLocaleStatsDO.country}, NOW(), NOW(), 0)
            ON DUPLICATE KEY UPDATE
            cnt = cnt + #{linkLocaleStatsDO.cnt};
            """
    )
    void shortLinkLocaleStats(@Param("linkLocaleStatsDO") LinkLocaleStatsDO linkLocaleStatsDO);

    /**
     * 根据短链接获取指定日期内基础监控数据
     */
    @Select(
            """
            SELECT
                province, SUM(cnt) AS cnt
            FROM
                t_link_locale_stats
            WHERE
                full_short_url = #{param.fullShortUrl}
                AND gid = #{param.gid}
                AND date BETWEEN #{param.startDate} and #{param.endDate}
            GROUP BY
                full_short_url, gid, province;
            """
    )
    List<LinkLocaleStatsDO> listLocaleByShortLink(@Param("param") ShortLinkStatsReqDTO requestParam);

    /**
     * 根据分组获取指定日期内地区监控数据
     */
    @Select(
            """
            SELECT
                province,
                SUM(cnt) AS cnt
            FROM
                t_link_locale_stats
            WHERE
                gid = #{param.gid}
                AND date BETWEEN #{param.startDate} and #{param.endDate}
            GROUP BY
                gid, province;
            """
            )
    List<LinkLocaleStatsDO> listLocaleByGroup(@Param("param") ShortLinkGroupStatsReqDTO requestParam);

}
