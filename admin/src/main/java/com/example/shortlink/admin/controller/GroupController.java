package com.example.shortlink.admin.controller;

import com.example.shortlink.admin.common.convention.result.Result;
import com.example.shortlink.admin.common.convention.result.Results;
import com.example.shortlink.admin.dto.req.ShortLinkGroupAddReqDTO;
import com.example.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.example.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.example.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增短链接分组
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<Void> addGroup(@RequestBody ShortLinkGroupAddReqDTO requestParam) {
        log.info("创建分组为:  {}", requestParam);
        String name = requestParam.getName();
        groupService.addGroup(name);
        return Results.success();
    }

    /**
     * 查询用户短链接分组集合
     */
    @GetMapping("/api/short-link/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup() {
        return Results.success(groupService.listGroup());
    }


    @PutMapping("/api/short-link/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO requestParam) {
        log.info("更新分组为:  {}", requestParam);
        groupService.updateGroup(requestParam);
        return Results.success();
    }
}
