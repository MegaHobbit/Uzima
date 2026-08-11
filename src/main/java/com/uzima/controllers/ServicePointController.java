package com.uzima.controllers;

import com.uzima.dtos.ServicePointRequest;
import com.uzima.dtos.ServicePointResponse;
import com.uzima.services.ServicePointService;
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
    public ResponseEntity<List<ServicePointResponse>> createServicePoint(
            @RequestBody List<ServicePointRequest> servicePointRequest) {

        return servicePointService.createServicePoint(servicePointRequest);

    }

    @GetMapping("/get")
    public ServicePointResponse getThisServicePoint(
            @RequestParam("id") Long id) {

        return servicePointService.getThisServicePoint(id);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ServicePointResponse>> getAllServicePoint(
            @RequestParam(value = "deleted_flag", required = false) Boolean deletedFlag) {

        return servicePointService.getAvailableServicePoints(deletedFlag);
    }

    @PutMapping("/update")
    public ServicePointResponse updateServicePoint(
            @RequestParam("id") Long id,
            @RequestBody ServicePointRequest request) {

        return servicePointService.updateThisServicePoint(id, request);
    }

    @DeleteMapping("/delete")
    public String deleteServicePoint(@RequestParam("id") Long id) {

        return servicePointService.deleteThisServicePoint(id);
    }


}
