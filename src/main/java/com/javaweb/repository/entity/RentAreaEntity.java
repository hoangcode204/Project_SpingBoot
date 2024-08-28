package com.javaweb.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="rentarea")
public class RentAreaEntity {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
@Column(name="value")
private String value;
@ManyToOne
@JoinColumn(name="buildingid")
private BuildingEntity building; //phải trùng với tên bên map của building



public BuildingEntity getBuilding() {
	return building;
}

public void setBuilding(BuildingEntity building) {
	this.building = building;
}

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}

}
