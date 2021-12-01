package org.jeecg.modules.mobile.entity;

import lombok.Builder;
import lombok.Data;
import org.jeecg.modules.demo.materialrk.entity.Site;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class UserPermission {
    /**
     * 当前登录用户id
     */
    private String userId;
    /**
     * 所属部门，决定可以填写哪些部门
     */
    private List<Site> depart2work;
    /**
     * 负责部门，决定可以看到哪些部门
     */
    private List<Site> depart2manage;
    /**
     * 角色权限，决定可以看到哪些功能
     */
    private List<String> roles;

}
