package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="role")
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="code",nullable=false,unique=true)
	private String code;
	   //----c1:thủ công
//	@OneToMany(mappedBy="role",fetch=FetchType.LAZY)
//	List<UserRoleEntity> userRoles=new ArrayList<UserRoleEntity>();	

	@ManyToMany(mappedBy="roles",fetch=FetchType.LAZY)
	private List<UserEntity> users = new ArrayList<>();

	
	

	public Long getId() {
		return id;
	}



	public List<UserEntity> getUsers() {
		return users;
	}



	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
