package com.uzima.services;


import com.uzima.Mapper.ServicePointMapper;
import com.uzima.dtos.ServicePointRequest;
import com.uzima.dtos.ServicePointResponse;
import com.uzima.models.ServicePoint;
import com.uzima.repository.ServicePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServicePointService {

    private final ServicePointRepository servicePointRepository;

    public ResponseEntity<List<ServicePointResponse>> createServicePoint(List<ServicePointRequest> servicePointRequest) {

        List<ServicePoint> servicePoints = servicePointRequest.stream()
                .map(ServicePointMapper::fromRequest)
                .toList();

        List<ServicePoint> savedServicePoints = servicePointRepository.saveAll(servicePoints);

        List<ServicePointResponse> savedServicePointsData =
                savedServicePoints.stream()
                        .map(ServicePointMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(savedServicePointsData);

    }

    public ServicePointResponse getThisServicePoint(Long id) {

        ServicePoint servicePoint = servicePointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Point with id: " + id + " could not be found"));

        return ServicePointMapper.toResponse(servicePoint);
    }

    public ServicePointResponse updateThisServicePoint(Long id, ServicePointRequest request) {

        ServicePoint servicePoint = servicePointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Point with id: " + id + " could not be found"));

        servicePoint.setPointName(request.getPointName());

        ServicePointMapper.updateServicePoint(servicePoint, request);
        servicePointRepository.save(servicePoint);

        return ServicePointMapper.toResponse(servicePoint);
    }

    public ResponseEntity<List<ServicePointResponse>> getAvailableServicePoints(Boolean deletedFlag) {

        List<ServicePoint> servicePoints = servicePointRepository.findAllByDeletedFlag(deletedFlag);
        if (servicePoints.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ServicePointResponse> servicePointsResponse = servicePoints.stream()
                .map(ServicePointMapper::toResponse)
                .toList();

        return ResponseEntity.ok(servicePointsResponse);
    }

    public String deleteThisServicePoint(Long id) {

        ServicePoint servicePoint = servicePointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Point with id: " + id + " does not exist"));

        servicePoint.setDeletedFlag(true);
        servicePointRepository.save(servicePoint);

        String PointName = servicePoint.getPointName();

        return "ServicePoint '" + PointName + "' with id: " + id + " is deleted";

    }
}
