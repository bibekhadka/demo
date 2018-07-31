package com.example.orchapi.schema.admin;

import java.io.Serializable;
import java.util.Calendar;
import lombok.Data;

@Data
public class Admin implements Serializable {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String salt;
	private Calendar addedOn;
}
