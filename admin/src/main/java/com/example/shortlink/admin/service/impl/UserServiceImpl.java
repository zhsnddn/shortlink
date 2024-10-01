package com.example.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shortlink.admin.common.convention.exception.ClientException;
import com.example.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.example.shortlink.admin.dao.entity.UserDO;
import com.example.shortlink.admin.dao.mapper.UserMapper;
import com.example.shortlink.admin.dto.req.UserLoginReqDTO;
import com.example.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.example.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.example.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.example.shortlink.admin.dto.resp.UserRespDTO;
import com.example.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static com.example.shortlink.admin.common.convention.errorcode.BaseErrorCode.*;
import static com.example.shortlink.admin.enums.Constans.USER_LOGIN;

/**
 * 用户接口实现层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> rBloomFilterConfiguration;
    private final RBloomFilter userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public Boolean hasUsername(String username) {
        return !rBloomFilterConfiguration.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if(!hasUsername(requestParam.getUsername())) {
            throw new ClientException(USER_NAME_EXIST_ERROR);
        }

        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try{
            if (lock.tryLock()) {
                int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                if(inserted < 1) {
                    throw new ClientException(USER_SAVE_REEOR);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException(USER_NAME_EXIST_ERROR);
        } finally {
            lock.unlock();
        }


    }

    @Override
    public void update(UserUpdateReqDTO requestParam) {
        //TODO 验证修改用户是否为登录用户
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class).
                eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class), updateWrapper);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if(userDO == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(USER_LOGIN + requestParam.getUsername()))) {
            throw new ClientException(USER_LOGGING_ERROR);
        }
        /**
         * Hash
         * Key:login_username
         * Value:
         *  Key: token 标识
         *  Value: JSON字符串（用户信息）
         */
        String uuid = UUID.randomUUID().toString();
        String value = JSON.toJSONString(userDO);
        stringRedisTemplate.opsForValue().set(uuid, value, 30L, TimeUnit.MINUTES);
        stringRedisTemplate.opsForHash().put(USER_LOGIN + requestParam.getUsername(), uuid, value);
        stringRedisTemplate.expire(USER_LOGIN + requestParam.getUsername(), 30, TimeUnit.MINUTES);
        return new UserLoginRespDTO(uuid);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get(USER_LOGIN + username, token) != null;
    }

    @Override
    public void logout(String username, String token) {
        if(checkLogin(username, token)) {
            stringRedisTemplate.delete(USER_LOGIN + username);
            return;
        }
        throw new ClientException(USER_LOGOUT_ERROR);
    }
}

