package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username",nullable=false, unique=true)
	private String userName;
	
	@Column(name="password",nullable=false)
	private String passWord;
	
	@Column(name="fullname")
	private String fullName;
	
	@Column(name="status",nullable=false)
	private Integer status;
	
	@Column(name="email")
	private String email;

//	//thủ công
//	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
//	List<UserRoleEntity> userRoles= new ArrayList<UserRoleEntity>();
	
	
      //tự động không cần trung gian
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="user_role",
			joinColumns = @JoinColumn(name="userid",nullable=false),//name1 khóa ngoại của bảng1
			inverseJoinColumns= @JoinColumn(name="roleid",nullable=false))
	List<RoleEntity> roles = new ArrayList<>();
//	-------------------
 
	public Long getId() {
		return id;
	}
    

	

	public List<RoleEntity> getRoles() {
		return roles;
	}




	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}




	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
