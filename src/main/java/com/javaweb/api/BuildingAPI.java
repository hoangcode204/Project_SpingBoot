package com.javaweb.api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
    @Autowired
    private BuildingService buildingService;
    @GetMapping(value = "/api/building/") // Đúng là GetMapping
    public List<BuildingDTO> getBuilding(@RequestParam(name="name",required = false) String name,
    		                              @RequestParam(name="districtId",required = false) Long district,
    		                              @RequestParam(name="typeCode",required=false) List<String>typeCode) {
    	List<BuildingDTO> result=buildingService.findAll(name, district);
        return result;
    }

    
}
 