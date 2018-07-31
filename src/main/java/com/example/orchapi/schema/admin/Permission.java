package com.example.orchapi.schema.admin;

import java.io.Serializable;
import lombok.Data;

@Data
public class Permission implements Serializable{
   int id;
   String name;
}
