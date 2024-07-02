package com.ruoci.shortlink.admin.controller;

import com.ruoci.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接分组controller
 **/
@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

}
