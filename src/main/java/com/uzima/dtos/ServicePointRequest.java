package com.uzima.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uzima.enums.PointStatus;
import lombok.Data;

@Data
public class ServicePointRequest {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String pointName;

    private String description;

    private PointStatus pointStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean deletedFlag;
}
