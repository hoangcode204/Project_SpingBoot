package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
	
	@Override
	public DistrictEntity findNameById(Long id) {
		String sql="Select d.name From district d where d.id= " + id + ";";
		System.out.println(sql);
		DistrictEntity districtEntity=new DistrictEntity();
		try (Connection conn = ConnectionJDBCUtil.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {
	             
	            while (rs.next()) {
	            	
	            	districtEntity.setName(rs.getString("name"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Connected datbase failed...");
	        }
		return districtEntity;
	}

}
