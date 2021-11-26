package org.jeecg.modules.mobile.controller;

import com.sun.xml.bind.v2.TODO;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/mobile")
public class MobileController {
    @Autowired
    private CommonAPI commonAPI;

    @GetMapping(value = "/getPermissionList")
    public Result<?> getPermissionList() {
        //todo 通过当前登录用户去查询用户角色，用户角色去查询用户页面权限与按钮权限id，id在前端与组件按钮显隐相绑定，因为当前权限并未设置完全，所以待权限设置完全后再进行开发。
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return Result.OK(sysUser);
    }
}
