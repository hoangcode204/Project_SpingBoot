package com.javaweb.repository.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="building")
public class BuildingEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name="name")
	private String name;
	
//	@Column(name="districid")
//	private Long districId;
	
	@Column(name="rentprice")
	private Long rentPrice;
	
	@Column(name="numberofbasement")
	private Long numberOfBasement;
	
	@Column(name="ward")
	private String ward;
	
	@Column(name="street")
	private String street;
	
	@Column(name="floorarea")
	private Long floorArea;
	
	@Column(name="managername")
	private String managerName;
	
	@Column(name="managerphonenumber")
	private String managerPhoneNumber;
	
	@Column(name="servicefee")
	private String serviceFee;
	
	@Column(name="brokeragefee")
	private String brokerageFee;
	
	//join thì bỏ k cần khai báo cột join
	@ManyToOne
	@JoinColumn(name = "district")
	private DistrictEntity district; //district dòng này phải trùng tên với bên mappedby.
	
	@OneToMany(mappedBy="building",fetch=FetchType.LAZY) //tên phải trùng với biến hứng dữ liệu
	private List<RentAreaEntity> rentAreaEntities=new ArrayList<RentAreaEntity>();
	
	
	
	public List<RentAreaEntity> getRentAreaEntities() {
		return rentAreaEntities;
	}
	public void setRentAreaEntities(List<RentAreaEntity> rentAreaEntities) {
		this.rentAreaEntities = rentAreaEntities;
	}
	public DistrictEntity getDistrict() {
		return district;
	}
	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}
	public Long getId() {
		return id;
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

	public Long getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(Long rentPrice) {
		this.rentPrice = rentPrice;
	}
	public Long getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(Long numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Long getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(Long floorArea) {
		this.floorArea = floorArea;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}
	public void setManagerPhoneNumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getBrokerageFee() {
		return brokerageFee;
	}
	public void setBrokerageFee(String brokerageFee) {
		this.brokerageFee = brokerageFee;
	}
	

	
	
	
}