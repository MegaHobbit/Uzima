package com.uzima.Mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uzima.dtos.ServicePointRequest;
import com.uzima.dtos.ServicePointResponse;
import com.uzima.enums.PointStatus;
import com.uzima.models.ServicePoint;
import lombok.Data;

@Data
public class ServicePointMapper {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String pointName;

    private String description;

    private PointStatus pointStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean deletedFlag;


    public static ServicePointResponse toResponse(ServicePoint servicePoint) {

        ServicePointResponse response = new ServicePointResponse();

        response.setPointName(servicePoint.getPointName());
        response.setDescription(servicePoint.getDescription());
        response.setDeletedFlag(servicePoint.getDeletedFlag());
        response.setPointStatus(servicePoint.getPointStatus());
        response.setId(servicePoint.getId());

        return response;
    }

    public static ServicePoint fromRequest(ServicePointRequest request) {

        ServicePoint servicePoint = new ServicePoint();

        servicePoint.setPointName(request.getPointName());
        servicePoint.setDescription(request.getDescription());
        servicePoint.setPointStatus(request.getPointStatus());

        servicePoint.setDeletedFlag(false);

        return servicePoint;
    }

    public static void updateServicePoint(ServicePoint servicePoint, ServicePointRequest request) {

        //ServicePoint
        servicePoint.setPointName(request.getPointName());
        servicePoint.setDescription(request.getDescription());
        servicePoint.setPointStatus(request.getPointStatus());

    }
}

