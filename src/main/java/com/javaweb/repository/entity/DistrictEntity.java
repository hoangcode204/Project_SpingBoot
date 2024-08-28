package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="district")
public class DistrictEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	          //Áp dụng pt onetomany
	@OneToMany(mappedBy="district",fetch=FetchType.LAZY)
	private List<BuildingEntity> buildings=new ArrayList<>();
	

	public List<BuildingEntity> getBuildings() {
		return buildings;
	}
	public void setBuildings(List<BuildingEntity> buildings) {
		this.buildings = buildings;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
