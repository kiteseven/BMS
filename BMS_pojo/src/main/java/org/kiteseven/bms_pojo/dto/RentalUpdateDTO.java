package org.kiteseven.bms_pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalUpdateDTO {
    private Integer bicycleId;
    private Integer userId;
    private LocalDateTime rentTime;
    private LocalDateTime returnTime;
    private Double rentalFee;
    private Integer status;
    private Integer totalRentalTime;
}
