package com.example.shortlink.admin.controller;

import com.example.shortlink.admin.common.convention.result.Result;
import com.example.shortlink.admin.common.convention.result.Results;
import com.example.shortlink.admin.dto.req.ShortLinkGroupAddReqDTO;
import com.example.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
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
    @PostMapping("/api/short-link/admin/v1/group")
    public Result<Void> addGroup(@RequestBody ShortLinkGroupAddReqDTO requestParam) {
        log.info("创建分组为:  {}", requestParam);
        String name = requestParam.getName();
        groupService.addGroup(name);
        return Results.success();
    }

    /**
     * 查询用户短链接分组集合
     */
    @GetMapping("/api/short-link/admin/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup() {
        return Results.success(groupService.listGroup());
    }

    /**
     * 更新用户短链接分组
     */
    @PutMapping("/api/short-link/admin/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO requestParam) {
        log.info("更新分组为:  {}", requestParam);
        groupService.updateGroup(requestParam);
        return Results.success();
    }

    /**
     * 删除用户短链接分组
     */
    @DeleteMapping("/api/short-link/admin/v1/group")
    public Result<Void> deleteGroup(@RequestParam("gid") String gid) {
        log.info("删除分组为:  {}", gid);
        groupService.deleteGroup(gid);
        return Results.success();
    }

    /**
     * 对短链接分组进行排序
     */
    @PostMapping("/api/short-link/admin/v1/group/sort")
    public Result<Void> sortGroup(@RequestBody List<ShortLinkGroupSortReqDTO> requestParam) {
        groupService.sortGroup(requestParam);
        return Results.success();
    }
}
