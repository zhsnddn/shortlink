package com.example.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shortlink.admin.dao.entity.UserDO;
import com.example.shortlink.admin.dto.req.UserLoginReqDTO;
import com.example.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.example.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.example.shortlink.admin.dto.resp.UserLoginRespDTO;
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

    /**
     *  用户注册
     * @param requestParam 用户注册请求参数
     */
    void register(UserRegisterReqDTO requestParam);

    /**
     * 根据用户名修改用户
     * @param requestParam 修改用户请求参数
     */
    void update(UserUpdateReqDTO requestParam);

    /**
     * 用户登录
     * @param requestParam 用户登录请求参数
     * @return 用户登录返回参数 Token
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 检查用户是否登录
     * @param token 用户登录请求参数 token
     * @return 用户是否登录
     */
    Boolean checkLogin(String username, String token);

    /**
     *  用户登出
     * @param username 用户名
     * @param token token参数
     */
    void logout(String username, String token);
}
