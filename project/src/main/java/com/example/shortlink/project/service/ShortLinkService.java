package com.example.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shortlink.project.dao.entity.ShortLinkDO;
import com.example.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.example.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.example.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.example.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 * 短链接接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     * 创建短链接
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO requestParam);

    /**
     * 短链接分页查询
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO requestParam);
}
