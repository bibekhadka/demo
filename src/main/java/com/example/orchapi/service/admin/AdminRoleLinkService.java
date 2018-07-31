package com.example.orchapi.service.admin;

import com.example.orchapi.schema.admin.AdminRoleLink;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AdminRoleLinkService {    

    public List<AdminRoleLink> getByAdminEmail(String email) {
       List<AdminRoleLink> adminRoleLinks = new ArrayList<>();
       return adminRoleLinks;
    }

}
