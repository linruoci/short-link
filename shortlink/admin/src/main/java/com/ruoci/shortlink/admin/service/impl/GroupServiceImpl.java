package com.ruoci.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoci.shortlink.admin.dao.entity.GroupDO;
import com.ruoci.shortlink.admin.dao.mapper.GroupMapper;
import com.ruoci.shortlink.admin.dto.resp.group.ShortLinkGroupRespDTO;
import com.ruoci.shortlink.admin.service.GroupService;
import com.ruoci.shortlink.admin.toolkit.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链接分组接口实现层
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    @Override
    public void saveGroup(String name) {
        String gid;
        do {
            gid = RandomStringGenerator.generateRandomString();
        } while (checkHasGid(gid));

        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .sortOrder(0)
                .name(name)
                .build();
        baseMapper.insert(groupDO);
    }

    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> lambdaQueryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .isNull(GroupDO::getUsername)
                .eq(GroupDO::getDelFlag, 0)
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(lambdaQueryWrapper);
        return BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);
    }

    /**
     * 当gid存在时, 返回true,不存在返回false
     */
    public boolean checkHasGid(String gid){
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getId, gid)
//                TODO 获取用户名
                .eq(GroupDO::getUsername, null);
        GroupDO hasGroupDO = baseMapper.selectOne(queryWrapper);
        return hasGroupDO != null;
    }



}
