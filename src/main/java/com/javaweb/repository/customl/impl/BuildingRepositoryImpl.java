package com.javaweb.repository.customl.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.customl.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
    
	
//	@Value("${spring.datasource.url}")
//	private String DB_URL;
//	
//	@Value("${spring.datasource.username}")
//	private String USER;
//	
//	@Value("${spring.datasource.password}")
//	private String PASS;
//	
	
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder,StringBuilder sql) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if(staffId!=null) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id=assignmentbuilding.buildingid ");
		}
		List<String> typeCode=buildingSearchBuilder.getTypeCode();
		if(typeCode!=null && typeCode.size()!=0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id=buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id=buildingrenttype.renttypeid ");
		}
		//dung EXISTS bên dưới
//		String rentAreaTo=(String)params.get("areaTo");
//		String rentAreaFrom=(String)params.get("areaFrom");
//		if(StringUtil.checkString(rentAreaFrom)||StringUtil.checkString(rentAreaTo)) {
//			sql.append(" INNER JOIN rentarea ON rentarea.buildingid=b.id ");
//		}
	}
	
	public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder,StringBuilder where) {
		
//		for(Map.Entry<String, Object> item : params.entrySet()) {
//			if(!item.getKey().equals("staffId") && !item.getKey().equals("typeCode") &&
//			   !item.getKey().startsWith("area") && !item.getKey().startsWith("rentPrice")){
//				   String value=(String) item.getValue();
//				   if(StringUtil.checkString(value)) {
//					   if(NumberUtil.checkNumber(value)) {
//						   where.append(" AND b."+item.getKey().toString()+"="+value+" ");
//					   }else {
//						   where.append(" AND b."+item.getKey().toString()+" like "+"'%"+value+"%' ");
//					   }
//					  
//				   }
//			   }
//		}
		
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				
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
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
    public static void querySpecical(BuildingSearchBuilder buildingSearchBuilder,StringBuilder where) {
    	Long staffId = buildingSearchBuilder.getStaffId();
		if(staffId!=null) {
			where.append(" AND assignmentbuilding.staffid="+staffId+" ");
		}
		
		Long rentAreaTo= buildingSearchBuilder.getAreaTo();
		Long rentAreaFrom= buildingSearchBuilder.getAreaFrom();
		if(rentAreaFrom!=null||rentAreaTo!=null) {
			where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE r.buildingid=b.id ");
			if(rentAreaFrom!=null) {
				where.append(" AND r.value >= "+rentAreaFrom+" ");
			}
			if(rentAreaTo!=null) {
				where.append(" AND r.value <= "+rentAreaTo+" ");
			}
			where.append(") ");
		}
		
		Long rentPriceFrom=buildingSearchBuilder.getRentPriceFrom();
		Long rentPriceTo=buildingSearchBuilder.getRentPriceTo();
		if(rentPriceFrom!=null||rentPriceTo!=null) {
			if(rentPriceFrom!=null) {
				where.append(" AND b.rentprice >= "+rentPriceFrom+" ");
			}
			if(rentPriceTo!=null) {
				where.append(" AND b.rentprice <= "+rentPriceTo+" ");
			}
		}
		
		//java 7
//		if(typeCode!=null && typeCode.size()!=0) {
//			List<String> code =new ArrayList<>();
//			for(String item:typeCode) {
//				code.add("'"+item+"'");
//			}
//			where.append(" AND renttype.code IN("+String.join(",", code)+")"+" ");
//		}
		
		//java 8
		List<String> typeCode= buildingSearchBuilder.getTypeCode();
		if(typeCode!=null && typeCode.size()!=0) {
			where.append(" AND ( ");
			String where1=typeCode.stream().map(item->" renttype.code LIKE '%"+item+"%' ").collect(Collectors.joining(" OR "));
			where.append(where1+" ) ");
		}
	}

	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql=new StringBuilder("SELECT b.* FROM building b ");
		StringBuilder where=new StringBuilder(" WHERE 1=1 ");
		joinTable(buildingSearchBuilder, sql);
		queryNormal(buildingSearchBuilder, where);
		querySpecical(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id");
		sql.append(where);
		
		Query query = entityManager.createNativeQuery(sql.toString(),BuildingEntity.class);	
		return query.getResultList();
		
	}

	

}
