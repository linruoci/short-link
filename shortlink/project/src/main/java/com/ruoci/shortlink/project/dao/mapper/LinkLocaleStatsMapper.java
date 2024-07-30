package com.ruoci.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoci.shortlink.project.dao.entity.LinkLocaleStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

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

}
