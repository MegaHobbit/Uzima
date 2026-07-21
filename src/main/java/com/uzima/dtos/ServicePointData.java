package com.uzima.dtos;


import com.uzima.enums.PointStatus;
import com.uzima.models.ServicePoint;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class ServicePointData {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String pointName;

    private String description;

    private PointStatus pointStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean deletedFlag;


    public static ServicePointData toData(ServicePoint servicePoint) {

        ServicePointData servicePointData = new ServicePointData();
        servicePointData.setPointName(servicePoint.getPointName());
        servicePointData.setDescription(servicePoint.getDescription());
        servicePointData.setDeletedFlag(servicePoint.getDeletedFlag());
        servicePointData.setPointStatus(servicePoint.getPointStatus());
        servicePointData.setId(servicePoint.getId());

        return servicePointData;
    }

    public static ServicePoint fromData(ServicePointData servicePointData) {

        ServicePoint servicePoint = new ServicePoint();
        servicePoint.setPointName(servicePointData.getPointName());
        servicePoint.setDescription(servicePointData.getDescription());
        servicePoint.setPointStatus(servicePointData.getPointStatus());

        servicePoint.setDeletedFlag(false);

        return servicePoint;
    }

}
