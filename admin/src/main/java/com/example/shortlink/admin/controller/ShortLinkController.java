package com.example.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shortlink.admin.common.convention.result.Result;
import com.example.shortlink.admin.remote.dto.ShortLinkRemoteService;
import com.example.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.example.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.example.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.example.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接后管
 */
@RestController
@Slf4j
public class ShortLinkController {

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {

    };

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return shortLinkRemoteService.pageShortLink(requestParam);
    }

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        log.info("创建短链接, requestParam:{}", requestParam);
        return shortLinkRemoteService.createShortLink(requestParam);
    }
}
