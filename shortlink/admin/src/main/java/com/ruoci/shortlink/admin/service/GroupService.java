package com.ruoci.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoci.shortlink.admin.dao.entity.GroupDO;

/**
 * 短链接分组接口层
 **/
public interface GroupService extends IService<GroupDO> {

    /**
     * 新建分组
     * @param groupName 分组名称
     */
    void saveGroup(String groupName);


}
