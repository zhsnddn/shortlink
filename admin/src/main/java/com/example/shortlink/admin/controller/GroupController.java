package com.example.shortlink.admin.controller;

import com.example.shortlink.admin.service.GroupService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;


}
