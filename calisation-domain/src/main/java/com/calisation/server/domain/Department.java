package com.calisation.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Department {

	@Id
	public Integer id;
	
	@Column(name="zz")
	public String name;
	
	
	
}
