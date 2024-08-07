package com.ruoci.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoci.shortlink.project.dao.entity.LinkAccessLogsDO;
import com.ruoci.shortlink.project.dao.entity.LinkAccessStatsDO;
import com.ruoci.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短链接监控高频访问持久层
 **/
public interface LinkAccessLogsMapper extends BaseMapper<LinkAccessLogsDO> {

    /**
     * 根据短链接获取指定日期内高频访问IP数据
     */
    @Select(
            """
            SELECT 
                ip, COUNT(ip) AS count FROM t_link_access_logs 
            WHERE 
                full_short_url = #{param.fullShortUrl} AND gid = #{param.gid} AND create_time BETWEEN CONCAT(#{param.startDate},' 00:00:00') and CONCAT(#{param.endDate},' 23:59:59')
            GROUP BY
                full_short_url, gid, ip
            ORDER BY
                count DESC
            LIMIT 5;
            """
    )
    List<HashMap<String, Object>> listTopIpByShortLink(@Param("param") ShortLinkStatsReqDTO requestParam);

    /**
     * 根据短链接获取指定日期内新旧访客数据
     */
    @Select(
            """
            SELECT
                SUM(old_user) AS oldUserCnt, 
                SUM(new_user) AS newUserCnt
            FROM (
                SELECT
                    CASE WHEN COUNT(DISTINCT DATE(create_time)) > 1 THEN 1 ELSE 0 END AS old_user,
                    CASE WHEN COUNT(DISTINCT DATE(create_time)) = 1 AND MAX(create_time) >= CONCAT(#{param.startDate},' 00:00:00') AND MAX(create_time) <= CONCAT(#{param.endDate},' 23:59:59') THEN 1 ELSE 0 END AS new_user
                FROM
                    t_link_access_logs
                WHERE
                    full_short_url = #{param.fullShortUrl}
                    AND gid = #{param.gid}
                GROUP BY user
                ) AS user_counts;
            """
    )
    HashMap<String, Object> findUvTypeCntByShortLink(@Param("param") ShortLinkStatsReqDTO requestParam);

    /**
     * TODO: 我觉得这里有点问题, 应该改为下面sql
     * """
     * SELECT
     *     log.id,
     *     log.user,
     *     log.create_time,
     *     CASE
     *         WHEN EXISTS (
     *             SELECT 1
     *             FROM t_link_access_logs AS prev_log
     *             WHERE prev_log.user = log.user
     *               AND prev_log.create_time < log.create_time
     *         ) THEN '老访客'
     *         ELSE '新访客'
     *     END AS visitor_status
     * FROM
     *     t_link_access_logs AS log
     * WHERE
     *     log.create_time BETWEEN '2024-08-04 00:00:00' AND '2024-08-04 23:59:59';
     * """
     * 获取用户信息是否新老访客
     */
    @Select(
            """
            <script>
            SELECT
                user, CASE WHEN MIN(create_time) BETWEEN CONCAT(#{startDate},' 00:00:00') AND CONCAT(#{endDate},' 23:59:59') THEN '新访客' ELSE '老访客' END AS uvType
            FROM
                t_link_access_logs
            WHERE
                full_short_url = #{fullShortUrl}
                AND gid = #{gid}
                AND user in
                    <foreach item='item' index='index' collection='userAccessLogsList' open='(' separator=',' close=')'>
                        #{item}
                    </foreach>
            GROUP BY
                user;
            </script>
            """
//            """
//                SELECT
//                    log.user,
//                    CASE
//                        WHEN EXISTS (
//                            SELECT 1
//                            FROM t_link_access_logs AS prev_log
//                            WHERE prev_log.user = log.user
//                              AND prev_log.create_time < log.create_time
//                              AND prev_log.full_short_url = log.full_short_url
//                        ) THEN '老访客'
//                        ELSE '新访客'
//                    END AS uvType
//                FROM
//                    t_link_access_logs AS log
//                WHERE
//                    log.full_short_url = #{fullShortUrl} and
//                    log.create_time BETWEEN CONCAT(#{startDate},' 00:00:00') AND CONCAT(#{endDate},' 23:59:59');
//            """
    )
    List<Map<String, Object>> selectUvTypeByUsers(
            @Param("gid") String gid,
            @Param("fullShortUrl") String fullShortUrl,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("userAccessLogsList") List<String> userAccessLogsList);


    /**
     * 根据短链接获取指定日期内 PV, UV, UIP数据
     */
    @Select(
            """
                SELECT
                    count(user) as pv,
                    count(distinct user) as uv,
                    count(distinct ip) as uip
                FROM
                    t_link_access_logs
                WHERE
                    full_short_url = #{param.fullShortUrl}
                    AND gid = #{param.gid}
                    AND create_time BETWEEN CONCAT(#{param.startDate},' 00:00:00') AND CONCAT(#{param.endDate},' 23:59:59')
                GROUP BY
                    full_short_url, gid;

            
            """
    )
    LinkAccessStatsDO findPvUvUidStatsByShortLink(@Param("param") ShortLinkStatsReqDTO requestParam);

}
