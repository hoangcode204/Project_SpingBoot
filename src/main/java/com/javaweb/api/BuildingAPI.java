package com.javaweb.api;
import java.util.List;
import java.util.Map;

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
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RestController
@PropertySource("classpath:application.properties") // Xác định nguồn file cấu hình
public class BuildingAPI {
	 @Value("${dev.nguyen}")
		private String data;
	 
    @Autowired
    private BuildingService buildingService;
   
    @GetMapping(value = "/api/building/") // Đúng là GetMapping
    public List<BuildingDTO> getBuilding(@RequestParam Map<String,Object> params,
    		                             @RequestParam(name="typeCode",required = false)List<String>typeCode) {
    	List<BuildingDTO> result=buildingService.findAll(params,typeCode);
        return result;
    }
    @PersistenceContext 
	   private EntityManager entityManager;
    @PostMapping(value="/api/building/")
    @Transactional
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
    @PutMapping(value="/api/building/")
	  public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		  BuildingEntity buildingEntity=new BuildingEntity();
		  buildingEntity.setId(1L);
		  buildingEntity.setName(buildingRequestDTO.getName());
		  buildingEntity.setWard(buildingRequestDTO.getWard());
		  buildingEntity.setStreet(buildingRequestDTO.getStreet());
		  DistrictEntity districtEntity = new DistrictEntity();
		  districtEntity.setId(buildingRequestDTO.getDistrict());
		  buildingEntity.setDistrict(districtEntity);
		  entityManager.merge(buildingEntity);
		  
	  }
    @DeleteMapping(value="/api/building/{id}")
    public void deleteBuilding(@PathVariable Long id) {
  	  BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
  	  entityManager.remove(buildingEntity);
  	  System.out.println("ok");
    }
}
//định nghĩa một API để lấy thông tin về các tòa nhà từ phía client 
//thông qua phương thức GET. Các tham số tìm kiếm được truyền qua URL, 
//sau đó được xử lý bởi BuildingService.
 