package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.tomcat.websocket.server.WsHandshakeRequest;
import org.springframework.stereotype.Repository;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
    public static void joinTable(Map<String,Object> params,List<String>typeCode,StringBuilder sql) {
    	String staffId=(String)params.get("staffId");
    	if(StringUtil.checkString(staffId)) {
    		sql.append("Inner join assignmentbuilding ON b.id=assignmentbuilding .buildingid ");
    	}
    	if(typeCode!=null&&typeCode.size()!=0) {
    		sql.append(" INNER JOIN buildingrenttype ON b.id=buildingrenttype.buildingid ");
    		sql.append(" INNER JOIN renttype ON renttype.id=buildingrenttype.renttypeid ");
    	}
                            //Bỏ đi
//    	String rentAreaTo=(String)params.get("areaTo");
//    	String rentAreaFrom=(String)params.get("areaFrom");
//    	if(StringUtil.checkString(rentAreaFrom)||StringUtil.checkString(rentAreaTo)) {
//    		sql.append("INNER JOIN rentarea ON rentarea.buildingid=b.id ");
//    	}
    }
public static void queryNormal(Map<String,Object> params,StringBuilder where) {
    	for(Map.Entry<String, Object>it:params.entrySet()) {
    		if(!it.getKey().equals("staffId")&&!it.getKey().equals("typeCode")&&!it.getKey().startsWith("area")&&!it.getKey().startsWith("rentPrice")) {
    			String value=it.getValue().toString();
    			if(StringUtil.checkString(value)==true) {
    					where.append(" AND b."+it.getKey()+" like '%" + value+ "%' ");
    				}else if(NumberUtil.isNumber(value)==true) {
    					where.append(" AND b."+it.getKey()+"="+value);
    					
    				}    					
    		}
    	}
    }
public static void querySpecial(Map<String,Object> params,List<String> typeCode,StringBuilder where) {
	String staffId=(String)params.get("staffId");
	if(StringUtil.checkString(staffId)) {
		where.append("AND assignmentbuilding.staffid= "+staffId);
	}
	String rentAreaTo=(String)params.get("areaTo");
	String rentAreaFrom=(String)params.get("areaFrom");
	if(StringUtil.checkString(rentAreaFrom)==true||StringUtil.checkString(rentAreaTo)==true) {
		where.append(" And exists (select * from rentarea r where b.id=r.buildingid ");
		if(StringUtil.checkString(rentAreaFrom)) {
			where.append(" AND r.value>= "+rentAreaFrom);			
		}
		if(StringUtil.checkString(rentAreaTo)) {
			where.append(" AND r.value<= "+rentAreaTo);			
		}
		where.append(") ");
}
	//-------------------
	String rentPriceTo=(String)params.get("rentPriceTo");
	String rentPriceFrom=(String)params.get("rentPriceFrom");
	if(StringUtil.checkString(rentPriceFrom)==true||StringUtil.checkString(rentPriceTo)==true) {
		if(StringUtil.checkString(rentPriceFrom)) {
			where.append(" AND b.rentprice>= "+rentPriceFrom);			
		}
		if(StringUtil.checkString(rentPriceTo)) {
			where.append(" AND b.rentprice<= "+rentPriceTo);			
		}
}
	   //-----java7----
//    if (typeCode != null && typeCode.size()!=0) {
//      List<String> code=new ArrayList<String>();
//      for(String item:typeCode) {
//    	  code.add("'"+item+"'");
//      }
//      where.append(" and renttype.code IN(" +String.join(",", code)+ ") ");
//    }
// --java8------
	if(typeCode != null && typeCode.size()!=0) {
		where.append(" AND(");
		String sql=typeCode.stream().map(it-> "renttype.code like" + "'%" + it+ "%'").collect(Collectors.joining(" OR "));
		where.append(sql);
		where.append(" ) ");
	}
}
	@Override
	public List<BuildingEntity> findAll(Map<String,Object> params,List<String>typeCode) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.district, b.street, b.ward, b.numberofbasement,"
				+ " b.floorarea, b.rentprice,b.managername, b.managerphone, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(params, typeCode, sql);
		StringBuilder where=new StringBuilder(" Where 1=1");
		queryNormal(params, where);
		querySpecial(params, typeCode, where);
		where.append(" GROUP BY b.id ");
		sql.append(where);
		System.out.println(sql);
        List<BuildingEntity> result = new ArrayList<>();
        try (Connection conn = ConnectionJDBCUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql.toString())) {
             
            while (rs.next()) {
            	BuildingEntity buildingEntity = new BuildingEntity();
            	buildingEntity.setId(rs.getLong("b.id"));
            	buildingEntity.setName(rs.getString("b.name"));
            	buildingEntity.setWard(rs.getString("b.ward"));
            	buildingEntity.setDistrict(rs.getLong("b.district"));
            	buildingEntity.setStreet(rs.getString("b.street"));
            	buildingEntity.setFloorArea(rs.getLong("b.floorarea"));
            	buildingEntity.setRentPrice(rs.getLong("b.rentprice"));
            	buildingEntity.setServiceFee(rs.getLong("b.servicefee"));
            	buildingEntity.setBrokerageFee(rs.getLong("b.brokeragefee"));
            	buildingEntity.setManagerName(rs.getString("b.managername"));
            	buildingEntity.setManagerPhoneNumber(rs.getString("b.managerphone"));
                result.add(buildingEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connected datbase failed...");
        }
        return result;
	}
	@Override
	public void DeleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
//Commit bằng github


}
