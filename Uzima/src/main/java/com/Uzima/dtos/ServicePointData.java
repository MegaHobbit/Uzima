package com.Uzima.dtos;


import com.Uzima.models.ServicePoint;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class ServicePointData {

    private String PointName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean deletedFlag;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    public static ServicePointData toData(ServicePoint servicePoint) {

        ServicePointData servicePointData = new ServicePointData();
        servicePointData.setPointName(servicePoint.getPointName());
        servicePointData.setDeletedFlag(servicePoint.getDeletedFlag());
        servicePointData.setId(servicePoint.getId());

        return servicePointData;
    }

    public static ServicePoint fromData(ServicePointData servicePointData) {

        ServicePoint servicePoint = new ServicePoint();
        servicePoint.setPointName(servicePointData.getPointName());
        servicePoint.setDeletedFlag(false);

        return servicePoint;
    }

}
