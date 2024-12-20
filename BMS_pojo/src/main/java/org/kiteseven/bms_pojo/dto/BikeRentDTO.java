package org.kiteseven.bms_pojo.dto;

import lombok.Data;
import org.kiteseven.bms_pojo.entity.Bicycles;

import java.time.LocalDateTime;

@Data
public class BikeRentDTO {
    private Integer bicycleId;
    private Integer userId;
    private LocalDateTime rentTime;
    private LocalDateTime returnTime;
    private Double rentalFee;
    private Integer status;
}
