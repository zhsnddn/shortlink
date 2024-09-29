package com.example.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shortlink.admin.dao.entity.UserDO;
import com.example.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */

public interface UserService extends IService<UserDO> {

    UserRespDTO getUserByUsername(String username);

}
