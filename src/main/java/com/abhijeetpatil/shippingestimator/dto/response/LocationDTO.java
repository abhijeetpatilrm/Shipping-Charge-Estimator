package com.abhijeetpatil.shippingestimator.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationDTO {

    @JsonProperty("lat")
    private double latitude;
    
    @JsonProperty("long")
    private double longitude;
}
