package com.ruoci.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoci.shortlink.project.dao.entity.ShortLinkDO;
import com.ruoci.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.ruoci.shortlink.project.dto.resp.ShortLinkCreateRespDTO;

/**
 * 短链接接口层
 **/
public interface ShortLinkService extends IService<ShortLinkDO> {
    /**
     * 新增短链接
     *
     * @param requestParam 新增短链接实体
     * @return 短链接信息
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam);
}
