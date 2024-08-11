package com.ruoci.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoci.shortlink.admin.common.biz.user.UserContext;
import com.ruoci.shortlink.admin.common.convention.exception.ClientException;
import com.ruoci.shortlink.admin.dao.entity.GroupDO;
import com.ruoci.shortlink.admin.dao.mapper.GroupMapper;
import com.ruoci.shortlink.admin.dto.req.group.ShortLinkGroupUpdateReqDTO;
import com.ruoci.shortlink.admin.dto.resp.group.ShortLinkGroupRespDTO;
import com.ruoci.shortlink.admin.remote.ShortLinkRemoteService;
import com.ruoci.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.ruoci.shortlink.admin.service.GroupService;
import com.ruoci.shortlink.admin.toolkit.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ruoci.shortlink.admin.common.constant.RedisCacheConstant.LOCK_GROUP_CREATE_KEY;

/**
 * 短链接分组接口实现层
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    private ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {};

    private final RedissonClient redissonClient;

    @Value("${short-link.group.max-num}")
    private Integer groupMaxNum;
    @Override
    public void saveGroup(String username, String groupName) {
        RLock lock = redissonClient.getLock(String.format(LOCK_GROUP_CREATE_KEY, username));
        lock.lock();
        try {
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getUsername, username)
                    .eq(GroupDO::getDelFlag, 0);
            List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
            if (CollUtil.isNotEmpty(groupDOList) && groupDOList.size() == groupMaxNum) {
                throw new ClientException(String.format("已超出最大分组数：%d", groupMaxNum));
            }
            String gid;
            do {
                gid = RandomStringGenerator.generateRandomString();
            } while (!checkHasGid(username, gid));
            GroupDO groupDO = GroupDO.builder()
                    .gid(gid)
                    .sortOrder(0)
                    .username(username)
                    .name(groupName)
                    .build();
            baseMapper.insert(groupDO);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void saveGroup(String name) {
        saveGroup(UserContext.getUsername(), name);
    }

    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> lambdaQueryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0)
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(lambdaQueryWrapper);
        List<ShortLinkGroupRespDTO> results = BeanUtil.copyToList(groupDOList, ShortLinkGroupRespDTO.class);

        List<ShortLinkGroupCountQueryRespDTO> groupCountQueryRespDTOList = shortLinkRemoteService.listGroupShortLinkCount(groupDOList.stream().map(GroupDO::getGid).collect(Collectors.toList())).getData();
        Map<String, Integer> counts = groupCountQueryRespDTOList.stream().collect(Collectors.toMap(ShortLinkGroupCountQueryRespDTO::getGid, ShortLinkGroupCountQueryRespDTO::getShortLinkCount));
        return results.stream().peek(result -> result.setShortLinkCount(counts.getOrDefault(result.getGid(), 0))).collect(Collectors.toList());

    }

    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO requestParam) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, requestParam.getGid());

        GroupDO groupDO = new GroupDO();
        groupDO.setName(requestParam.getName());
        baseMapper.update(groupDO, updateWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid);

        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO, updateWrapper);
    }

    /**
     * 当gid存在时, 返回true,不存在返回false
     */
    public boolean checkHasGid(String username, String gid){
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getId, gid)
                .eq(GroupDO::getUsername, Optional.ofNullable(username).orElse(UserContext.getUsername()));
        GroupDO hasGroupDO = baseMapper.selectOne(queryWrapper);
        return hasGroupDO != null;
    }



}
