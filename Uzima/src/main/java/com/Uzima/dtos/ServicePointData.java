package com.Uzima.dtos;


import com.Uzima.models.Doctor;
import com.Uzima.models.ServicePoint;
import lombok.Data;

@Data

public class ServicePointData {

    private String PointName;

    public static ServicePointData toData(ServicePoint servicePoint) {

        ServicePointData servicePointData = new ServicePointData();
        servicePointData.setPointName(servicePoint.getPointName());

        return servicePointData;
    }

    public static ServicePoint fromData(ServicePointData servicePointData) {

        ServicePoint servicePoint = new ServicePoint();
        servicePoint.setPointName(servicePointData.getPointName());

        return servicePoint;
    }

}
