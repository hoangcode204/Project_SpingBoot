package com.javaweb.api;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.ToolBarUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
@Transactional
@RestController
@PropertySource("classpath:application.properties") // Xác định nguồn file cấu hình

public class BuildingAPI {
	 @Value("${dev.nguyen}")
		private String data;
	
    @Autowired
    private BuildingService buildingService;
	
	@Autowired
	private BuildingRepository buildingRepository;
//	@PersistenceContext 
//	   private EntityManager entityManager;
	
    @GetMapping(value = "/api/building1/{id}") 
    public BuildingDTO getBuildingById(@PathVariable Long id){
    		    BuildingDTO result=new BuildingDTO();
    		    BuildingEntity building=buildingRepository.findById(id).get();
    		    return result;
    	
    }
    @PersistenceContext
    private EntityManager entityManager;
    @PostMapping(value="/api/building/")
    public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
    	  BuildingEntity buildingEntity = new BuildingEntity();
		  buildingEntity.setName(buildingRequestDTO.getName());
		  buildingEntity.setWard(buildingRequestDTO.getWard());
		  buildingEntity.setStreet(buildingRequestDTO.getStreet());
		  DistrictEntity districtEntity = new DistrictEntity();
		  districtEntity.setId(buildingRequestDTO.getDistrict());
		  buildingEntity.setDistrict(districtEntity);
		  //Cần một districtEntity để có các ttin như mã, code,name
		  entityManager.persist(buildingEntity);
    }
                           //jpa
//    @PutMapping(value="/api/building/")
//	  public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
//		  BuildingEntity buildingEntity=new BuildingEntity();
//		  buildingEntity.setId(1L);
//		  buildingEntity.setName(buildingRequestDTO.getName());
//		  buildingEntity.setWard(buildingRequestDTO.getWard());
//		  buildingEntity.setStreet(buildingRequestDTO.getStreet());
//		  DistrictEntity districtEntity = new DistrictEntity();
//		  districtEntity.setId(buildingRequestDTO.getDistrict());
//		  buildingEntity.setDistrict(districtEntity);
//		  entityManager.merge(buildingEntity);
//		  
//	  }
     //----------------------------------//
    @PutMapping(value="/api/building/")
	  public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		  BuildingEntity buildingEntity=buildingRepository.findById(buildingRequestDTO.getId()).get();
		  buildingEntity.setName(buildingRequestDTO.getName());
		  buildingEntity.setWard(buildingRequestDTO.getWard());
		  buildingEntity.setStreet(buildingRequestDTO.getStreet());
		  DistrictEntity districtEntity = new DistrictEntity();
		  districtEntity.setId(buildingRequestDTO.getDistrict());
		  buildingEntity.setDistrict(districtEntity);
		  buildingRepository.save(buildingEntity);
		  
	  }
              //---------Dùng Jpa-----------------//
//    @DeleteMapping(value="/api/building/{id}")
//    public void deleteBuilding(@PathVariable Long id) {
//  	  BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
//  	  entityManager.remove(buildingEntity);
//  	  System.out.println("ok");
//    }
    
    @DeleteMapping(value="/api/building/{ids}")
    public void deleteBuilding(@PathVariable List<Long> ids) {
  	  buildingRepository.deleteByIdIn(ids);
  	  System.out.println("ok");
    } 
    
    @GetMapping(value="/api/building/{name}")
    public BuildingDTO getBuildingById(@PathVariable String name) {
  	  BuildingDTO result=new BuildingDTO();
  	  List<BuildingEntity> buiding=buildingRepository.findByNameContaining(name);
  	  return result;
    } 
    @GetMapping(value="/api/building/{name}/{street}")
	public List<BuildingDTO> getBuildingById(@PathVariable String name,
			                           @PathVariable String street){
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
	    List<BuildingEntity> buildings = buildingRepository.findByNameContainingAndStreet(name, street);
	    
		return result;
	}
    
}

 