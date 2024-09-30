package com.example.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shortlink.admin.dao.entity.UserDO;
import com.example.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */

public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查找用户信息
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查看用户名是否存在
     * @param username 用户名
     * @return 用户名是否存在
     */
    Boolean hasUsername(String username);

}
