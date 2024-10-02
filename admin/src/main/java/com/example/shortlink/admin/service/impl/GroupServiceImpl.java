package com.example.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shortlink.admin.dao.entity.GroupDO;
import com.example.shortlink.admin.dao.mapper.GroupMapper;
import com.example.shortlink.admin.service.GroupService;
import com.example.shortlink.admin.toolkit.RandomStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

        @Override
        public void addGroup(String groupName) {
            String gid;
            do {
                gid = RandomStringUtils.generateRandomString(6);
            } while (!hasGid(gid));
            GroupDO groupDO = GroupDO.builder()
                    .gid(gid)
                    .name(groupName)
                    .build();
            baseMapper.insert(groupDO);
        }

        private boolean hasGid(String gid) {
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getGid, gid)
                    // TODO 设置用户名
                    .eq(GroupDO::getUsername, null);
            GroupDO hasGroupFlag = baseMapper.selectOne(queryWrapper);
            return hasGroupFlag == null;
        }
}
