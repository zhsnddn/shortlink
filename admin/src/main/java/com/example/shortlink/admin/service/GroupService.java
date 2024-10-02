package com.example.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shortlink.admin.dao.entity.GroupDO;
import com.example.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.example.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.example.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

/**
 * 短链接分组接口层
 */

public interface GroupService extends IService<GroupDO> {


    /**
     * 新增分组
     * @param groupName 分组名称
     */
    void addGroup(String groupName);

    /**
     * 查询用户短链接分组集合
     * @return 用户短链接分组集合
     */
    List<ShortLinkGroupRespDTO> listGroup();

    /**
     * 更新分组
     * @param requestParam 更新分组请求参数
     */
    void updateGroup(ShortLinkGroupUpdateReqDTO requestParam);

    /**
     * 删除分组
     * @param gid 分组标识
     */
    void deleteGroup(String gid);

    /**
     * 短链接分组排序
     * @param requestParam 短链接分组排序请求参数
     */
    void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam);
}
