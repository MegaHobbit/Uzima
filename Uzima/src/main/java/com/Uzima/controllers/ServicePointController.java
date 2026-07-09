package com.Uzima.controllers;

import com.Uzima.dtos.ServicePointData;
import com.Uzima.services.ServicePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service_point")
public class ServicePointController {

    private final ServicePointService servicePointService;

    @PostMapping("/create")
    public ResponseEntity<List<ServicePointData>> createServicePoint(
            @RequestBody List<ServicePointData> servicePointData) {

        return servicePointService.createServicePoint(servicePointData);

    }

    @GetMapping("/get")
    public ServicePointData getThisServicePoint(
            @RequestParam("id") Long id) {

        return servicePointService.getThisServicePoint(id);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ServicePointData>> getAllServicePoint(
            @RequestParam (value = "deleted_flag",required = false)  Boolean deletedFlag) {

        return servicePointService.getAvailableServicePoints(deletedFlag);
    }

    @PutMapping("/update")
    public ServicePointData updateServicePoint(
            @RequestParam("id") Long id,
            @RequestBody ServicePointData servicePointData) {

        return servicePointService.updateThisServicePoint(id, servicePointData);
    }

    @DeleteMapping("/delete")
    public String deleteServicePoint(@RequestParam("id") Long id) {

        return servicePointService.deleteThisServicePoint(id);
    }


}
