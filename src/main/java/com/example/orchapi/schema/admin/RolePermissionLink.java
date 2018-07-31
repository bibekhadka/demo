package com.example.orchapi.schema.admin;

import java.io.Serializable;
import lombok.Data;

@Data
public class RolePermissionLink implements Serializable{
    int id;
    Role role;
    Permission permission;
}
