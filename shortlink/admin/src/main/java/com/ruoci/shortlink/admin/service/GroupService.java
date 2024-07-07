package com.ruoci.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoci.shortlink.admin.dao.entity.GroupDO;
import com.ruoci.shortlink.admin.dto.resp.group.ShortLinkGroupRespDTO;

import java.util.List;

/**
 * 短链接分组接口层
 **/
public interface GroupService extends IService<GroupDO> {

    /**
     * 新建分组
     * @param groupName 分组名称
     */
    void saveGroup(String groupName);


    /**
     * 查询短链接分组.
     * @return 返回用户短链接分组
     */
    List<ShortLinkGroupRespDTO> listGroup();
}