package com.example.orchapi.schema.admin;

import java.io.Serializable;
import lombok.Data;

@Data
public class Role implements Serializable {
    int id;
    String name;
}
