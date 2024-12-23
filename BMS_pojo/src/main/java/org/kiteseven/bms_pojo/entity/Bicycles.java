package org.kiteseven.bms_pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bicycles implements Serializable {
    private Integer bicycleId;
    private String model;
    private String location;
    private Integer status;//是否可出租
    private Double rentalFree;
    private Integer rentalCount;
}
