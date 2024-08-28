package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {
	@Autowired
	private ModelMapper modelMapper;
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		BuildingDTO building=modelMapper.map(item, BuildingDTO.class);
		//mapper đối với các th cấu trúc tương đồng 
		//vd: buildingentity và buildingdto
		building.setAddress(item.getStreet()+", "+item.getWard()+", "+item.getDistrict().getName());
		List<RentAreaEntity> rentAreas = item.getRentAreaEntities();
		//Kh sd được mapper vì ph chuyển từ list->chuỗi
		String resultArea = rentAreas.stream().map(it->it.getValue().toString()).collect(Collectors.joining(","));
		building.setRentArea(resultArea);
		return building;
	}
}
