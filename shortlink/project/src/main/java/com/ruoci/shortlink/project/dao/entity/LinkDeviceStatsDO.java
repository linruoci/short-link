package com.ruoci.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoci.shortlink.project.common.database.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 短链接监控之访问设备实体
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_link_device_stats")
public class LinkDeviceStatsDO extends BaseDO {
    /**
     * id
     */
    private Long id;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 日期
     */
    private Date date;

    /**
     * 访问量
     */
    private Integer cnt;

    /**
     * 设备名称
     */
    private String device;
}
