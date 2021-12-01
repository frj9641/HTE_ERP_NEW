package org.jeecg.modules.mobile.service.impl;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.demo.materialrk.entity.Site;
import org.jeecg.modules.mobile.entity.UserPermission;
import org.jeecg.modules.mobile.mapper.MobileMapper;
import org.jeecg.modules.mobile.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class MobileServiceImpl implements MobileService {
    @Autowired
    private MobileMapper mobileMapper;

    @Override
    public UserPermission getUserPermission() {
        //todo 通过当前登录用户去查询用户角色，用户角色去查询用户页面权限与按钮权限id，id在前端与组件按钮显隐相绑定，因为当前权限并未设置完全，所以待权限设置完全后再进行开发。
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<Site> departs2Work = mobileMapper.selectDeparts2Work(sysUser.getId());
        List<Site> departs2Manage = mobileMapper.selectDeparts2Manage(sysUser.getDepartIds().split(","));
        List<String> roles = mobileMapper.selectRoles(sysUser.getId());
        UserPermission userPermission = UserPermission.builder().userId(sysUser.getId())
                .depart2work(departs2Work).depart2manage(departs2Manage).roles(roles).build();
        return userPermission;
    }
}
