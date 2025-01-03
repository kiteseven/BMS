package org.kiteseven.bms_pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BicyclesVO implements Serializable {
    private Integer bicycleId;
    private String model;
    private String location;
    private Integer status;//是否可出租
    private Double rentalFree;
    private Integer rentalCount;
    private String bicycleImage;
}
