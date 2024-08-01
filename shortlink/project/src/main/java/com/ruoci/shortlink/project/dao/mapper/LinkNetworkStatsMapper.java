package com.ruoci.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoci.shortlink.project.dao.entity.LinkNetworkStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 短链接监控浏览器持久层
 **/
public interface LinkNetworkStatsMapper extends BaseMapper<LinkNetworkStatsDO> {
    @Insert(
            """
            INSERT INTO t_link_network_stats (full_short_url, gid, date, cnt, network, create_time, update_time, del_flag )
            VALUES(#{linkNetworkStatsDO.fullShortUrl}, #{linkNetworkStatsDO.gid}, #{linkNetworkStatsDO.date}, #{linkNetworkStatsDO.cnt}, 
            #{linkNetworkStatsDO.network}, NOW(), NOW(), 0)
            ON DUPLICATE KEY UPDATE
            cnt = cnt + #{linkNetworkStatsDO.cnt};
            """
    )
    void shortLinkNetworkState(@Param("linkNetworkStatsDO") LinkNetworkStatsDO linkNetworkStatsDO);

}
