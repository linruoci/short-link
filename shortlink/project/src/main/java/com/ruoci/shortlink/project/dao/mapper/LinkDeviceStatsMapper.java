package com.ruoci.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoci.shortlink.project.dao.entity.LinkDeviceStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 短链接监控浏览器持久层
 **/
public interface LinkDeviceStatsMapper extends BaseMapper<LinkDeviceStatsDO> {
    @Insert(
            """
            INSERT INTO t_link_device_stats (full_short_url, gid, date, cnt, device, create_time, update_time, del_flag )
            VALUES(#{linkDeviceStatsDO.fullShortUrl}, #{linkDeviceStatsDO.gid}, #{linkDeviceStatsDO.date}, #{linkDeviceStatsDO.cnt}, 
            #{linkDeviceStatsDO.device}, NOW(), NOW(), 0)
            ON DUPLICATE KEY UPDATE
            cnt = cnt + #{linkDeviceStatsDO.cnt};
            """
    )
    void shortLinkDeviceState(@Param("linkDeviceStatsDO")LinkDeviceStatsDO linkDeviceStatsDO);

}
