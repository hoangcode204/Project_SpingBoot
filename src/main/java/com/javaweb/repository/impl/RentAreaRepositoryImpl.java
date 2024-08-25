package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

	@Override
	public List<RentAreaEntity> getValueByBuildingId(Long id) {
		String sql="Select * FROM rentarea where rentarea.buildingid= "+id;
	    List<RentAreaEntity> rentAreas=new ArrayList<>();
	    try (Connection conn = ConnectionJDBCUtil.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {
	             
	            while (rs.next()) {
	             RentAreaEntity areaEntity=new RentAreaEntity();
	             areaEntity.setValue(rs.getString("value"));
	             rentAreas.add(areaEntity);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Connected datbase failed...");
	        }
		return rentAreas;
	}

}
