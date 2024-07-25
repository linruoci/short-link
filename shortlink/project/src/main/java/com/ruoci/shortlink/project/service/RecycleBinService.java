package com.ruoci.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoci.shortlink.project.dao.entity.ShortLinkDO;
import com.ruoci.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.ruoci.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.ruoci.shortlink.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.ruoci.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 * 回收站管理接口层
 **/
public interface RecycleBinService extends IService<ShortLinkDO> {

    /**
     * 保存回收站
     *
     * @param requestParam 请求参数
     */
    void saveRecycleBin(RecycleBinSaveReqDTO requestParam);

    /**
     * 回收站短链接分页接口
     *
     * @param requestParam 分页请求参数
     * @return 分页结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkRecycleBinPageReqDTO requestParam);

    /**
     * 恢复回收站短链接接口
     *
     * @param requestParam 请求参数
     */
    void recoverRecycleBin(RecycleBinRecoverReqDTO requestParam);
}
