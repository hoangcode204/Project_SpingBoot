package com.javaweb.repository.impl;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepository {
    @PersistenceContext //khoi tao de dung jpql
    private EntityManager entityManager;
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		// TODO Auto-generated method stub
		//JPQL :JPA Query 
//		String sql="From BuildingEntity b ";
//		Query query=entityManager.createQuery(sql,BuildingEntity.class);
//		return query.getResultList();
		//Sql native
		String sql=" select * from building b where b.name like '%building%' ";
		Query query=entityManager.createNativeQuery(sql, BuildingEntity.class);
		return query.getResultList();
		
	}

	@Override
	public void DeleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

}
