package com.example.orchapi.service.admin;

import com.example.orchapi.schema.admin.RolePermissionLink;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionLinkService{
    

    public List<RolePermissionLink> getByRoleName(String name) {
        List<RolePermissionLink> rolePermissionLinks = new ArrayList<>();
        return rolePermissionLinks;
    }
}
