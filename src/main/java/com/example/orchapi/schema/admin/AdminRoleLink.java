package com.example.orchapi.schema.admin;

import java.io.Serializable;
import lombok.Data;

@Data
public class AdminRoleLink implements Serializable {

    int id;
    Admin admin;
    Role role;
}
