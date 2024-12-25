package org.kiteseven.bms_pojo.dto;

import lombok.Data;

@Data
public class BicycleUpdateDTO  {
    private Integer bicycleId;
    private String model;
    private String location;
    private Integer status;//是否可出租
    private Double rentalFree;
    private Integer rentalCount;
    private String bicycleImage;
}
