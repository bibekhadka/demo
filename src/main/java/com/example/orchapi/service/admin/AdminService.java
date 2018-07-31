package com.example.orchapi.service.admin;

import com.example.orchapi.schema.admin.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminService {

    public Admin getByEmail(String email) {
        return new Admin();
    }
}
