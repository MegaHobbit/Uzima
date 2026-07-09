package com.Uzima.services;


import com.Uzima.dtos.ServicePointData;
import com.Uzima.models.ServicePoint;
import com.Uzima.repository.ServicePointRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServicePointService {

    private final ServicePointRepository servicePointRepository;

    public ResponseEntity<List<ServicePointData>> createServicePoint(List<ServicePointData> servicePointData) {

        List<ServicePoint> servicePoints = servicePointData.stream()
                .map(ServicePointData::fromData)
                .toList();

        List<ServicePoint> savedServicePoints = servicePointRepository.saveAll(servicePoints);

        List<ServicePointData> savedServicePointsData =
                savedServicePoints.stream()
                        .map(ServicePointData::toData)
                        .toList();

        return ResponseEntity.ok(savedServicePointsData);

    }

    public ServicePointData getThisServicePoint(Long id) {

        ServicePoint servicePoint = servicePointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Point with id: " + id + " could not be found"));

        return ServicePointData.toData(servicePoint);
    }

    public ServicePointData updateThisServicePoint(Long id, ServicePointData servicePointData) {

        ServicePoint servicePoint = servicePointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Point with id: " + id + " could not be found"));

        servicePoint.setPointName(servicePointData.getPointName());

        return ServicePointData.toData(servicePointRepository.save(servicePoint));
    }

    public ResponseEntity<List<ServicePointData>> getAvailableServicePoints(Boolean deletedFlag) {

        List<ServicePoint> servicePoints = servicePointRepository.findAllByDeletedFlag(deletedFlag);
        if (servicePoints.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ServicePointData> servicePointsData = servicePoints.stream()
                .map(ServicePointData::toData)
                .toList();

        return ResponseEntity.ok(servicePointsData);
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
