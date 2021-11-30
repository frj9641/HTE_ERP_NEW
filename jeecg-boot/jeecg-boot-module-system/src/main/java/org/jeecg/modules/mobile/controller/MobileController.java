package org.jeecg.modules.mobile.controller;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.mobile.entity.UserPermission;
import org.jeecg.modules.mobile.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mobile")
public class MobileController {
    @Autowired
    private MobileService mobileService;

    @GetMapping(value = "/getPermissionList")
    public Result<?> getPermissionList() {
        UserPermission userPermission = mobileService.getUserPermission();
        return Result.OK(userPermission);
    }
}
