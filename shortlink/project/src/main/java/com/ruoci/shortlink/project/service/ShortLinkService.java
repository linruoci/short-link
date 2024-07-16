package com.ruoci.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoci.shortlink.project.dao.entity.ShortLinkDO;
import com.ruoci.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.ruoci.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.ruoci.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.ruoci.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.ruoci.shortlink.project.dto.resp.ShortLinkPageRespDTO;

import java.util.List;

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

    /**
     * 短链接分页接口
     * @param requestParam 短链接分页请求参数
     * @return 短链接分页结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam);

    /**
     * 短链接分组内数量
     * @param requestParam 短链接分组内数量请求参数
     * @return 短链接分组内结果
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> requestParam);
}
