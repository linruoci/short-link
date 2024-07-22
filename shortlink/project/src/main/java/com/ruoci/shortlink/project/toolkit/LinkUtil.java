package com.ruoci.shortlink.project.toolkit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.ruoci.shortlink.project.common.constant.ShortLinkConstant;

import java.util.Date;
import java.util.Optional;

/**
 * 短链接工具类
 **/
public class LinkUtil {

    /**
     * 获取短链接缓存有效时间
     * @param validDate 有效期时间
     * @return 有效期时间戳
     */
    public static long getLinkCacheValidTime(Date validDate){
        return Optional.ofNullable(validDate)
                .map(each -> DateUtil.between(new Date(), each, DateUnit.MS))
                .orElse(ShortLinkConstant.DEFAULT_CACHE_VALID_TIME);
    }

}
