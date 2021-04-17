package com.example.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Permission;
import com.example.entity.Role;
import com.example.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RoleService roleService;

    public List<Permission> getByRoles(List<Role> roles) {
        List<Permission> permissions = new ArrayList<>();
        for (Role role : roles) {
            Role r = roleService.getById(role.getId());
            if (CollUtil.isNotEmpty(r.getPermission())) {
                permissions.addAll(listByIds(r.getPermission()));
            }
        }
        return permissions;
    }

    public void delete(Long id) {
        Permission delPermission = getById(id);
        removeById(id);
        // 删除角色分配的菜单
        List<Role> list = roleService.list();
        for (Role role : list) {
            List<Long> permission = role.getPermission();
            // 重新分配权限
            List<Long> newP = new ArrayList<>();
            for (Object p : permission) {
                if (!delPermission.getFlag().equals(p)) {
                    newP.add((long) p);
                }
            }
            role.setPermission(newP);
            roleService.updateById(role);
        }
    }
}
