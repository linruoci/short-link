package com.ruoci.shortlink.admin.controller;

import com.ruoci.shortlink.admin.common.convention.result.Result;
import com.ruoci.shortlink.admin.common.convention.result.Results;
import com.ruoci.shortlink.admin.dto.req.group.ShortLinkGroupSaveReqDTO;
import com.ruoci.shortlink.admin.dto.req.group.ShortLinkGroupUpdateReqDTO;
import com.ruoci.shortlink.admin.dto.resp.group.ShortLinkGroupRespDTO;
import com.ruoci.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接分组controller
 **/
@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * 新建短链接分组
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<Void> save(@RequestBody ShortLinkGroupSaveReqDTO requestParam){
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }

    @GetMapping("/api/short-link/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup(){
        return Results.success(groupService.listGroup());
    }

    @PutMapping("/api/short-link/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO requestParam){
        groupService.updateGroup(requestParam);
        return Results.success();
    }

    @DeleteMapping("/api/short-link/v1/group")
    public Result<Void> deleteGroup(@RequestParam String gid){
        groupService.deleteGroup(gid);
        return Results.success();

    }


}
