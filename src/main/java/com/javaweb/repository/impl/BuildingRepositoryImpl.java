package com.javaweb.repository.impl;

import java.lang.reflect.Field;
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

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
    public static void joinTable(BuildingSearchBuilder buildingSearchBuilder,StringBuilder sql) {
    	Long staffId=buildingSearchBuilder.getStaffId();
    	if(staffId!=null) {
    		sql.append("Inner join assignmentbuilding ON b.id=assignmentbuilding .buildingid ");
    	}
    	List<String> typeCode=buildingSearchBuilder.getTypeCode();
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
public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder,StringBuilder where) {
	 try {
		 Field[] fields=BuildingSearchBuilder.class.getDeclaredFields();
		 for(Field item:fields) {
			 item.setAccessible(true);
			 String fieldName=item.getName();
			 if(!fieldName.equals("staffId") && !fieldName.equals("typeCode") &&
					   !fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")) {
						Object value = item.get(buildingSearchBuilder);
						if(value!=null) {
							   if(item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
								   where.append(" AND b."+fieldName+"="+value+" ");
							   }else if(item.getType().getName().equals("java.lang.String")) {
								   where.append(" AND b."+fieldName+" like "+"'%"+value+"%' ");
							   }
							  
						   }
					}
		 }
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
    }
public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder,StringBuilder where) {
	Long staffId = buildingSearchBuilder.getStaffId();
	if(staffId!=null) {
		where.append("AND assignmentbuilding.staffid= "+staffId);
	}
	Long rentAreaTo=buildingSearchBuilder.getAreaTo();
	Long rentAreaFrom=buildingSearchBuilder.getAreaFrom();
	if(rentAreaFrom!=null||rentAreaTo!=null) {
		where.append(" And exists (select * from rentarea r where b.id=r.buildingid ");
		if(rentAreaFrom!=null) {
			where.append(" AND r.value>= "+rentAreaFrom);			
		}
		if(rentAreaTo!=null) {
			where.append(" AND r.value<= "+rentAreaTo);			
		}
		where.append(") ");
		           //Loại các tòa nhà có 1 rentArea không thỏa mãn điều kiện.
//		if (rentAreaFrom != null) {
//	        where.append(" AND NOT EXISTS (SELECT * FROM rentarea r2 WHERE r2.buildingid = b.id AND r2.value < " + rentAreaFrom + ")");
//	    }
}
	//-------------------
	Long rentPriceTo=buildingSearchBuilder.getRentPriceTo();
	Long rentPriceFrom=buildingSearchBuilder.getRentPriceFrom();
	if(rentPriceFrom!=null||rentPriceTo!=null) {
		if(rentPriceFrom!=null) {
			where.append(" AND b.rentprice>= "+rentPriceFrom);			
		}
		if(rentPriceTo!=null) {
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
	    List<String >typeCode=buildingSearchBuilder.getTypeCode();
	    if(typeCode!=null&&typeCode.size()!=0) {
		where.append(" AND(");
		String sql=typeCode.stream().map(it-> "renttype.code like" + "'%" + it+ "%'").collect(Collectors.joining(" OR "));
		where.append(sql);
		where.append(" ) ");
	    }
}
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.district, b.street, b.ward, b.numberofbasement,"
				+ " b.floorarea, b.rentprice,b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where=new StringBuilder(" Where 1=1");
		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
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
            	buildingEntity.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
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
