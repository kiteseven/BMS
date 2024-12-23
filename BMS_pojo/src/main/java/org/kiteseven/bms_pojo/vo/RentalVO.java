package org.kiteseven.bms_pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalVO {
    private Integer rentalId;
    private Integer bicycleId;
    private String model;
    private Integer userId;
    private String username;
    private String phoneNumber;
    private LocalDateTime rentTime;
    private LocalDateTime returnTime;
    private Double rentalFee;
    private Integer status;
    private Integer totalRentalTime;
}
