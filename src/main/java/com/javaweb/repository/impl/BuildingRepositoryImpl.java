package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String DB_URL="jdbc:mysql://localhost:3306/estatebasic";
    static final String USER="root";
    static final String PASS="123456";
	@Override
	public List<BuildingEntity> findAll(String name) {
		String sql = "SELECT * FROM building b where name like '%"+name+"%'";
        List<BuildingEntity> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
             
            while (rs.next()) {
            	BuildingEntity building = new BuildingEntity();
                building.setName(rs.getString("name"));
                building.setStreet(rs.getString("street"));
                building.setWard(rs.getString("ward"));
                building.setNumberOfBasement(rs.getInt("numberofbasement"));
                result.add(building);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connected datbase failed...");
        }
        return result;
	}



}
